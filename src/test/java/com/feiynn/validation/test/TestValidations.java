package com.feiynn.validation.test;

import com.feiynn.validation.Validations;
import com.feiynn.validation.test.model.TestUser;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/** 
 * 
 * @author Dean
 */
public class TestValidations {

	
	@Test
		public void validateMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", " Dean");
		map.put("password", "  "); //space
		map.put("age", 1000);
		map.put("salary", 5000);
		map.put("email", "myEmail@");
		map.put("userMobile", "1898046123");
		map.put("remark", "今天是2016年2月24日Wednesday");
		System.out.println(Validations.INSTANCE.validate(map, "userAdd"));
	}
	
	@Test
	public void validateJavaBean(){
		TestUser user = new TestUser();
		user.setUsername(" Dean");
		user.setPassword("   ");
		user.setAge(1000);
		user.setSalary(5000);
		user.setEmail("myEmail@");
		user.setUserMobile("1898046123");
		user.setRemark("今天是2016年2月24日Wednesday");
		System.out.println(Validations.INSTANCE.validate(user, "userAdd"));
	}
}
