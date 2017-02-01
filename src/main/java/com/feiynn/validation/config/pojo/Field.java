package com.feiynn.validation.config.pojo;

import java.util.List;

/** 
 * 
 * @author Dean
 */
public class Field {
	private String name;
	
	private List<Rule> rules;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	
}
