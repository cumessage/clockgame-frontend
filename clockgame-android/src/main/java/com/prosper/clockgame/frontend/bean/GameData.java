package com.prosper.clockgame.frontend.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.SparseArray;

public class GameData {

	private int userId;
	
	private SparseArray<StepData> stepDataMap;
	
	public GameData(JSONArray jsonArray) {
		stepDataMap = new SparseArray<GameData.StepData>();
		for (int i = 0; i < jsonArray.length(); i ++) {
			StepData stepData = null;
			try {
				stepData = new StepData(jsonArray.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			stepDataMap.put(stepData.getId(), stepData);
		}
	}
	
	public static class StepData {
		
		private int id;
		
		private String value;
		
		private int isDone;
		
		public StepData(JSONObject jsonObject) {
			try {
				id = jsonObject.getInt("step");
				value = jsonObject.getString("value");
				isDone = jsonObject.getInt("isDone");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getIsDone() {
			return isDone;
		}

		public void setIsDone(int isDone) {
			this.isDone = isDone;
		}
	}
	
	
	
}
