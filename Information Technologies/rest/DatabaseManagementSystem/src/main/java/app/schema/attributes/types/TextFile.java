package app.schema.attributes.types;

// Class for working with text files in format
// /some/path/to/file;sometext

public class TextFile {
    private String filePath;
    private String content;

    public TextFile(String filePath, String content) {
        this.filePath = filePath;
        this.content = content;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return this.filePath + ";" + this.content;
    }

    public static TextFile parseTextFile(String value) {
        String[] parts = value.split(";");
        if (parts.length != 2) return null;
        return new TextFile(parts[0], parts[1]);
    }
}
