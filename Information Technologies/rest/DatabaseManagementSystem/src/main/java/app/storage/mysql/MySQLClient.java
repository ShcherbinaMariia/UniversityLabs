package app.storage.mysql;

import app.database.Database;
import app.storage.Storage;
import app.table.Table;
import app.util.Deserializer;
import app.util.MutationResult;
import app.util.Serializer;

import java.sql.*;
import java.util.ArrayList;

public class MySQLClient implements Storage {
    private final String url="jdbc:mysql://mysql:3306";
    private final String username="root";
    private final String password="password";

    private Connection connection;
    private Statement statement;

    public MySQLClient() {}

    private void open() throws Exception {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
        statement = connection.createStatement();
    }

    private void close() throws Exception {
        statement.close();
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    @Override
    public ArrayList<String> getDatabaseNames() {
        ArrayList<String> result = new ArrayList<>();
        try {
            open();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
            while (resultSet.next()) {
                result.add(resultSet.getString("Database"));
            }
            resultSet.close();
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public MutationResult saveDatabase(Database database) {
        try {
            open();
            String createDatabase = String.format("CREATE DATABASE %s", database.getName());
            statement.executeUpdate(createDatabase);

            for (Table table : database.list()) {
                String fullTableName = String.format("%s.%s",database.getName(), table.getName());
                String createTable = String.format("CREATE TABLE %s(data JSON)", fullTableName);
                statement.executeUpdate(createTable);

                String jsonValue = Serializer.Serialize(table);

                String insertData = String.format("INSERT INTO %s VALUES(\'%s\')", fullTableName, jsonValue);
                statement.executeUpdate(insertData);
            }
            close();
        } catch (Exception e) {
            e.printStackTrace();
            return MutationResult.Fail("Failed to save data " + e.getMessage());
        }

        return MutationResult.Success();
    }

    @Override
    public Database loadDatabase(String databaseName) {
        Database database = new Database(databaseName);

        String showTables = String.format("SHOW TABLES FROM %s", databaseName);
        try {
            open();

            ResultSet tables = statement.executeQuery(showTables);
            String columnName = String.format("Tables_in_%s", databaseName);

            ArrayList<String> tableNames = new ArrayList<>();

            while(tables.next()) {
                tableNames.add(tables.getString(columnName));
            }

            tables.close();

            for (String tableName: tableNames) {

                String selectTable = String.format("SELECT data FROM %s.%s", databaseName, tableName);
                ResultSet data = statement.executeQuery(selectTable);

                while (data.next()) {
                    Table table = Deserializer.getGson().fromJson(data.getString("data"), Table.class);
                    database.add(table);
                }

                data.close();
            }

            close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return database;
    }
}
