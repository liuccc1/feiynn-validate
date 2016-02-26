package com.feiynn.validation.validators;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;
import com.feiynn.validation.utils.ValidateUtils;

/** 
 * 
 * @author: Dean
 */
public class RangeLengthValidator implements Validator {

	final static Logger logger = LoggerFactory.getLogger(RangeLengthValidator.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validate(Object object, Class propertyType,
			Object propertyValue, Rule rule) {
		String ruleValue = rule.getValue(); 
		if(!ValidateUtils.isConfigedRuleValue(ruleValue,rule.getName())){
			return true;
		}
		ruleValue = ruleValue.replaceAll("\\u005B", "");
		ruleValue = ruleValue.replaceAll("]", "");
		String[] rangeArray = ruleValue.split(",");
		
		String propertyValueStr = ValidateUtils.null2String(propertyValue);
		try {
			if(StringUtils.isBlank(propertyValueStr)){
				return true;
			}
			int low = Integer.parseInt(rangeArray[0]);
			int big = Integer.parseInt(rangeArray[1]);
			
			int value = propertyValueStr.length();
			if(value < low || value > big){
				return false;
			}
		} catch (NumberFormatException e) {
			logger.error("The rule value must be integer.Rule name is " + rule.getName() + ".");
			return true;
		}
		return true;
	}

}
