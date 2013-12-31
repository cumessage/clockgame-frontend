package com.prosper.clockgame.frontend.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RunStepTemplate extends StepTemplate {
	
	private int distance;
	
	private float[] startPoint = new float[2];
	
	private float[] endPoint = new float[2];
	
	public void init(JSONObject jsonObject) {
		try {
			distance = jsonObject.getInt("distance");
			JSONArray path = jsonObject.getJSONArray("path");
			startPoint[0] = path.getInt(0);
			startPoint[1] = path.getInt(1);
			endPoint[0] = path.getInt(2);
			endPoint[1] = path.getInt(3);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public float[] getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(float[] startPoint) {
		this.startPoint = startPoint;
	}

	public float[] getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(float[] endPoint) {
		this.endPoint = endPoint;
	}

}
