package com.feiynn.validation.utils;

import com.feiynn.validation.exception.ValidateException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Utils
 *
 * @author Dean
 */
public class ValidateUtils {
	private final static Logger logger = LoggerFactory.getLogger(ValidateUtils.class);

	public static String formatTemplateMsg(String template, String ruleValue, String ruleName) {
		if (StringUtils.isBlank(template)) {
			throw new IllegalArgumentException("Default message not configured.");
		}
		if (!template.contains("{0}")) {
			return template;
		} else {
			if (!ValidateUtils.isConfiguredRuleValue(ruleValue, ruleName)) {
				return template;
			}
		}

		String[] ruleValueArray;
		if (ruleValue.contains(",")) {
			ruleValue = ruleValue.replaceAll("\\u005B", "");
			ruleValue = ruleValue.replaceAll("]", "");
			ruleValueArray = ruleValue.split(",");
		} else {
			ruleValueArray = new String[]{ruleValue};
		}

		return formatTemplateMsg(template, ruleValueArray);
	}

	private static String formatTemplateMsg(String template, String[] ruleValueArray) {
		for (int i = 0; i < ruleValueArray.length; i++) {
			template = template.replace("{" + i + "}", ruleValueArray[i]);
		}
		return template;
	}

	public static Document read(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			throw new IllegalArgumentException("FileName is null,please check.");
		}
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(getUrl(fileName));
		} catch (DocumentException e) {
			throw new ValidateException(e);
		}
		return document;
	}

	private static URL getUrl(String fileName) {
		URL url = ValidateUtils.class.getResource("/" + fileName);
		if (url == null) {
			throw new ValidateException("File does not exist,fileName is : " + fileName);
		}
		return url;
	}

	public static Properties readProperties(String fileName) {
		Properties prop = new Properties();
		InputStream inStream = Object.class.getResourceAsStream("/" + fileName);
		try {
			prop.load(inStream);
		} catch (IOException e) {
			throw new ValidateException(e);
		}
		return prop;
	}

	public static String null2String(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static boolean isConfiguredRuleValue(String ruleValue, String ruleName) {
		if (StringUtils.isBlank(ruleValue)) {
			logger.error("The rule is not configured with the value attribute,please check configuration.Rule name is " + ruleName + ".");
			return false;
		}
		return true;
	}
}