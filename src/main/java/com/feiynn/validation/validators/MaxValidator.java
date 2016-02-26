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
public class MaxValidator implements Validator {
	final static Logger logger = LoggerFactory.getLogger(MaxValidator.class);

	@SuppressWarnings("rawtypes")
	public boolean validate(Object object,Class propertyType,Object propertyValue,Rule rule) {
		String ruleValue = rule.getValue(); 
		if(!ValidateUtils.isConfigedRuleValue(ruleValue,rule.getName())){
			return true;
		}
		try {
			if(propertyValue == null){
				return true;
			}
			double ruleValueDouble = Double.parseDouble(ruleValue);
			double propertyValueDouble = Double.parseDouble(propertyValue.toString());
			if(ruleValueDouble >= propertyValueDouble){
				return true;
			}
		} catch (NumberFormatException e) {
			if(ruleValue.compareTo(propertyValue.toString()) >= 0){
				return true;
			}
		}
		return false;
	}

}
