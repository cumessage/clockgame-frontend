package com.prosper.clockgame.frontend.restful;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserRestClient {

	public static void getLogin() throws JSONException {
        RestClient.get("user/login", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray timeline) {
                // Pull out the first event on the public timeline
                JSONObject firstEvent;
				try {
					firstEvent = (JSONObject) timeline.get(0);
					System.out.println(firstEvent.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        });
    }
	
	public static void getUserInfo(int id) throws JSONException {
        RestClient.get("user/" + id +"/info", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray timeline) {
                // Pull out the first event on the public timeline
                JSONObject firstEvent;
				try {
					firstEvent = (JSONObject) timeline.get(0);
					System.out.println(firstEvent.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        });
    }
}
