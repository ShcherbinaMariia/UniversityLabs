package app.schema.attributes;

import app.row.Row;
import app.row.values.Value;
import app.schema.attributes.types.TextFile;
import app.schema.attributes.types.Types;

import java.util.function.Function;

enum TextFilesOperators {
    EQUAL_PATH,
    EQUAL_TEXT,
    TEXT_CONTAINS,
    IS_SUBPATH,
}

public class TextFileAttribute extends Attribute {
    @Override
    public Types getType() {
        return Types.TEXTFILE;
    }

    @Override
    public Boolean validateValue(String value) {
        return TextFile.parseTextFile(value) != null;
    }

    @Override
    public Value getValue(String value) {
        return new Value<>(TextFile.parseTextFile(value));
    }

    @Override
    public Value getDefault() {
        return new Value<>(new TextFile("", ""));
    }

    @Override
    public Function<Row, Boolean> filter(String operator, String operandStr) {
        Function<TextFile, Boolean> operation;
        TextFile operand = (TextFile) getValue(operandStr).getValue();

        switch (TextFilesOperators.valueOf(operator)) {
            case EQUAL_PATH:
                operation = value -> value.getFilePath().equals(operand.getFilePath());
                break;
            case EQUAL_TEXT:
                operation = value -> value.getContent().equals(operand.getContent());
                break;
            case TEXT_CONTAINS:
                operation = value -> value.getContent().contains(operand.getContent());
                break;
            case IS_SUBPATH:
                operation = value -> value.getFilePath().startsWith(operand.getFilePath());
                break;
            default:
                operation = value -> true;
        }

        return row -> {
            TextFile value = (TextFile) row.values.get(this.name).getValue();
            return operation.apply(value);
        };
    }

    @Override
    public Object[] getOperators() {
        return TextFilesOperators.values();
    }

    public TextFileAttribute(String name){
        this.name = name;
    }
}
