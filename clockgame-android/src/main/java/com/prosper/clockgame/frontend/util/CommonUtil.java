package com.prosper.clockgame.frontend.util;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;

public class CommonUtil {
	
	public static Map<String, String> getMap(String... args) {
		if (args.length > 0 && args.length % 2 == 0) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < args.length; i = i + 2) {
				map.put(args[i], args[i + 1]);
			}
			return map;
		} else {
			throw new RuntimeException("args is not pair");
		}
	}
	
	public static Message getMessage(int statusCode, byte[] responseBody) {
    	Message message = new Message();
    	message.what = statusCode;

    	Bundle bundle = new Bundle(1);
    	bundle.putByteArray("response", responseBody);
    	message.setData(bundle);
    	return message;
	}
	
}
