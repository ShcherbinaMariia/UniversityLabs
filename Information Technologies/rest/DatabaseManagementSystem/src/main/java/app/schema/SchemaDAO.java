package app.schema;

import app.schema.attributes.Attribute;
import app.table.Table;
import app.util.BasicDAO;
import app.util.MutationResult;

import java.util.Collection;

public class SchemaDAO implements BasicDAO<String, Attribute> {
    Table table;

    public SchemaDAO(Table table) {
        this.table = table;
    }

    @Override
    public Collection<Attribute> list() {
        return table.listAttributes();
    }

    @Override
    public Attribute get(String key) {
        return table.getAttribute(key);
    }

    @Override
    public MutationResult add(Attribute attribute) {
        return table.addAttribute(attribute);
    }

    @Override
    public MutationResult delete(String key) {
        return table.deleteAttribute(key);
    }
}
