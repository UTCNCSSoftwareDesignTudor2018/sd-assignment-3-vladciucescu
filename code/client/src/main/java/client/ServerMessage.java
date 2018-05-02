package client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum ServerMessage {
	SERVER_INFO,
	SERVER_CLOSED,
	SERVER_STOPPED_LISTENING,
	SERVER_STARTED_LISTENING,
	CLIENT_DISCONNECT,
	USERNAME,
	INVALID_USERNAME,
	PASSWORD,
	INVALID_PASSWORD,
	WRITER,
	GET_ARTICLE,
	GET_ALL_ARTICLES,
	CREATE_ARTICLE,
	DELETE_ARTICLE,
	REFRESH_ARTICLES;
	
	public static String toJson(ServerMessage message) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(message);
	}
	
	public static ServerMessage toMessage(String s) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(s, ServerMessage.class);
	}
}
