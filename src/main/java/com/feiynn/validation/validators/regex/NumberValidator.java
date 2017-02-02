package com.feiynn.validation.validators.regex;

import com.feiynn.validation.validators.RegexValidator;

/**
 * decimal number
 *
 * @author Dean
 */
public class NumberValidator extends RegexValidator implements RegexGetter {

	@Override
	public String getRegex() {
		return "-?(0|[1-9]\\d*)(\\.\\d+)?";
	}
}
