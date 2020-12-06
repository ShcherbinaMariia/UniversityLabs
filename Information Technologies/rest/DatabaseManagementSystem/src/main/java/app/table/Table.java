package app.table;

import app.row.Row;
import app.schema.Schema;
import app.schema.attributes.Attribute;
import app.util.MutationResult;

import java.util.ArrayList;
import java.util.Collection;

// POJO for representing tables
public class Table {
    String name;
    Schema schema;
    ArrayList<Row> rows;

    public Table(String name) {
        this.name = name;
        this.schema = new Schema();
        this.rows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    private void addAttributeInRows(Attribute attribute) {
        for (Row row: rows) {
            row.values.put(attribute.name, attribute.getDefault());
        }
    }

    private void removeAttributeFromRows(String key) {
        for (Row row: rows) {
            row.values.remove(key);
        }
    }

    public MutationResult addAttribute(Attribute attribute) {
        MutationResult result = schema.addAttribute(attribute);
        if (result.isSuccessful()) {
            addAttributeInRows(attribute);
        }
        return result;
    }

    public MutationResult deleteAttribute(String key) {
        MutationResult result = schema.deleteAttribute(key);
        if (result.isSuccessful()) {
            removeAttributeFromRows(key);
        }
        return result;
    }

    public Collection<Attribute> listAttributes() {
        return schema.listAttributes();
    }

    public Attribute getAttribute(String key) {
        return schema.getAttribute(key);
    }

    public Collection<Row> listRows() {
        return this.rows;
    }

    public Row getRow(int index) {
        return this.rows.get(index);
    }

    public MutationResult addRow(Row row) {
        this.rows.add(row);
        return MutationResult.Success();
    }

    public MutationResult deleteRow(int index) {
        if (index < 0 || index >= this.rows.size()) {
            return MutationResult.Fail("Index out of bounds");
        }
        this.rows.remove(index);
        return MutationResult.Success();
    }

}
