package com.prosper.clockgame.frontend.common;

import com.fasterxml.jackson.databind.JsonNode;

public class DefaultResponse {
	
	private int opCode;
	
	private JsonNode json;
	
	public DefaultResponse(int opCode, JsonNode json) {
		setOpCode(opCode);
		setJson(json);
	}

	public int getOpCode() {
		return opCode;
	}

	public void setOpCode(int opCode) {
		this.opCode = opCode;
	}

	public JsonNode getJson() {
		return json;
	}

	public void setJson(JsonNode json) {
		this.json = json;
	}

	
}
