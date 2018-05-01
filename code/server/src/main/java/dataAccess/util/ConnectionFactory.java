package dataAccess.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConnectionFactory {

    private static final String DEFAULT_HOST = "localhost";
    private static final Integer DEFAULT_PORT = 27017;
    private static final String DEFAULT_DB = "ass3";
    private MongoClient mongoClient;

    public ConnectionFactory() {
        mongoClient = new MongoClient(DEFAULT_HOST, DEFAULT_PORT);
    }

    public ConnectionFactory(String host, int port) {
        mongoClient = new MongoClient(host, port);
    }

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(DEFAULT_DB);
    }
}
