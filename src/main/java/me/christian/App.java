package me.christian;

import static spark.Spark.*;
/**
 * @author Christian Sweat
 */
public class App 
{
    public static void main( String[] args )
    {
        get("/hello", (req, res) -> "Hello World");
    }
}
