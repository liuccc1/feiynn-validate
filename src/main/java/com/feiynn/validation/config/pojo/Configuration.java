package com.feiynn.validation.config.pojo;

import com.feiynn.validation.Validator;

import java.util.HashMap;
import java.util.Map;

/** 
 * 
 * @author Dean
 */
public class Configuration {
	private Map<String,Group> groups;
	
	private Map<String,Validator> validators = new HashMap<>();
	
	private Map<String,String> validatorsDefaultMsg = new HashMap<>();
	
	public Group getGroup(String name){
		return this.groups.get(name);
	}

	public Map<String, Group> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, Group> groups) {
		this.groups = groups;
	}

	public Map<String, Validator> getValidators() {
		return validators;
	}

	public void setValidators(Map<String, Validator> validators) {
		this.validators = validators;
	}

	public Map<String, String> getValidatorsDefaultMsg() {
		return validatorsDefaultMsg;
	}

}
