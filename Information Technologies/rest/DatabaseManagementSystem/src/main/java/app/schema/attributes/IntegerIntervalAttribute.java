package app.schema.attributes;

import app.row.Row;
import app.row.values.Value;
import app.schema.attributes.types.IntegerInterval;
import app.schema.attributes.types.Types;

import java.util.function.Function;

enum IntegerIntervalOperator {
    EQUAL,
    CONTAINS,
    INTERSECTS,
}

public class IntegerIntervalAttribute extends Attribute {
    @Override
    public Types getType() {
        return Types.INTEGER_INTERVAL;
    }

    @Override
    public Boolean validateValue(String value) {
        return IntegerInterval.parseInterval(value) != null;
    }

    @Override
    public Value getValue(String value) {
        return new Value<>(IntegerInterval.parseInterval(value));
    }

    @Override
    public Value getDefault() {
        return new Value<>(new IntegerInterval(0, 1));
    }

    @Override
    public Function<Row, Boolean> filter(String operator, String operandStr) {
        Function<IntegerInterval, Boolean> operation;
        IntegerInterval operand = (IntegerInterval) getValue(operandStr).getValue();

        switch (IntegerIntervalOperator.valueOf(operator)) {
            case EQUAL:
                operation = value -> value.equals(operand);
                break;
            case CONTAINS:
                operation = value -> value.contains(operand);
                break;
            case INTERSECTS:
                operation = value -> value.intersects(operand);
                break;
            default:
                operation = value -> true;
        }

        return row -> {
            IntegerInterval value = (IntegerInterval) row.values.get(this.name).getValue();
            return operation.apply(value);
        };
    }

    @Override
    public Object[] getOperators() {
        return IntegerIntervalOperator.values();
    }

    public IntegerIntervalAttribute(String name){
        this.name = name;
    }
}
