package me.christian.controller.implementation;

import com.mongodb.client.MongoCollection;
import me.christian.App;
import me.christian.application.implementation.UserApplication;
import me.christian.controller.Controller;
import org.bson.Document;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class UserController extends Controller {

    public UserController() {
        super("User Controller");
    }

    @Override
    protected void establishRoutes() {
        MongoCollection<Document> usersCollection = App.getUserDatabase().getCollection("users");



//        LinkedHashMap<String, Object> administratorData = new LinkedHashMap<>();
//
//        administratorData.put("username", "csweat03");
//        administratorData.put("password", "password");
//        administratorData.put("role-type", "administrator");
////        administratorData.put("api-key", new User("your", "mom").generateApiKey());
//
//        usersCollection.insertOne(new Document(administratorData));
    }
}
