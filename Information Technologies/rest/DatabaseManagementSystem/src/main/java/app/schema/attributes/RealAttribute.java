package app.schema.attributes;

import app.row.Row;
import app.row.values.Value;
import app.schema.attributes.types.Types;

import java.util.function.Function;

enum RealOperator {
    EQUAL,
    LESS,
    GREATER,
}

public class RealAttribute extends Attribute {
    @Override
    public Types getType() {
        return Types.REAL;
    }

    @Override
    public Boolean validateValue(String value) {
        try {
            Float.parseFloat(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Value getValue(String value) {
        return new Value<>(Float.parseFloat(value));
    }

    @Override
    public Value getDefault() {
        return new Value<>(0.0);
    }

    @Override
    public Function<Row, Boolean> filter(String operator, String operandStr) {
        Function<Float, Boolean> operation;
        Float operand = (Float) getValue(operandStr).getValue();

        switch (RealOperator.valueOf(operator)) {
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
            Float value = (Float) row.values.get(this.name).getValue();
            return operation.apply(value);
        };
    }

    @Override
    public Object[] getOperators() {
        return RealOperator.values();
    }

    public RealAttribute(String name){
        this.name = name;
    }
}
