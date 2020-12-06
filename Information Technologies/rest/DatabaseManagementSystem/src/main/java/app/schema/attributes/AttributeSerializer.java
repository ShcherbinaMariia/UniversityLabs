package app.schema.attributes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class AttributeSerializer implements JsonSerializer<Attribute> {

    @Override
    public JsonElement serialize(Attribute attribute, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("type", attribute.getType().toString());
        result.addProperty("name", attribute.name);
        return result;
    }
}
