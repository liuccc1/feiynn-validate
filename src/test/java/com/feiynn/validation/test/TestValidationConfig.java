package com.feiynn.validation.test;

import org.junit.Assert;
import org.junit.Test;

import com.feiynn.validation.config.ValidationConfig;

/** 
 * 
 * @author Dean
 */
public class TestValidationConfig {

	@Test
	public void testDefault(){
		ValidationConfig config =  ValidationConfig.getInstance();
		Assert.assertNotNull(config);
	}

	@Test
	public void testNoExistFile(){
		String rulesFile = "validation-rules-noExist.xml"; // not exist file
		ValidationConfig.getInstance(rulesFile);
	}

}
