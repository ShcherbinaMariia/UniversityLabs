package app.database;

import app.util.BasicDAO;
import app.util.MutationResult;

import java.util.Collection;
import java.util.HashMap;

public class DatabaseManager implements BasicDAO<String, Database> {
    private static DatabaseManager instance;
    private HashMap<String, Database> databases = new HashMap<>();

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    @Override
    public Collection<Database> list() {
        return databases.values();
    }

    @Override
    public Database get(String name) {
        return databases.getOrDefault(name, null);
    }

    @Override
    public MutationResult add(Database database) {
        if (databases.containsKey(database.name)) {
            return MutationResult.Fail("Duplicate name for database");
        }
        databases.put(database.name, database);
        return MutationResult.Success();
    }

    @Override
    public MutationResult delete(String name) {
        if (!databases.containsKey(name)) {
            return MutationResult.Fail("No such database");
        }
        databases.remove(name);
        return MutationResult.Success();
    }
}
