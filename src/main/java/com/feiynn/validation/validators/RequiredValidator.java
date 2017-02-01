package com.feiynn.validation.validators;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;
import org.apache.commons.lang3.ClassUtils;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Dean
 */
public class RequiredValidator implements Validator {

	@SuppressWarnings("rawtypes")
	public boolean validate(Object object, Class propertyType, Object propertyValue, Rule rule) {
		if (propertyValue == null) {
			return false;
		}
		boolean isPassFlag;
		if (ClassUtils.isAssignable(propertyType, Object[].class)) {
			isPassFlag = ((Object[]) propertyValue).length > 0;
		} else if (propertyType == String.class) {
			isPassFlag = ((String) propertyValue).trim().length() > 0;
		} else {
			isPassFlag = !(propertyType == Timestamp.class || propertyType == Date.class);
		}
		return isPassFlag;
	}
}
