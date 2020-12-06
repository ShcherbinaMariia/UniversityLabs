package app;

import app.database.DatabasesController;
import app.filter.FilterController;
import app.row.RowController;
import app.schema.SchemaController;
import app.storage.StorageController;
import app.table.TableController;
import app.util.Hateoas;
import app.util.Path;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        staticFileLocation("/static");
        port(getHerokuAssignedPort());

        before((request, response) -> {
            response.type("application/json");
            response.header("Access-Control-Allow-Origin", "*");
        });
        
        get(Path.STORAGE, StorageController.listDatabases);
        post(Path.SAVE_DATABASE, StorageController.saveDatabase);
        get(Path.LOAD_DATABASE, StorageController.loadDatabase);

        get(Path.DATABASES, DatabasesController.listDatabases);
        post(Path.DATABASES, DatabasesController.addDatabase);

        delete(Path.DATABASE, DatabasesController.deleteDatabase);
        get(Path.DATABASE, DatabasesController.getDatabase);

        path(Path.DATABASE, () -> {
            get(Path.TABLES, TableController.listTables);
            post(Path.TABLES, TableController.addTable);

            get(Path.TABLE, TableController.getTable);
            delete(Path.TABLE, TableController.deleteTable);

            path(Path.TABLE, () -> {
               get(Path.SCHEMA, SchemaController.listAttributes);
               post(Path.SCHEMA, SchemaController.addAttribute);

               get(Path.FILTERS, FilterController.getFilters);

               get(Path.ATTRIBUTE, SchemaController.getAttribute);
               delete(Path.ATTRIBUTE, SchemaController.deleteAttribute);


               get(Path.ROWS, RowController.listRows);
               post(Path.ROWS, RowController.addRow);

               get(Path.FILTER, FilterController.filterRows);

               get(Path.ROW, RowController.getRow);
               delete(Path.ROW, RowController.deleteRow);
            });
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
