package com.prosper.clockgame.frontend.restful;

import org.apache.http.HttpEntity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class RestClient {
	private static final String BASE_URL = "http://118.244.224.234:8080/clockgame-service/";
	
	private static AsyncHttpClient client = new AsyncHttpClient();

	  public static void get(String url, AsyncHttpResponseHandler responseHandler) {
		  System.out.println(getAbsoluteUrl(url));
	      client.get(getAbsoluteUrl(url), null, responseHandler);
	  }

	  public static void post(String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
	      client.post(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }
}
