package storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import app.database.Database;
import app.schema.attributes.Attribute;
import app.schema.attributes.types.Types;
import app.storage.mysql.MySQLClient;

import app.table.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class StorageTest {
    @InjectMocks private MySQLClient client;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;
    @Mock private ResultSet mockResultSet;
    @Mock private ResultSet mockTableSet;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadDatabase() throws Exception {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery(Mockito.matches("SHOW TABLES FROM test_db"))).thenReturn(mockResultSet);

        Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockResultSet.getString("Tables_in_test_db")).thenReturn("test_table");

        Mockito.when(mockStatement.executeQuery(Mockito.matches("SELECT data FROM test_db.test_table"))).thenReturn(mockTableSet);
        Mockito.when(mockTableSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockTableSet.getString("data")).thenReturn("{\"name\":\"test_table\",\"attributes\":[{\"type\":\"STRING\",\"name\":\"test_attribute\"}],\"rows\":[]}");

        Database database = client.loadDatabase("test_db");
        assertEquals(database.getName(), "test_db");

        List<Table> tables = new ArrayList<>(database.list());
        assertEquals(tables.size(), 1);
        Table table = tables.get(0);

        List<Attribute> attributes = new ArrayList<>(table.listAttributes());
        assertEquals(attributes.size(), 1);
        Attribute attribute = attributes.get(0);
        assertEquals(attribute.getName(), "test_attribute");
        assertEquals(attribute.getType().toString(), "STRING");

        assertEquals(table.listRows().size(), 0);
    }

    @Test
    public void getDatabaseNames() throws Exception {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery(Mockito.matches("SHOW DATABASES"))).thenReturn(mockResultSet);

        Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(mockResultSet.getString("Database")).thenReturn("db1").thenReturn("db2");

        List<String> databaseNames = client.getDatabaseNames();
        assertEquals(databaseNames.size(), 2);
        assertEquals(databaseNames.get(0), "db1");
        assertEquals(databaseNames.get(1), "db2");
    }

    @Test
    public void saveDatabase() throws Exception {

        Database database = new Database("test_db");
        Table table = new Table("test_table");
        Attribute attribute = Attribute.getAttribute("test_attribute", Types.STRING);
        table.addAttribute(attribute);
        database.add(table);

        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeUpdate(Mockito.matches("CREATE DATABASE test_db"))).thenReturn(1);

        Mockito.calls(3);
    }
}