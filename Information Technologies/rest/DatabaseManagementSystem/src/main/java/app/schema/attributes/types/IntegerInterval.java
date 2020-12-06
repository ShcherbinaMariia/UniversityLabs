package app.schema.attributes.types;

// Class for working with integer intervals in format [begin;end]
public class IntegerInterval {
    private int begin;
    private int end;

    public IntegerInterval(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[" + this.begin + ";" + this.end + "]";
    }

    public static IntegerInterval parseInterval(String value) {
        if (value.length() < 3 || value.charAt(0) != '[' || value.charAt(value.length() - 1) != ']') {
            return null;
        }
        String[] numbers = value.substring(1, value.length() - 1).split(";");
        if (numbers.length != 2) {
            return null;
        }
        int begin, end;

        try {
            begin = Integer.parseInt(numbers[0]);
            end = Integer.parseInt(numbers[1]);
        } catch (Exception e) {
            return null;
        }
        return new IntegerInterval(begin, end);
    }

    public boolean equals(IntegerInterval other) {
        return this.begin == other.begin && this.end == other.end;
    }

    public boolean contains(IntegerInterval other) {
        return this.begin <= other.begin && this.end >= other.end;
    }

    public boolean intersects(IntegerInterval other) {
        return !(this.end < other.begin || this.begin > other.end);
    }
}
