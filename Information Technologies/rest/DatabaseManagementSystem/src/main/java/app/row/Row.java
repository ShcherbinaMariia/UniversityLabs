package app.row;

import app.row.values.Value;
import java.util.HashMap;

public class Row {
    public HashMap<String, Value> values;

    public Row(HashMap<String, Value> values) {
        this.values = values;
    }
}
