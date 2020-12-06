package app.schema.attributes;

import app.row.Row;
import app.row.values.Value;
import app.schema.attributes.types.Types;

import java.util.function.Function;

public abstract class Attribute {
    public String name;

    public String getName() {
        return this.name;
    }

    public abstract Types getType();
    public abstract Boolean validateValue(String value);

    public static Attribute getAttribute(String name, Types type) {
        switch (type) {
            case INTEGER:
                return new IntegerAttribute(name);
            case REAL:
                return new RealAttribute(name);
            case CHAR:
                return new CharAttribute(name);
            case STRING:
                return new StringAttribute(name);
            case TEXTFILE:
                return new TextFileAttribute(name);
            case INTEGER_INTERVAL:
                return new IntegerIntervalAttribute(name);
            default:
                return null;
        }
    }

    public abstract Value getValue(String value);
    public abstract Value getDefault();

    public abstract Function<Row, Boolean> filter(String operator, String operandStr);

    public abstract Object[] getOperators();
}
