package app.schema.attributes;

import com.google.gson.*;
import app.schema.attributes.types.Types;

import java.lang.reflect.Type;

public class AttributeDeserializer implements JsonDeserializer<Attribute> {
    @Override
    public Attribute deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String name = jsonObject.getAsJsonPrimitive("name").getAsString();
        String attributeType = jsonObject.getAsJsonPrimitive("type").getAsString();
        return Attribute.getAttribute(name, Types.valueOf(attributeType));
    }
}
