package com.feiynn.validation.config.pojo;

import java.util.HashMap;
import java.util.Map;

import com.feiynn.validation.Validator;

/** 
 * 
 * @author: Dean
 */
public class Configuration {
	private Map<String,Group> groups;
	
	private Map<String,Validator> validators = new HashMap<String,Validator>();
	
	private Map<String,String> validatorsDefaultMsg = new HashMap<String,String>();
	
	public void addGroup(String name, Group group){
		this.groups.put(name, group);
	}
	
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

	public void setValidatorsDefaultMsg(Map<String, String> validatorsDefaultMsg) {
		this.validatorsDefaultMsg = validatorsDefaultMsg;
	}
	
	
	
}
