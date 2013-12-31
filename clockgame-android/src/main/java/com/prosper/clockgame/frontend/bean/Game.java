package com.prosper.clockgame.frontend.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Game {

	private int id;

	private String name;

	private String desc;

	private String map;

	private List<StepTemplate> stepList;

	public Game(JSONObject jsonObject) {
		try {
			this.id = jsonObject.getInt("id");
			this.name = jsonObject.getString("name");
			this.desc = jsonObject.getString("desc");
			this.map = jsonObject.getString("id");
			stepList = new ArrayList<StepTemplate>();
			
			JSONArray stepJsonArray = jsonObject.getJSONArray("steps");
			for (int i = 0; i < stepJsonArray.length(); i ++) {
				JSONObject stepJsonObject = stepJsonArray.getJSONObject(i);
				String type = stepJsonObject.getString("type");
				@SuppressWarnings("unchecked")
				Class<? extends StepTemplate> clazz = (Class<? extends StepTemplate>) Class.forName(System.PACKAGE_NAME + "." + type);
				StepTemplate step = clazz.newInstance();
				step.init(stepJsonObject);
				stepList.add(step);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
}
