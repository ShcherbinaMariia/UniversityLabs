package app.util;

public class Path {
    public final static String ROOT = "/";

    public final static String DATABASES = "/databases/";
    public final static String DATABASE_ID = ":database";
    public final static String DATABASE = DATABASES + DATABASE_ID;

    public final static String STORAGE_TYPE = ":storageType";
    public final static String STORAGE = "/storage/" + STORAGE_TYPE + "/";
    public final static String SAVE_DATABASE = STORAGE + DATABASE_ID + "/save/";
    public final static String LOAD_DATABASE = STORAGE + DATABASE_ID + "/load/";

    public final static String TABLES = "/tables/";
    public final static String TABLE_ID = ":table";
    public final static String TABLE = TABLES + TABLE_ID;

    public final static String SCHEMA = "/schema/";
    public final static String ATTRIBUTE_ID = ":attributeName";
    public final static String ATTRIBUTE = SCHEMA + ATTRIBUTE_ID;

    public final static String FILTERS = SCHEMA + "/filters/";

    public final static String ROWS = "/rows/";
    public final static String ROW_ID = ":rowId";
    public final static String ROW = ROWS + ROW_ID;

    public final static String FILTER = ROWS + "filter";
}
