package me.christian.controller;

import spark.Route;

public interface Controller {

    void get(String path, Route route);
    void post(String path, Route route);
    void put(String path, Route route);
    void delete(String path, Route route);

}
