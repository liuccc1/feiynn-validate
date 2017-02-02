package com.feiynn.validation.validators.regex;

import com.feiynn.validation.validators.RegexValidator;

/**
 * @author Dean
 */
public class ZipValidator extends RegexValidator implements RegexGetter {

	@Override
	public String getRegex() {
		return "[1-9]\\d{5}(?!\\d)";
	}
}
