package app.util;

import app.database.Database;
import app.database.DatabaseManager;
import app.row.Row;
import app.schema.Schema;
import app.schema.attributes.Attribute;
import app.table.Table;
import spark.Request;

import java.util.Collection;
import java.util.HashMap;

public class Resolver {
    private static BasicDAO<String, Database> databasesDAO = DatabaseManager.getInstance();

    public static Database ResolveDatabase(Request request) {
        return databasesDAO.get(request.params(Path.DATABASE_ID));
    }

    public static Table ResolveTable(Request request) {
        Database database = ResolveDatabase(request);
        if (database == null) return null;
        return database.get(request.params(Path.TABLE_ID));
    }

    private static String getDatabaseUrl(Database database) {
        return Path.DATABASES + database.getName();
    }

    private static String getTableUrl(Table table) {
        return Path.TABLES + table.getName();
    }

    private static String getAttributeUrl(Attribute attribute) {
        return Path.SCHEMA + attribute.getName();
    }

    public static HashMap<String, Resource> getDatabaseResources(Collection<Database> databases) {
        HashMap<String, Resource> resources = new HashMap<>();
        for (Database database: databases) {
            resources.put("get-" + database.getName(), new Resource(getDatabaseUrl(database), "get"));
            resources.put("delete-" + database.getName(), new Resource(getDatabaseUrl(database), "delete"));
        }
        resources.put("add-database", new Resource(Path.DATABASES, "post"));
        return resources;
    }

    public static HashMap<String, Resource> getTableResources(Database database) {
        return getTableResources(database.list());
    }

    public static HashMap<String, Resource> getTableResources(Collection<Table> tables) {
        HashMap<String, Resource> resources = new HashMap<>();
        for (Table table: tables) {
            resources.put("get-" + table.getName(), new Resource(getTableUrl(table), "get"));
            resources.put("delete-" + table.getName(), new Resource(getTableUrl(table), "delete"));
        }
        resources.put("add-table", new Resource(Path.TABLES, "post"));
        return resources;
    }

    public static HashMap<String, Resource> getSchemaResources(Collection<Attribute> attributes) {
        HashMap<String, Resource> resources = new HashMap<>();
        for (Attribute attribute: attributes) {
            resources.put("get-" + attribute.getName(), new Resource(getAttributeUrl(attribute), "get"));
            resources.put("delete-" + attribute.getName(), new Resource(getAttributeUrl(attribute), "delete"));
        }
        resources.put("add-attribute", new Resource(Path.SCHEMA, "post"));
        return resources;
    }

    public static HashMap<String, Resource> getRowsAndSchemaResources(Table table) {
        HashMap<String, Resource> resources = new HashMap<>();
        resources.put("schema", new Resource(Path.SCHEMA, "get"));
        resources.put("rows", new Resource(Path.ROWS, "get"));
        return resources;
    }
}
