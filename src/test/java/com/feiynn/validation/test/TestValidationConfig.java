package com.feiynn.validation.test;

import org.junit.Assert;
import org.junit.Test;

import com.feiynn.validation.config.ValidationConfig;

/** 
 * 
 * @author: Dean
 */
public class TestValidationConfig {

	@Test
	public void test(){
		ValidationConfig config =  ValidationConfig.getInstance();
//		String rulesFile = "validation-rules.xml";
//		String rulesFile = "validation-rules-noexist.xml";
//		ValidationConfig config =  ValidationConfig.getInstance(rulesFile);
		Assert.assertNotNull(config);
	}
	
}
