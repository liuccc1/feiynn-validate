package com.feiynn.validation.config.pojo;

import java.util.Map;

/** 
 * 
 * @author: Dean
 */
public class Rule {
	private String name;
	private String value;
	private String message;
	private Map<String,String> param;
	
	public void addParameter(String name, String value){
		this.param.put(name,value);
	}
	
	public String getParameter(String name){
		return this.param.get(name);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
