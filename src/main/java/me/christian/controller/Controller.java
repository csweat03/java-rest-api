package me.christian.controller;

public abstract class Controller {

    protected String identifier;

    protected Controller(String identifier) {
        this.identifier = identifier;
    }

    protected abstract void establishRoutes();

    public void initialize() {
        establishRoutes();
        System.out.printf("%s has been initialized.%n", identifier);
    }

}
