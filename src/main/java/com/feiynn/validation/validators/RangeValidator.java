package com.feiynn.validation.validators;

import org.apache.commons.lang3.StringUtils;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;
import com.feiynn.validation.utils.ValidateUtils;

/**
 * @author Dean
 */
public class RangeValidator implements Validator {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validate(Object object, Class propertyType,
							Object propertyValue, Rule rule) {
		String ruleValue = rule.getValue();
		if (!ValidateUtils.isConfiguredRuleValue(ruleValue, rule.getName())) {
			return true;
		}
		ruleValue = ruleValue.replaceAll("\\u005B", "");
		ruleValue = ruleValue.replaceAll("]", "");
		String[] rangeArray = ruleValue.split(",");

		String propertyValueStr = ValidateUtils.null2String(propertyValue);
		try {
			if (StringUtils.isBlank(propertyValueStr)) {
				return true;
			}
			double low = Double.parseDouble(rangeArray[0]);
			double big = Double.parseDouble(rangeArray[1]);

			propertyValueStr = propertyValueStr.trim();

			double value = Double.parseDouble(propertyValueStr);
			if (value < low || value > big) {
				return false;
			}
		} catch (NumberFormatException e) {
			if (rangeArray[1].compareTo(propertyValueStr) >= 0 && rangeArray[0].compareTo(propertyValueStr) <= 0) {
				return true;
			}
		}
		return true;
	}

}
