package app.storage.mongo;

import app.database.Database;
import app.storage.Storage;
import app.table.Table;
import app.util.Deserializer;
import app.util.MutationResult;
import app.util.Serializer;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;

public class MongoDBClient implements Storage {
    MongoClient mongoClient;

    public MongoDBClient() {
        mongoClient = new MongoClient("mongo", 27017);
    }

    @Override
    public ArrayList<String> getDatabaseNames() {
        ArrayList<String> availableDatabases = new ArrayList<>();
        for (String s : mongoClient.listDatabaseNames()) {
            availableDatabases.add(s);
        }
        return availableDatabases;
    }

    @Override
    public MutationResult saveDatabase(Database database) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database.getName());
        for (Table table : database.list()) {
            mongoDatabase.createCollection(table.getName());
            MongoCollection<Document> collection = mongoDatabase.getCollection(table.getName());
            String jsonString = Serializer.Serialize(table);
            collection.insertOne(Document.parse(jsonString));
        }

        return MutationResult.Success();
    }

    @Override
    public Database loadDatabase(String databaseName) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
        Database database = new Database(databaseName);

        for (String tableName : mongoDatabase.listCollectionNames()) {
            MongoCollection<Document> collection = mongoDatabase.getCollection(tableName);
            Document tableDocument = collection.find().first();
            Table table = Deserializer.getGson().fromJson(tableDocument.toJson(), Table.class);
            database.add(table);
        }

        return database;
    }
}
