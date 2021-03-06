package com.feiynn.validation.validators.regex;

import com.feiynn.validation.validators.RegexValidator;

/**
 * positive integer
 *
 * @author Dean
 */
public class DigitsValidator extends RegexValidator implements RegexGetter {

	@Override
	public String getRegex() {
		return "^(0|[1-9]\\d*)$";
	}
}
