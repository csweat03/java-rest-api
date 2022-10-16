package me.christian.controller.implementation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import me.christian.App;
import me.christian.controller.Controller;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Iterator;
import java.util.StringJoiner;

import static spark.Spark.get;

public class ProjectController extends Controller {

    public ProjectController() {
        super("Project Controller");
    }
    /**
     * post("/project/:name", (req, res) -> {
     *             return "add new record to database";
     *         });
     *         put("/project/:name", (req, res) -> {
     *             return "modify record in database";
     *         });
     *         delete("/project/:name", (req, res) -> {
     *             return "delete record in database";
     *         });
     */
    @Override
    protected void establishRoutes() {
        MongoCollection<Document> projectsCollection = App.getProjectDatabase().getCollection("projects");

        get("/api/projects", (req, res) -> {
            Iterator<Document> documents = App.getProjectDatabase().getCollection("projects").find().iterator();

            StringJoiner joiner = new StringJoiner(", ");
            documents.forEachRemaining(document -> joiner.add(document.toJson()));

            res.type("application/json");
            res.body("["+ joiner + "]");
            return res.body();
        });

        get("/api/project/:name", (req, res) -> {
            String projectName = req.params(":name");
            Bson nameFilter = Filters.eq("name", projectName);
            Document resultDocument = projectsCollection.find(nameFilter).first();

            if (resultDocument == null) {
                res.status(404);
            } else {
                res.type("application/json");
                res.body("["+ resultDocument.toJson() + "]");
            }
            return res.body();
        });
    }
}
