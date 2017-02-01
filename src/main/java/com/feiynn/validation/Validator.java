package com.feiynn.validation;

import com.feiynn.validation.config.pojo.Rule;


/**
 * @author Dean
 */
public interface Validator {
	boolean validate(Object object, Class propertyType, Object propertyValue, Rule rule);
}
