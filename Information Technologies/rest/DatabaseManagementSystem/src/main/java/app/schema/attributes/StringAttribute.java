package app.schema.attributes;

import app.row.Row;
import app.row.values.Value;
import app.schema.attributes.types.Types;

import java.util.function.Function;

enum StringOperator {
    EQUAL,
    CONTAINS,
}

public class StringAttribute extends Attribute {

    @Override
    public Types getType() {
        return Types.STRING;
    }

    @Override
    public Boolean validateValue(String value) {
        return true;
    }

    @Override
    public Value getValue(String value) {
        return new Value<>(value);
    }

    @Override
    public Value getDefault() {
        return new Value<>("");
    }

    @Override
    public Function<Row, Boolean> filter(String operator, String operand) {
        Function<String, Boolean> operation;

        switch (StringOperator.valueOf(operator)) {
            case EQUAL:
                operation = value -> value.equals(operand);
                break;
            case CONTAINS:
                operation = value -> value.contains(operand);
                break;
            default:
                operation = value -> true;
        }

        return row -> {
            String value = (String) row.values.get(this.name).getValue();
            return operation.apply(value);
        };
    }

    @Override
    public Object[] getOperators() {
        return StringOperator.values();
    }

    public StringAttribute(String name){
        this.name = name;
    }
}
