package app.table;

import app.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collection;

public class TableController {
    public static Route listTables = (Request request, Response response) -> {
        BasicDAO<String, Table> tableDAO = Resolver.ResolveDatabase(request);
        Collection<Table> tables = tableDAO.list();
        return Serializer.Serialize(tables);
    };

    public static Route addTable = (Request request, Response response) -> {
        BasicDAO<String, Table> tableDAO = Resolver.ResolveDatabase(request);
        MutationResult result = tableDAO.add(new Table(request.body()));
        ResponseHelper.processMutationResult(result, response);
        return Serializer.Serialize(result);
    };

    public static Route deleteTable = (Request request, Response response) -> {
        BasicDAO<String, Table> tableDAO = Resolver.ResolveDatabase(request);
        MutationResult result = tableDAO.delete(request.params(Path.TABLE_ID));
        ResponseHelper.processMutationResult(result, response);
        return Serializer.Serialize(result);
    };

    public static Route getTable = (Request request, Response response) -> {
        BasicDAO<String, Table> tableDAO = Resolver.ResolveDatabase(request);
        Table table = tableDAO.get(request.params(Path.TABLE_ID));
        return Serializer.Serialize(table);
    };
}
