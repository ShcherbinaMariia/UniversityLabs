package app.schema.attributes;

import app.row.Row;
import app.row.values.Value;
import app.schema.attributes.types.Types;

import java.util.function.Function;

enum IntegerOperator {
    EQUAL,
    LESS,
    GREATER,
}

public class IntegerAttribute extends Attribute {
    @Override
    public Types getType() {
        return Types.INTEGER;
    }

    @Override
    public Boolean validateValue(String value) {
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Value getValue(String value) {
        return new Value<>(Integer.parseInt(value));
    }

    @Override
    public Value getDefault() {
        return new Value<>(0);
    }

    @Override
    public Function<Row, Boolean> filter(String operator, String operandStr) {
        Function<Integer, Boolean> operation;
        Integer operand = (Integer)getValue(operandStr).getValue();

        switch (IntegerOperator.valueOf(operator)) {
            case EQUAL:
                operation = value -> value.equals(operand);
                break;
            case LESS:
                operation = value -> value < operand;
                break;
            case GREATER:
                operation = value -> value > operand;
                break;
            default:
                operation = value -> true;
        }

        return row -> {
            Integer value = (Integer) row.values.get(this.name).getValue();
            return operation.apply(value);
        };
    }

    @Override
    public Object[] getOperators() {
        return IntegerOperator.values();
    }

    public IntegerAttribute(String name) {
        this.name = name;
    }
}
