package app.schema.attributes;

import app.row.Row;
import app.row.values.Value;
import app.schema.attributes.types.Types;

import java.util.function.Function;

enum CharOperator {
    EQUAL,
    LESS,
    GREATER,
}

public class CharAttribute extends Attribute {
    @Override
    public Types getType() {
        return Types.CHAR;
    }

    @Override
    public Boolean validateValue(String value) {
        return value.length() == 1;
    }

    @Override
    public Value getValue(String value) {
        return new Value<>(value.charAt(0));
    }

    @Override
    public Value getDefault() {
        return new Value<>('0');
    }

    @Override
    public Function<Row, Boolean> filter(String operator, String operandStr) {
        Function<Character, Boolean> operation;
        Character operand = (Character) getValue(operandStr).getValue();
        switch (CharOperator.valueOf(operator)) {
            case EQUAL:
                operation = value -> value == operand;
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
            Character value = (Character) row.values.get(this.name).getValue();
            return operation.apply(value);
        };
    }

    @Override
    public Object[] getOperators() {
        return CharOperator.values();
    }

    public CharAttribute(String name){
        this.name = name;
    }
}
