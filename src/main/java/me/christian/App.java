package me.christian;

import me.christian.factory.implementation.ControllerFactory;

import static spark.Spark.*;

/**
 * @author Christian Sweat
 */
public class App 
{
    private final static ControllerFactory CONTROLLER_FACTORY = new ControllerFactory();

    public static void main( String[] args )
    {
        // Landing Page
        get("/", (req, res) -> "Hello World! Try navigating to /(project name)/(class name)");

        CONTROLLER_FACTORY.initialize();
    }
}
