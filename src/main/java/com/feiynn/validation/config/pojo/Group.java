package com.feiynn.validation.config.pojo;

import java.util.List;

/** 
 * 
 * @author Dean
 */
public class Group {
	private String name;
	private List<Field> fields;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	
}
