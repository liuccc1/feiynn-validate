package com.feiynn.validation.validators.regex;

import com.feiynn.validation.validators.RegexValidator;

/** 
 * 
 * @author: Dean
 */
public class ZipValidator extends RegexValidator {
	protected String getRegex() {
		return "[1-9]\\d{5}(?!\\d)";
	}
}
