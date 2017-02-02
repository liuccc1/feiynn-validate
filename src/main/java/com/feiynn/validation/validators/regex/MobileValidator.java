package com.feiynn.validation.validators.regex;

import com.feiynn.validation.validators.RegexValidator;

/**
 * @author Dean
 */
public class MobileValidator extends RegexValidator implements RegexGetter {

	@Override
	public String getRegex() {
		return "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	}
}
