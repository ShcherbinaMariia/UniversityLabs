package app.schema;

import app.schema.attributes.Attribute;
import app.table.Table;
import app.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collection;

public class SchemaController {
    public static Route listAttributes = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);
        SchemaDAO schemaDAO = new SchemaDAO(table);
        Collection<Attribute> attributes = schemaDAO.list();
        return Serializer.Serialize(attributes);
    };

    public static Route addAttribute = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);
        SchemaDAO schemaDAO = new SchemaDAO(table);
        MutationResult mutationResult = schemaDAO.add(Deserializer.getGson().fromJson(request.body(), Attribute.class));
        ResponseHelper.processMutationResult(mutationResult, response);
        return Serializer.Serialize(mutationResult);
    };

    public static Route deleteAttribute = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);
        SchemaDAO schemaDAO = new SchemaDAO(table);
        MutationResult mutationResult = schemaDAO.delete(request.params(Path.ATTRIBUTE_ID));
        ResponseHelper.processMutationResult(mutationResult, response);
        return Serializer.Serialize(mutationResult);
    };

    public static Route getAttribute = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);
        SchemaDAO schemaDAO = new SchemaDAO(table);
        return Serializer.Serialize(schemaDAO.get(request.params(Path.ATTRIBUTE_ID)));
    };
}
