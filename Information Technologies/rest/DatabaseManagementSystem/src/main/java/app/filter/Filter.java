package app.filter;

import app.row.Row;
import app.schema.attributes.Attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class Filter {
     static HashMap<String, ArrayList<String>> getFilters(Collection<Attribute> attributes) {
        HashMap<String, ArrayList<String>> filterOperators = new HashMap<>();
        for (Attribute attribute: attributes) {
            ArrayList<String> operators = new ArrayList<>();
            for (Object operator: attribute.getOperators()) {
                operators.add(operator.toString());
            }
            filterOperators.put(attribute.getName(), operators);
        }
        return filterOperators;
    }

    static FilterPattern getFilterPattern(Collection<Attribute> attributes, HashMap<String, FilterParam> params) {
        FilterPattern filterPattern = new FilterPattern();
        for (Attribute attribute: attributes) {
            if (params.containsKey(attribute.getName())) {
                FilterParam filterParam = params.get(attribute.getName());
                filterPattern.addFilter(attribute.filter(filterParam.operator, filterParam.value));
            }
        }
        return filterPattern;
    }

    static ArrayList<Row> filterRows(Collection<Attribute> attributes, HashMap<String, FilterParam> params, Collection<Row> rows) {
         return getFilterPattern(attributes, params).filter(rows);
    }
}
