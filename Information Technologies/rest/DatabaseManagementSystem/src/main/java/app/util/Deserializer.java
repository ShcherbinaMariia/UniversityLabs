package app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import app.schema.attributes.Attribute;
import app.schema.attributes.AttributeDeserializer;
import app.table.Table;
import app.table.TableDeserializer;

public class Deserializer {
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Table.class, new TableDeserializer())
            .registerTypeAdapter(Attribute.class, new AttributeDeserializer())
            .create();

    public static Gson getGson() {
        return gson;
    }
}
