package com.feiynn.validation.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;
import com.feiynn.validation.utils.ValidateUtils;

/**
 * @author Dean
 */
public class MaxLengthValidator implements Validator {
	private final static Logger logger = LoggerFactory.getLogger(MaxLengthValidator.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validate(Object object, Class propertyType,
							Object propertyValue, Rule rule) {
		String ruleValue = rule.getValue();
		if (!ValidateUtils.isConfiguredRuleValue(ruleValue, rule.getName())) {
			return true;
		}

		int ruleValueInt;
		try {
			ruleValueInt = Integer.parseInt(ruleValue);
		} catch (Exception e) {
			logger.error("The rule value must be integer.Rule name is " + rule.getName() + ".");
			return true;
		}

		if (propertyValue != null) {
			String propertyValueStr = propertyValue.toString().trim();
			if (propertyValueStr.length() <= ruleValueInt) {
				return true;
			}
		}
		return false;
	}

}
