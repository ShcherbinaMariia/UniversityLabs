package app.filter;

import app.row.RowDAO;
import app.schema.SchemaDAO;
import app.schema.attributes.Attribute;
import app.table.Table;
import app.util.Hateoas;
import app.util.Resolver;
import app.util.Serializer;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FilterController {
    public final static String OPERATOR_SUFFIX = "_op";
    public final static String VALUE_SUFFIX = "_val";

    public static Route getFilters = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);

        SchemaDAO schemaDAO = new SchemaDAO(table);

        Collection<Attribute> attributes = schemaDAO.list();

        return Serializer.Serialize(Filter.getFilters(attributes));
    };

    public static Route filterRows = (Request request, Response response) -> {
        Table table = Resolver.ResolveTable(request);

        RowDAO rowDAO = new RowDAO(table);
        SchemaDAO schemaDAO = new SchemaDAO(table);

        HashMap<String, String> queryParams = new HashMap<>();

       for (String param: request.queryParams()) {
           queryParams.put(param, request.queryParams(param));
       }

        HashMap<String, FilterParam> params = extractParams(queryParams);

       return Serializer.Serialize(Filter.filterRows(schemaDAO.list(), params, rowDAO.list()));
    };

    private static FilterParam extractAttributeParam(Map<String, String> params, String name) {
        FilterParam param = new FilterParam();
        param.operator = params.get(name + OPERATOR_SUFFIX);
        param.value = params.get(name + VALUE_SUFFIX);
        return param;
    }

    private static HashMap<String, FilterParam> extractParams(Map<String, String> params) {
        HashMap<String, FilterParam> result = new HashMap<>();

        for (String paramName: params.keySet()) {
            if (paramName.contains(VALUE_SUFFIX)) {
                if (params.get(paramName).equals("")) {
                    continue;
                }
                String attributeName = paramName.replace(VALUE_SUFFIX, "");

                FilterParam filterParam = extractAttributeParam(params, attributeName);

                result.put(attributeName, filterParam);
            }
        }

        return result;
    }
}
