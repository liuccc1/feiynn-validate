package com.feiynn.validation.validators;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;
import com.feiynn.validation.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author Dean
 */
public class RegexValidator implements Validator {

	private String regex = "";

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validate(Object object, Class propertyType,
							Object propertyValue, Rule rule) {
		String propertyValueStr = ValidateUtils.null2String(propertyValue);
		if (StringUtils.isBlank(propertyValueStr)) {
			return true;
		}
		String ruleValue = rule.getValue();
		if (StringUtils.isBlank(getRegex())) {
			if (!ValidateUtils.isConfiguredRuleValue(ruleValue, rule.getName())) {
				return true;
			}
			setRegex(ruleValue);
		}
		return Pattern.matches(getRegex(), propertyValueStr);
	}

	protected String getRegex() {
		return regex;
	}

	protected void setRegex(String regex) {
		this.regex = regex;
	}


}
