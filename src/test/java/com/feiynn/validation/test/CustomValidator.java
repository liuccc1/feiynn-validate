package com.feiynn.validation.test;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;

/** 
 * 
 * @author: Dean
 */
public class CustomValidator implements Validator {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean validate(Object object, Class propertyType,
			Object propertyValue, Rule rule) {
		if(propertyValue != null){
			return false;
		}
		return false;
	}

}
