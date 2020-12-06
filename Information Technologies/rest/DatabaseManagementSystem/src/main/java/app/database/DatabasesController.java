package app.database;

import app.util.*;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collection;

public class DatabasesController {
    private static BasicDAO<String, Database> databasesDAO = DatabaseManager.getInstance();

    public static Route listDatabases = (Request request, Response response) -> {
        Collection<Database> databases = databasesDAO.list();
        return Serializer.Serialize(databases);
    };

    public static Route getDatabase = (Request request, Response response) -> {
        String name = request.params(Path.DATABASE_ID);
        Database database = databasesDAO.get(name);
        return Serializer.Serialize(database);
    };

    public static Route addDatabase = (Request request, Response response) -> {
        Database database = Deserializer.getGson().fromJson(request.body(), Database.class);
        MutationResult mutationResult = databasesDAO.add(database);
        ResponseHelper.processMutationResult(mutationResult, response);
        return Serializer.Serialize(mutationResult);
    };

    public static Route deleteDatabase = (Request request, Response response) -> {
        String name = request.params(Path.DATABASE_ID);
        MutationResult mutationResult = databasesDAO.delete(name);
        ResponseHelper.processMutationResult(mutationResult, response);
        return Serializer.Serialize(mutationResult);
    };
}
