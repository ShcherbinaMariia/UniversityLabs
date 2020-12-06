package app.database;

import app.table.Table;
import app.util.BasicDAO;
import app.util.MutationResult;

import java.util.Collection;
import java.util.HashMap;

// POJO for representing database
public class Database implements BasicDAO<String, Table> {
    String name;
    HashMap<String, Table> tables;

    public Database(String name) {
        this.name = name;
        this.tables = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public Collection<Table> list() {
        return tables.values();
    }

    @Override
    public Table get(String name) {
        return tables.getOrDefault(name, null);
    }

    @Override
    public MutationResult add(Table table) {
        if (tables.containsKey(table.getName()))
            return MutationResult.Fail("Duplicate name for table");
        tables.put(table.getName(), table);
        return MutationResult.Success();
    }

    @Override
    public MutationResult delete(String name) {
        if (!tables.containsKey(name))
            return MutationResult.Fail("No such table");
        tables.remove(name);
        return MutationResult.Success();
    }
}
