package com.feiynn.validation.test.vaidators;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;

/**
 * A custom validator
 *
 * @author Dean
 */
public class CustomValidator implements Validator {

	@Override
	public boolean validate(Object object, Class propertyType, Object propertyValue, Rule rule) {
		if (propertyValue != null) {
			return false;
		}
		return false;
	}

}
