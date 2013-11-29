package com.prosper.clockgame.frontend.common;

import com.fasterxml.jackson.databind.JsonNode;

public class DefaultResponse {
	
	private int opCode;
	
	private JsonNode response;
	
	public DefaultResponse(int opCode, JsonNode response) {
		setOpCode(opCode);
		setResponse(response);
	}

	public int getOpCode() {
		return opCode;
	}

	public void setOpCode(int opCode) {
		this.opCode = opCode;
	}

	public JsonNode getResponse() {
		return response;
	}

	public void setResponse(JsonNode response) {
		this.response = response;
	}
	
}
