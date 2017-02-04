package com.feiynn.validation.config;

import com.feiynn.validation.BaseValidateService;
import com.feiynn.validation.ValidateService;
import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.*;
import com.feiynn.validation.utils.ValidateUtils;
import com.feiynn.validation.validators.*;
import com.feiynn.validation.validators.regex.*;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * <p>You can create a ValidationConfig instance by invoking {@code getInstance()} if the default configuration
 * is all you need. You can also use {@code getInstance(String)} to build a ValidationConfig instance with custom
 * configuration .</p>
 *
 * @author Dean
 */
public class ValidationConfig {
	private final static Logger logger = LoggerFactory.getLogger(ValidationConfig.class);
	public static final String DEFAULT_RULES_FILE = "validation-rules.xml";
	private static final String DEFAULT_PROP_FILE = "validation.properties";
	private String rulesFile;
	private Configuration configuration;
	private volatile static ValidationConfig instance = null;

	private ValidationConfig(String rulesFile) {
		this.rulesFile = rulesFile;
	}

	public static ValidationConfig getInstance() {
		return getInstance(DEFAULT_RULES_FILE);
	}

	public static ValidationConfig getInstance(String rulesFile) {
		if (instance != null) {
			logger.debug("Attempting to create an existing singleton. Existing singleton returned.");
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

	private void initConfiguration() {
		configuration = new Configuration();
		initValidators();
		initValidatorsMessage();
		readXML(rulesFile);
	}

	public ValidateService buildValidateService() {
		return new BaseValidateService(this);
	}

	/**
	 * Read xml group configuration.
	 */
	@SuppressWarnings("unchecked")
	private void readXML(String path) {
		logger.debug("Read validation file: " + path);
		Document document = ValidateUtils.read(path);
		Element root = document.getRootElement();
		List<Element> includeList = root.elements("include");
		if (includeList != null && !includeList.isEmpty()) {
			for (Element includeE : includeList) {
				String file = includeE.attributeValue("file");
				if (StringUtils.isBlank(file)) continue;
				this.readXML(file);
			}
		}
		this.readGroups(root);
		this.readValidators(root);
	}

	@SuppressWarnings("unchecked")
	private void readGroups(Element root) {
		List<Element> groupList = root.elements("group");
		for (Element groupElement : groupList) {
			String groupName = groupElement.attributeValue("name").trim();

			List<Field> fields = new ArrayList<>();
			List<Element> fieldList = groupElement.elements("field");
			for (Element fieldElement : fieldList) {
				String fieldName = fieldElement.attributeValue("name").trim();
				Field field = new Field();
				field.setName(fieldName);

				List<Rule> rules = new ArrayList<>();
				List<Element> ruleList = fieldElement.elements("rule");
				for (Element ruleE : ruleList) {
					String ruleName = ruleE.attributeValue("name").trim();
					String ruleMessage = ruleE.elementTextTrim("message");
					ruleMessage = ruleMessage == null ? ruleE.attributeValue("message") : ruleMessage;
					String value = ruleE.elementTextTrim("value");
					value = value == null ? ruleE.attributeValue("value") : ruleMessage;
					Rule rule = new Rule();
					rule.setName(ruleName);
					rule.setValue(value);
					rule.setMessage(ruleMessage);

					List<Element> params = ruleE.elements("param");
					for (Element paramE : params) {
						String paramName = paramE.attributeValue("name").trim();
						String paramValue = paramE.elementTextTrim("value");
						paramValue = paramValue == null ? paramE.attributeValue("value").trim() : paramValue;
						rule.addParameter(paramName, paramValue);
					}

					rules.add(rule);
				}
				field.setRules(rules);
				fields.add(field);
			}
			Group group = new Group();
			group.setName(groupName);
			group.setFields(fields);

			Map<String, Group> gp = configuration.getGroups();
			if (gp == null) {
				gp = new HashMap<>();
				configuration.setGroups(gp);
			}
			gp.put(groupName, group);
		}
	}

	@SuppressWarnings("unchecked")
	private void readValidators(Element root) {
		try {
			List<Element> validators = root.elements("validators");
			for (Element groupE : validators) {
				List<Element> fieldList = groupE.elements("validator");
				for (Element validatorE : fieldList) {
					String vName = validatorE.attributeValue("name").trim();
					String vClass = validatorE.attributeValue("class").trim();
					String message = validatorE.attributeValue("message");
					ValidatorNode v = new ValidatorNode();
					v.setName(vName);
					v.setClassName(vClass);
					Validator validator = this.instanceValidator(v);
					if (validator != null) {
						configuration.getValidators().put(vName, validator);
						configuration.getValidatorsDefaultMsg().put(vName, message);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Validator instanceValidator(ValidatorNode v) {
		Validator validator = null;
		try {
			Class<Validator> validatorClass = (Class<Validator>) Class.forName(v.getClassName());
			validator = validatorClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Instance Validator failed,class name is " + v.getClassName());
		}
		return validator;
	}

	private void initValidators() {
		Map<String, Validator> validatorMap = configuration.getValidators();
		validatorMap.put("required", new RequiredValidator());
		validatorMap.put("max", new MaxValidator());
		validatorMap.put("min", new MinValidator());
		validatorMap.put("range", new RangeValidator());
		validatorMap.put("maxlength", new MaxLengthValidator());
		validatorMap.put("minlength", new MinLengthValidator());
		validatorMap.put("rangelength", new RangeLengthValidator());
		validatorMap.put("digits", new DigitsValidator());
		validatorMap.put("number", new NumberValidator());
		validatorMap.put("regex", new RegexValidator());
		validatorMap.put("email", new EmailValidator());
		validatorMap.put("mobile", new MobileValidator());
		validatorMap.put("zip", new ZipValidator());
	}

	private void initValidatorsMessage() {
		Properties prop = ValidateUtils.readProperties(DEFAULT_PROP_FILE);
		for (Object key : prop.keySet()) {
			configuration.getValidatorsDefaultMsg().put(key.toString(), prop.getProperty(key.toString()));
		}
	}

	public Configuration getConfiguration() {
		return configuration;
	}

}
