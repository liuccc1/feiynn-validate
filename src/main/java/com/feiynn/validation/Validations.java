package com.feiynn.validation;

import com.feiynn.validation.config.ValidationConfig;

import java.util.Map;

/**
 * This is the main class for using feiynn-validate
 *
 * @author Dean
 */
public final class Validations {
	private Validations() {
	}

	public static Map<String, String> validate(Object bean, String groupName) {
		return validate(bean, groupName, ValidationConfig.DEFAULT_RULES_FILE);
	}

	public static Map<String, String> validate(Object bean, String groupName, String rulesFile) {
		return ValidationConfig.getInstance(rulesFile).buildValidateService().validate(bean, groupName);
	}
}
