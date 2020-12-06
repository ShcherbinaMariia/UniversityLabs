package app.row.values;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ValueSerializer implements JsonSerializer<Value> {
    @Override
    public JsonElement serialize(Value value, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(value.toString());
    }
}
