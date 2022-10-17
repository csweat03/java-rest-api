package me.christian;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoDatabase;
import me.christian.factory.implementation.ControllerFactory;
import me.christian.utility.MongoUtility;
import spark.Spark;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import static spark.Spark.get;

/**
 * @author Christian Sweat
 */
public class App 
{
    private static BufferedReader READER;
    private static Gson GSON;
    private static MongoDatabase MONGODB_USER_DB, MONGODB_PROJECT_DB;
    private static ControllerFactory CONTROLLER_FACTORY;

    public static void main( String[] args ) {
        READER = new BufferedReader(new InputStreamReader(Objects.requireNonNull(App.class.getClassLoader().getResourceAsStream("json/config.json"))));

        GSON = new GsonBuilder().setPrettyPrinting().create();
        MONGODB_USER_DB = MongoUtility.establishDatabaseConnection("userdb");
        MONGODB_PROJECT_DB = MongoUtility.establishDatabaseConnection("projectdb");

        CONTROLLER_FACTORY = new ControllerFactory();

        Spark.port(8080);
        // Landing Page
        get("/", (req, res) -> "Hello World! Try navigating to api/(project name)/(class name)");

        CONTROLLER_FACTORY.initialize();

        System.out.println("Access the API on http://localhost:8080/");
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
