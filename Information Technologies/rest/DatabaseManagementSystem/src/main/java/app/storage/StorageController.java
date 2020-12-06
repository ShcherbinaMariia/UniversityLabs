package app.storage;

import app.database.Database;
import app.database.DatabaseManager;
import app.storage.mongo.MongoDBClient;
import app.storage.mysql.MySQLClient;
import app.util.BasicDAO;
import app.util.MutationResult;
import app.util.Path;
import app.util.Serializer;
import spark.Request;
import spark.Response;
import spark.Route;

public class StorageController {
    private static BasicDAO<String, Database> databasesDAO = DatabaseManager.getInstance();

    private static Storage getStorageClient(Request request) {
        String storageType = request.params(Path.STORAGE_TYPE);

        if (storageType.equals("mongo")) {
            return new MongoDBClient();
        }

        if (storageType.equals("mysql")) {
            return new MySQLClient();
        }

        return null;
    }

    public static Route listDatabases = (Request request, Response response) -> {
        Storage client = getStorageClient(request);
        if (client == null) {
            return null;
        }

        return Serializer.Serialize(client.getDatabaseNames());
    };

    public static Route saveDatabase = (Request request, Response response) -> {
        Storage client = getStorageClient(request);
        if (client == null) {
            return null;
        }

        String name = request.params(Path.DATABASE_ID);
        Database database = databasesDAO.get(name);
        MutationResult mutationResult = client.saveDatabase(database);

        return Serializer.Serialize(mutationResult);
    };

    public static Route loadDatabase = (Request request, Response response) -> {
        Storage client = getStorageClient(request);
        if (client == null) {
            return null;
        }

        String name = request.params(Path.DATABASE_ID);
        Database database = client.loadDatabase(name);
        return Serializer.Serialize(database);
    };

}
