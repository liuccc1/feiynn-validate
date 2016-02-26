package com.feiynn.validation;

import com.feiynn.validation.config.pojo.Rule;


/** 
 * 
 * @author: Dean
 */
public interface Validator {

	/**
	 * 
	 * @param object
	 * @param propertyType
	 * @param propertyValue
	 * @param rule
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean validate(Object object,Class propertyType,Object propertyValue,Rule rule);
}
