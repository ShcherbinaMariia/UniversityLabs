package app.row;

import app.table.Table;
import app.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class RowController {

    public static Route listRows = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);
        RowDAO rowDAO = new RowDAO(table);
        return Serializer.Serialize(rowDAO.list());
    };

    public static Route addRow = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);
        RowDAO rowDAO = new RowDAO(table);
        MutationResult mutationResult = rowDAO.add(Deserializer.getGson().fromJson(request.body(), Row.class));
        ResponseHelper.processMutationResult(mutationResult, response);
        return Serializer.Serialize(mutationResult);
    };

    public static Route deleteRow = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);
        RowDAO rowDAO = new RowDAO(table);
        MutationResult mutationResult = rowDAO.delete(request.params(Path.ROW_ID));
        ResponseHelper.processMutationResult(mutationResult, response);
        return Serializer.Serialize(mutationResult);
    };

    public static Route getRow = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);
        RowDAO rowDAO = new RowDAO(table);
        return Serializer.Serialize(rowDAO.get(request.params(Path.ROW_ID)));
    };

}
