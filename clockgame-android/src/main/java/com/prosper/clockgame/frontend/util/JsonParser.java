package com.prosper.clockgame.frontend.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {
	
	private static ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static void setMapper(ObjectMapper mapper) {
		JsonParser.mapper = mapper;
	}
	
}
