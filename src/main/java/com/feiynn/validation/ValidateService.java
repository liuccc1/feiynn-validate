package com.feiynn.validation;

import java.util.Map;

/**
 * @author Dean
 */
public interface ValidateService {

	/**
	 * @param bean      The object to be validated, can be map or Java Bean
	 * @param groupName The name attribute of the group node in the configuration file
	 * @return a map that key is property name,value is validate result
	 */
	Map<String, String> validate(Object bean, String groupName);
}	
