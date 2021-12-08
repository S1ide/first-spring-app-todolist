package main.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class CustomJsonDeserializer extends JsonDeserializer<TodoItem> {
    @Override
    public TodoItem deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        if (node.get("id") != null){
            return new TodoItem(node.get("id").asText(), node.get("name").asText(), node.get("date").asText());
        }
        else return new TodoItem(node.get("name").asText(), node.get("date").asText());
    }
}
