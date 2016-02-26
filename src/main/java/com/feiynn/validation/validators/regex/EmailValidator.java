package com.feiynn.validation.validators.regex;

import com.feiynn.validation.validators.RegexValidator;

/** 
 * 
 * @author: Dean
 */
public class EmailValidator extends RegexValidator{
	
	protected String getRegex() {
		return "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	}

	
}
