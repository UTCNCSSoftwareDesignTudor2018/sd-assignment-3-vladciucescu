package client.util;

import java.io.IOException;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.entity.Writer;

public class WriterAdapter {
    
    public WriterAdapter() {}

    public Writer getWriter(Document document) {
    	if (document == null) {
    		return null;
    	}
        return new Writer(
                document.getString("username"),
                document.getString("name"),
                document.getString("surname")
        	);
    }
    
    public Writer getWriter(String json) throws JsonParseException, JsonMappingException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Writer.class);
    }

    public String getJson(Writer writer) throws JsonProcessingException {
    	if (writer == null) {
    		return null;
    	}
    	ObjectMapper mapper = new ObjectMapper();
    	String json = mapper.writeValueAsString(writer);
    	return json;
    }
    
    public Document getDocument(Writer writer) {
    	if (writer == null) {
    		return null;
    	}
        return new Document()
                .append("username", writer.getUsername())
                .append("name", writer.getName())
                .append("surname", writer.getSurname());
    }
    
}
