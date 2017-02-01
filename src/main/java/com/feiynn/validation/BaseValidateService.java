package com.feiynn.validation;

import com.feiynn.validation.config.ValidationConfig;
import com.feiynn.validation.config.pojo.Configuration;
import com.feiynn.validation.config.pojo.Field;
import com.feiynn.validation.config.pojo.Group;
import com.feiynn.validation.config.pojo.Rule;
import com.feiynn.validation.utils.ValidateUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dean
 */
public class BaseValidateService implements ValidateService {
	private final static Logger logger = LoggerFactory.getLogger(BaseValidateService.class);

	private ValidationConfig validationConfig;

	public BaseValidateService(ValidationConfig validationConfig) {
		this.validationConfig = validationConfig;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Map<String, String> validate(Object object, String groupName) {
		Map<String, String> results = new LinkedHashMap<>();
		if (object == null) {
			return results;
		}

		Configuration configuration = validationConfig.getConfiguration();
		Group group = configuration.getGroup(groupName);
		if (group == null) {
			logger.warn("The groupName '" + groupName + "' is not found in the configuration file.");
			return results;
		}
		Map<String, Validator> validators = configuration.getValidators();
		if (validators == null || validators.isEmpty()) {
			return results;
		}
		List<Field> fields = group.getFields();
		if (fields == null || fields.isEmpty()) {
			return results;
		}
		for (Field field : fields) {
			String fieldName = field.getName();
			Object propertyValue = null;
			Class propertyType = null;
			List<Rule> rules = field.getRules();
			if (rules == null || rules.isEmpty()) continue;
			try {
				if (object instanceof Map) {
					Map<String, Object> map = (Map<String, Object>) object;
					propertyValue = map.get(fieldName);
					propertyType = propertyValue != null ? propertyValue.getClass() : null;
				} else {
					propertyValue = PropertyUtils.getProperty(object, fieldName);
					propertyType = PropertyUtils.getPropertyType(object, fieldName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				for (Rule rule : rules) {
					String ruleName = rule.getName();

					Validator validator = validators.get(ruleName);
					if (validator == null) continue;
					boolean isValidatePass = validator.validate(object, propertyType, propertyValue, rule);
					if (!isValidatePass) {
						String messageTemplate = rule.getMessage();
						if (StringUtils.isBlank(messageTemplate)) {
							messageTemplate = configuration.getValidatorsDefaultMsg().get(ruleName);
						}
						String message = ValidateUtils.formatTemplateMsg(messageTemplate, rule.getValue(), rule.getName());
						results.put(fieldName, message);
						break;
					}
				}
			}
		}
		return results;
	}

}
