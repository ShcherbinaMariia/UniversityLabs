package app.util;

import app.row.values.Value;
import app.row.values.ValueSerializer;
import app.table.Table;
import app.table.TableSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import app.schema.attributes.Attribute;
import app.schema.attributes.AttributeSerializer;

public class Serializer {
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Table.class, new TableSerializer())
            .registerTypeAdapter(Value.class, new ValueSerializer())
            .registerTypeAdapter(Attribute.class, new AttributeSerializer())
            .create();

    public static String Serialize(Object object) {
        return gson.toJson(object);
    }
}
