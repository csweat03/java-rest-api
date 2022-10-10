package me.christian.utility;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import me.christian.App;

import java.util.concurrent.atomic.AtomicReference;

public class MongoUtility {
    public static MongoDatabase establishDatabaseConnection(String databaseName) {
        AtomicReference<MongoDatabase> database = new AtomicReference<>();

        JSONUtility.execute("mongodb", value -> {
            MongoRecord mongoRecord = App.getGson().fromJson(value, MongoRecord.class);

            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(mongoRecord.generateURL()))
                    .build();

            return database.getAndSet(MongoClients.create(mongoClientSettings).getDatabase(databaseName));
        });

        if (database.get() == null) {
            System.err.println("\nCRITICAL: Application has not successfully connected to the MongoDB database.\nYou should review `config.json` and verify the database settings.\n");
            System.exit(-1);
            return null;
        }

        return database.get();
    }

    static class MongoRecord {
        String username, password, cluster, params;

        MongoRecord(String username, String password, String cluster, String params) {
            this.username = username;
            this.password = password;
            this.cluster = cluster;
            this.params = params;
        }

        public String generateURL() {
            return String.format("mongodb+srv://%s:%s@%s.mongodb.net/?%s", username, password, cluster, params);
        }

    }

}
