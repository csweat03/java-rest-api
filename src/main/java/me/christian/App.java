package me.christian;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoDatabase;
import me.christian.factory.implementation.ControllerFactory;
import me.christian.utility.FileUtility;
import me.christian.utility.MongoUtility;
import org.bson.Document;

import java.io.BufferedReader;
import java.util.Iterator;

import static spark.Spark.get;

/**
 * @author Christian Sweat
 */
public class App 
{
    private final static String CONFIG_FILE = "assets\\config.json";
    private final static BufferedReader READER = FileUtility.instantiateNewReader(CONFIG_FILE);
    private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final static MongoDatabase
            MONGODB_USER_DB = MongoUtility.establishDatabaseConnection("userdb"),
            MONGODB_PROJECT_DB = MongoUtility.establishDatabaseConnection("projectdb");
    private final static ControllerFactory CONTROLLER_FACTORY = new ControllerFactory();

    public static void main( String[] args )
    {
        // Landing Page
        get("/", (req, res) -> "Hello World! Try navigating to /(project name)/(class name)");

        CONTROLLER_FACTORY.initialize();
    }

    public static BufferedReader getConfigReader() {
        return READER;
    }

    public static Gson getGson() {
        return GSON;
    }

    public static MongoDatabase getUserDatabase() {
        return MONGODB_USER_DB;
    }
    public static MongoDatabase getProjectDatabase() {
        return MONGODB_PROJECT_DB;
    }
}
