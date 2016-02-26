package com.feiynn.validation.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feiynn.validation.BaseValidateService;
import com.feiynn.validation.ValidateService;
import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Configuration;
import com.feiynn.validation.config.pojo.Field;
import com.feiynn.validation.config.pojo.Group;
import com.feiynn.validation.config.pojo.Rule;
import com.feiynn.validation.config.pojo.Validat;
import com.feiynn.validation.utils.ValidateUtils;
import com.feiynn.validation.validators.MaxLengthValidator;
import com.feiynn.validation.validators.MaxValidator;
import com.feiynn.validation.validators.MinLengthValidator;
import com.feiynn.validation.validators.MinValidator;
import com.feiynn.validation.validators.RangeLengthValidator;
import com.feiynn.validation.validators.RangeValidator;
import com.feiynn.validation.validators.RegexValidator;
import com.feiynn.validation.validators.RequiredValidator;
import com.feiynn.validation.validators.regex.DigitsValidator;
import com.feiynn.validation.validators.regex.EmailValidator;
import com.feiynn.validation.validators.regex.MobileValidator;
import com.feiynn.validation.validators.regex.NumberValidator;
import com.feiynn.validation.validators.regex.ZipValidator;

/** 
 * This is the main class for using feiynn-validate
 *
 * <p>You can create a ValidationConfig instance by invoking {@code getInstance()} if the default configuration
 * is all you need. You can also use {@code getInstance(String)} to build a ValidationConfig instance with custom 
 * configuration .</p>
 * @author: Dean
 */
public class ValidationConfig {
	final static Logger logger = LoggerFactory.getLogger(ValidationConfig.class);
	protected static final String DEFAULT_RULES_FILE = "validation-rules.xml";
	protected static final String DEFAULT_PROP_FILE = "validation.properties";
	protected String rulesFile;
	protected Configuration configuration;
	private volatile static ValidationConfig instance = null; 
	
	private ValidationConfig(){
	}
	
	private ValidationConfig(String rulesFile){
		this.rulesFile = rulesFile;
	}
	
	public static ValidationConfig getInstance(){
		return getInstance(DEFAULT_RULES_FILE);
	}
	
	public static ValidationConfig getInstance(String rulesFile){
		if(instance != null){
			return instance;
		}
		synchronized (ValidationConfig.class) {
            if (instance == null) {
            	instance = new ValidationConfig(rulesFile);
            	instance.initConfiguration();
            } else {
            	logger.debug("Attempting to create an existing singleton. Existing singleton returned.");
            }
            return instance;
        }
	}
	
	private void initConfiguration(){
		configuration = new Configuration();
		initValidators();
		initValidatorsMessage();
		readXML();
	}
	
	public ValidateService buildValidateService(){
		return new BaseValidateService(this);
	}
	
	/**
	 * Read xml group configuration.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void readXML(){
		logger.debug("Read validation file: " + rulesFile);
		Document document = null;
		document = ValidateUtils.read(rulesFile);
		Element root = document.getRootElement();
		List<Element> includeList = root.elements("include");
		if(includeList != null && !includeList.isEmpty()) {
			Iterator<Element> il = includeList.iterator();
			while(il.hasNext()){
				Element includeE = il.next();
				String file = includeE.attributeValue("file");
				if(StringUtils.isBlank(file)) continue;
				this.readXML(file);
			}
		}
		this.readGroups(root);
		this.readValidators(root);
	}
	
	@SuppressWarnings("unchecked")
	private void readXML(String path) {
		logger.debug("Read include validation file: "+path);
		Document document = null;
		document = ValidateUtils.read(path);
		Element root = document.getRootElement();
		List<Element> includeList = root.elements("include");
		if(includeList != null && !includeList.isEmpty()) {
			Iterator<Element> il = includeList.iterator();
			while(il.hasNext()){
				Element includeE = il.next();
				String file = includeE.attributeValue("file");
				if(StringUtils.isBlank(file)) continue;
				this.readXML(file);
			}
		}
		this.readGroups(root);
		this.readValidators(root);
	}
	
	@SuppressWarnings("unchecked")
	private void readGroups(Element root){
		List<Element> groupList = root.elements("group");
		Iterator<Element> gl = groupList.iterator();
		while(gl.hasNext()){
			Element groupE = gl.next();
			String gname = groupE.attributeValue("name").trim();
			
			List<Field> fields = new ArrayList<Field>();
			List<Element> fieldList = groupE.elements("field");
			Iterator<Element> fl = fieldList.iterator();
			while(fl.hasNext()){
				Element fieldE = fl.next();
				String fname = fieldE.attributeValue("name").trim();
				Field field = new Field();
				field.setName(fname);
				
				List<Rule> rules = new ArrayList<Rule>();
				List<Element> ruleList = fieldE.elements("rule");
				Iterator<Element> rl = ruleList.iterator();
				while(rl.hasNext()){
					Element ruleE = rl.next();
					String rname = ruleE.attributeValue("name").trim();
					String rmessage = ruleE.elementTextTrim("message");
					rmessage = rmessage == null? ruleE.attributeValue("message"):rmessage;
					String value = ruleE.elementTextTrim("value");
					value = value == null? ruleE.attributeValue("value"):rmessage;
					Rule rule = new Rule();
					rule.setName(rname);
					rule.setValue(value);
					rule.setMessage(rmessage);
					
					List<Element> params = ruleE.elements("param");
					Iterator<Element> ps = params.iterator();
					while(ps.hasNext()){
						Element paramE = ps.next();
						String pname = paramE.attributeValue("name").trim();
						String pvalue = paramE.elementTextTrim("value");
						pvalue = pvalue == null ? paramE.attributeValue("value").trim() : pvalue;
						rule.addParameter(pname, pvalue);
					}
					
					rules.add(rule);
				}
				
				field.setRules(rules);
				fields.add(field);
			}
			Group group = new Group();
			group.setName(gname);
			group.setFields(fields);
			
			Map<String, Group> gp = configuration.getGroups();
			if(gp == null){
				gp = new HashMap<String, Group>();
				configuration.setGroups(gp);
			}
			gp.put(gname,group);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void readValidators(Element root){
		try {
			List<Element> validators = root.elements("validators");
			Iterator<Element> gl = validators.iterator();
			while(gl.hasNext()){
				Element groupE = gl.next();
				List<Element> fieldList = groupE.elements("validator");
				Iterator<Element> vl = fieldList.iterator();
				while(vl.hasNext()){
					Element validatorE = vl.next();
					String vname = validatorE.attributeValue("name").trim();
					String vclass = validatorE.attributeValue("class").trim();
					String message = validatorE.attributeValue("message");
					Validat v = new Validat();
					v.setName(vname);
					v.setClassName(vclass);
					Validator validator = this.instanceValidator(v);
					if(validator != null){
						configuration.getValidators().put(vname, validator);
						configuration.getValidatorsDefaultMsg().put(vname, message);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unchecked")
	public Validator instanceValidator(Validat v) {
		Validator validator = null;
		try {
			Class<Validator> vclassor = (Class<Validator>) Class.forName(v.getClassName());
			validator = vclassor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Instance Validator failed,class name is " + v.getClassName());
		}
		return validator;
	}
	
	private void initValidators(){
		configuration.getValidators().put("required", new RequiredValidator());
		configuration.getValidators().put("max", new MaxValidator());
		configuration.getValidators().put("min", new MinValidator());
		configuration.getValidators().put("range", new RangeValidator());
		configuration.getValidators().put("maxlength", new MaxLengthValidator());
		configuration.getValidators().put("minlength", new MinLengthValidator());
		configuration.getValidators().put("rangelength", new RangeLengthValidator());
		configuration.getValidators().put("digits", new DigitsValidator());
		configuration.getValidators().put("number", new NumberValidator());
		configuration.getValidators().put("regex", new RegexValidator());	
		configuration.getValidators().put("email", new EmailValidator());	
		configuration.getValidators().put("mobile", new MobileValidator());	
		configuration.getValidators().put("zip", new ZipValidator());
	}
	
	protected void initValidatorsMessage(){
		Properties prop = ValidateUtils.readProperties(DEFAULT_PROP_FILE);
		for (Object key : prop.keySet()) {
			configuration.getValidatorsDefaultMsg().put(key.toString(), prop.getProperty(key.toString()));
		}
	}

	public String getRulesFile() {
		return rulesFile;
	}


	public void setRulesFile(String rulesFile) {
		this.rulesFile = rulesFile;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	
	
}
