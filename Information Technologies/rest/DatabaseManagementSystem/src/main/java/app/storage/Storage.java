package app.storage;

import app.database.Database;
import app.util.MutationResult;

import java.util.ArrayList;

public interface Storage {
    ArrayList<String> getDatabaseNames();

    MutationResult saveDatabase(Database database);

    Database loadDatabase(String databaseName);
}
