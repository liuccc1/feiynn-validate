package com.feiynn.validation.test;

import com.feiynn.validation.exception.ValidateException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.feiynn.validation.config.ValidationConfig;
import org.junit.rules.ExpectedException;

/**
 * @author Dean
 */
public class TestValidationConfig {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testDefault() {
		ValidationConfig config = ValidationConfig.getInstance();
		Assert.assertNotNull(config);
	}

	/**
	 * 由于ValidationConfig是单例的，打包时执行整体单元测试，如果这个单元测试先执行，那么ValidationConfig
	 * 将没有机会再次正确初始化了，所以需要@Ignore
	 */
	@Ignore
	@Test
	public void testNoExistFile() {
		thrown.expect(ValidateException.class);
		thrown.expectMessage("File does not exist");

		String rulesFile = "validation-rules-noExist.xml"; // not exist file
		ValidationConfig.getInstance(rulesFile);
	}

}
