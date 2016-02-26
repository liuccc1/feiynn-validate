package com.feiynn.validation.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feiynn.validation.ValidateService;
import com.feiynn.validation.config.ValidationConfig;


/** 
 * 
 * @author: Dean
 */
public class TestFeiynnValidation {

	
	@Test
	public void testMap(){
		ValidateService validateService =  ValidationConfig.getInstance().buildValidateService();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", " aaaaa");
		map.put("password", "  "); //space
		map.put("age", 1000);
		map.put("salary", 5000);
		map.put("email", "liuccc");
		map.put("userMobile", "1898046123");
		map.put("remark", "今天是2016年2月24日Wednesday");
		System.out.println(validateService.validate(map, "userAdd"));
	}
	
	@Test
	public void testJavaBean(){
		ValidateService validateService =  ValidationConfig.getInstance().buildValidateService();
		TestUser user = new TestUser();
		user.setUsername(" aaaaa");
		user.setPassword("   ");
		user.setAge(1000);
		user.setSalary(5000);
		user.setEmail("liuccc");
		user.setUserMobile("1898046123");
		user.setRemark("今天是2016年2月24日Wednesday");
		System.out.println(validateService.validate(user, "userAdd"));
	}
	
	
		
}
