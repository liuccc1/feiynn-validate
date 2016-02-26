package com.feiynn.validation.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;
import com.feiynn.validation.utils.ValidateUtils;

/** 
 * 
 * @author: Dean
 */
public class MinLengthValidator implements Validator {

	final static Logger logger = LoggerFactory.getLogger(MinLengthValidator.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validate(Object object, Class propertyType,
			Object propertyValue, Rule rule) {
		String ruleValue = rule.getValue(); 
		if(!ValidateUtils.isConfigedRuleValue(ruleValue,rule.getName())){
			return true;
		}
		
		int ruleValueInt = 0;
		try {
			ruleValueInt = Integer.parseInt(ruleValue);
		} catch (Exception e) {
			logger.error("The rule value must be integer.Rule name is " + rule.getName() + ".");
			return true;
		}
		
		if(propertyValue != null){
			String propertyValueStr = propertyValue.toString().trim();
			
			if(propertyValueStr.length() >= ruleValueInt){
				return true;
			}
			
		}
		return false;
	}

}
