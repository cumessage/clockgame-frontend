package com.prosper.clockgame.frontend.bean;

import org.json.JSONObject;

public abstract class StepTemplate {
	
	private int id;
	
	private String name;
	
	private String type;
	
	public abstract void init(JSONObject jsonObject);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
