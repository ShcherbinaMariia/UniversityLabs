package app.table;

import app.schema.attributes.Attribute;
import app.schema.attributes.AttributeSerializer;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TableSerializer implements JsonSerializer<Table> {

    @Override
    public JsonElement serialize(Table table, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name", table.getName());
        JsonArray jsonArray = new JsonArray();
        AttributeSerializer attributeSerializer = new AttributeSerializer();
        for (Attribute attribute: table.listAttributes()) {
            jsonArray.add(attributeSerializer.serialize(attribute, Attribute.class, jsonSerializationContext));
        }
        result.add("attributes", jsonArray);
        result.add("rows", jsonSerializationContext.serialize(table.listRows()));
        return result;
    }
}
