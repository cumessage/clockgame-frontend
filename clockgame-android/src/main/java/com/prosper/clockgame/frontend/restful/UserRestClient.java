package com.prosper.clockgame.frontend.restful;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;

import android.os.Handler;

import com.loopj.android.http.TextHttpResponseHandler;
import com.prosper.clockgame.frontend.util.CommonUtil;
import com.prosper.clockgame.frontend.util.JsonParser;

public class UserRestClient {
	
	public static void login(String email, String password, final Handler handler) throws Exception {
		String postString = JsonParser.getMapper().writeValueAsString(
				CommonUtil.getMap("email", email, "password", password));
		StringEntity postEntity = new StringEntity(postString);
        RestClient.post("user/login", postEntity, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            	handler.sendMessage(CommonUtil.getMessage(statusCode, responseBody));
            }
        });
    }
	
	public static void register(String email, String password, final Handler handler) throws Exception {
		String postString = JsonParser.getMapper().writeValueAsString(
				CommonUtil.getMap("email", email, "password", password));
		StringEntity postEntity = new StringEntity(postString);
        RestClient.post("user", postEntity, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            	handler.sendMessage(CommonUtil.getMessage(statusCode, responseBody));
            }
        });
    }
	
	public static void getUserInfo(int id, final Handler handler) throws JSONException {
		System.out.println("start");
        RestClient.get("user/" + id +"/info", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Pull out the first event on the public timeline
            	handler.sendEmptyMessage(0);
            	System.out.println("get");
				System.out.println(new String(responseBody));
            }
        });
    }
	
	public static void getUserGameListJoined(long id, final Handler handler) throws JSONException {
		System.out.println("start");
        RestClient.get("user/" + id +"/game/joined", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            	handler.sendMessage(CommonUtil.getMessage(statusCode, responseBody));
            }
        });
    }
	
	public static void getUserGameListJoinable(long id, final Handler handler) throws JSONException {
		System.out.println("start");
        RestClient.get("user/" + id +"/game/joinable", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            	handler.sendMessage(CommonUtil.getMessage(statusCode, responseBody));
            }
        });
    }
}
