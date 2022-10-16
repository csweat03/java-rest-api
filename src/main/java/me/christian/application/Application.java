package me.christian.application;

public class Application {

    protected String identifier;

    protected Application(String identifier) {
        this.identifier = identifier;
        System.out.printf("%s has been initialized.%n", identifier);
    }

}
