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

import static spark.Spark.*;

/**
 * @author Christian Sweat
 */
public class App 
{
    public final static String CONFIG_FILE = "assets\\config.json";
    private final static BufferedReader READER = FileUtility.instantiateNewReader(CONFIG_FILE);
    private final static Gson GSON = new GsonBuilder().create();

    private final static MongoDatabase MONGODB_PROJECTS = new MongoUtility().establishDatabaseConnection("Projects");
    private final static ControllerFactory CONTROLLER_FACTORY = new ControllerFactory();

    public static void main( String[] args )
    {
        // Landing Page
        get("/", (req, res) -> "Hello World! Try navigating to /(project name)/(class name)");

        CONTROLLER_FACTORY.initialize();


        Iterator<Document> iterator = MONGODB_PROJECTS.getCollection("java-rest-api").find().iterator();

        System.out.println("\n\n\n\n\n");
        iterator.forEachRemaining(document -> System.out.println(document.toString()));
        System.out.println("\n\n\n\n\n");

    }

    public static BufferedReader getConfigReader() {
        return READER;
    }

    public static Gson getGson() {
        return GSON;
    }
}
