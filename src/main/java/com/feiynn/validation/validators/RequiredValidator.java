package com.feiynn.validation.validators;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.lang3.ClassUtils;

import com.feiynn.validation.Validator;
import com.feiynn.validation.config.pojo.Rule;

/** 
 * 
 * @author: Dean
 */
public class RequiredValidator implements Validator{
	
	@SuppressWarnings("rawtypes")
	public boolean validate(Object object,Class propertyType,Object propertyValue,Rule rule) {
		boolean isPassFlag = false;
		if(propertyValue == null) {
			isPassFlag = false;
		}
		if(ClassUtils.isAssignable(propertyType, Object[].class)) {
			isPassFlag =  (propertyValue != null && ((Object[])propertyValue).length > 0);
		}else if (propertyType == String.class){
			isPassFlag =  (propertyValue != null && ((String)propertyValue).trim().length() > 0);
		}else if (propertyType == Timestamp.class || propertyType == Date.class){
			isPassFlag =  (propertyValue != null);
		}else{
			isPassFlag = true;
		}
		return isPassFlag;
	}
}
