package app.filter;

import app.row.Row;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class FilterPattern {
    ArrayList<Function<Row, Boolean>> predicates;

    FilterPattern() {
        predicates = new ArrayList<>();
    }

    void addFilter(Function<Row, Boolean> predicate) {
        this.predicates.add(predicate);
    }

    boolean filterRow(Row row) {
        for (Function<Row, Boolean> predicate : predicates) {
            if (!predicate.apply(row)) {
                return false;
            }
        }
        return true;
    }

    ArrayList<Row> filter(Collection<Row> rows) {
        ArrayList<Row> result = new ArrayList<>();
        for (Row row : rows) {
            if (filterRow(row)) {
                result.add(row);
            }
        }
        return result;
    }
}
