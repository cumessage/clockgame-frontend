package com.prosper.clockgame.frontend.restful;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import android.os.Handler;

import com.loopj.android.http.TextHttpResponseHandler;
import com.prosper.clockgame.frontend.util.CommonUtil;
import com.prosper.clockgame.frontend.util.JsonParser;

public class GameRestClient {
	
	public static void getInfo(long id, final Handler handler) throws Exception {
		RestClient.get("game/" + id +"/info", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            	handler.sendMessage(CommonUtil.getMessage(statusCode, responseBody));
            }
        });
    }
	
	public static void getGameDataByGameIdAndUserId(long gameId, long userId, final Handler handler) throws Exception {
		RestClient.get("game/" + gameId + "/data/" + userId , new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            	handler.sendMessage(CommonUtil.getMessage(statusCode, responseBody));
            }
        });
    }
	
	public static void getGameDataByGameIdAndWithOutUserId(long gameId, long userId, final Handler handler) throws Exception {
		RestClient.get("game/" + gameId + "/data/without-" + userId , new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            	handler.sendMessage(CommonUtil.getMessage(statusCode, responseBody));
            }
        });
    }
	
	public static void join(long gameId, long userId, final Handler handler) throws Exception {
		String postString = JsonParser.getMapper().
				writeValueAsString(CommonUtil.getMap("userId", Long.toString(userId)));
		StringEntity postEntity = new StringEntity(postString);
        RestClient.post("game/" + Long.toString(gameId) + "/member", postEntity, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            	handler.sendMessage(CommonUtil.getMessage(statusCode, responseBody));
            }
        });
    }

}
