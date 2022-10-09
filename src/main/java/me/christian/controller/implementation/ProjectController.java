package me.christian.controller.implementation;

import me.christian.controller.Controller;

import static spark.Spark.*;

public class ProjectController extends Controller {

    public ProjectController() {
        super("Project Controller");
    }

    @Override
    protected void establishRoutes() {
        get("/project/:name", (req, res) -> {
            String projectName = req.params(":name");
            return "get database information for " + projectName;
        });
        post("/project/:name", (req, res) -> {
            return "add new record to database";
        });
        put("/project/:name", (req, res) -> {
            return "modify record in database";
        });
        delete("/project/:name", (req, res) -> {
            return "delete record in database";
        });

    }
}
