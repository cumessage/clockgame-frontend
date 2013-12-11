package com.prosper.clockgame.frontend.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.prosper.clockgame.frontend.util.JsonParser;

import android.os.Handler;
import android.os.Message;

public class DefaultHandler extends Handler {
	
	@Override
	public void handleMessage (Message msg) {
        switch(msg.what) {  
        case 200:  
            try {
            	JsonNode responseJson = JsonParser.getMapper().readTree(msg.getData().getByteArray("response"));
            	DefaultResponse defaultResponse = new DefaultResponse(responseJson.get("opCode").asInt(), responseJson);
				doMessage(defaultResponse);
				System.out.println(responseJson.toString());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            break;  
        default:  
        	System.out.println("fail");  
            break;  
        }  
    }

	/**
	 * override to do message
	 */
	public void doMessage(DefaultResponse defaultResponse) {
		System.out.println(defaultResponse.toString());
	}
}
