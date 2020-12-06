package app.row.values;

public class Value<T> {
    private T value;

    public Value(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
