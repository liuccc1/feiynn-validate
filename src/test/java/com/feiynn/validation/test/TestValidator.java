package com.feiynn.validation.test;

import com.feiynn.validation.Validations;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dean
 */
public class TestValidator {

	@Test
	public void testDigits() {
		Map<String, Object> map = new HashMap<>();
		map.put("test1", 66);
		map.put("test2", "0");
		map.put("test3", "-1");
		map.put("test4", "1.1");
		map.put("test5", "-1.1");
		map.put("test6", "01");
		Map<String, String> resultMap = Validations.INSTANCE.validate(map, "testDigits");
		System.out.println("testDigits:" + resultMap);

		Assert.assertNull(resultMap.get("test1"));
		Assert.assertNull(resultMap.get("test2"));

		Assert.assertNotNull(resultMap.get("test3"));
		Assert.assertNotNull(resultMap.get("test4"));
		Assert.assertNotNull(resultMap.get("test5"));
		Assert.assertNotNull(resultMap.get("test6"));

	}

	@Test
	public void testNumber() {
		Map<String, Object> map = new HashMap<>();
		map.put("test1", "66");
		map.put("test2", "0");
		map.put("test3", "-1");
		map.put("test4", "1.1");
		map.put("test5", "-1.1");
		map.put("test6", "01");

		Map<String, String> resultMap = Validations.INSTANCE.validate(map, "testNumber");
		System.out.println("testNumber:" + resultMap);

		Assert.assertNull(resultMap.get("test1"));
		Assert.assertNull(resultMap.get("test2"));
		Assert.assertNull(resultMap.get("test3"));
		Assert.assertNull(resultMap.get("test4"));
		Assert.assertNull(resultMap.get("test5"));

		Assert.assertNotNull(resultMap.get("test6"));
	}

	@Test
	public void testMaxLength() {
		Map<String, Object> map = new HashMap<>();
		map.put("test1", "a");
		map.put("test2", "aaaaa");
		map.put("test3", "aaaaaaaaaa");
		map.put("test4", "a");
		map.put("test5", "a");
		map.put("test6", "a");
		Map<String, String> resultMap = Validations.INSTANCE.validate(map, "testMaxLength");
		System.out.println("testMaxLength:" + resultMap);
	}

	@Test
	public void testMax() {
		Map<String, Object> map = new HashMap<>();
		map.put("test1", "90");
		map.put("test2", "100");
		map.put("test3", "a");
		map.put("test4", "a");
		map.put("test5", "c");
		Map<String, String> resultMap = Validations.INSTANCE.validate(map, "testMax");
		System.out.println("testMax:" + resultMap);
	}

	@Test
	public void testRegex() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test1", "aaa");
		Map<String, String> resultMap = Validations.INSTANCE.validate(map, "testRegex");
		System.out.println("testRegex:" + resultMap);
		Assert.assertNotNull(resultMap.get("test1"));
	}
}
