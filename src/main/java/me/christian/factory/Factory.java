package me.christian.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Factory<T> {

    protected String identifier;

    protected Factory(String identifier) {
        this.identifier = identifier;
    }

    private final List<T> implementations = new ArrayList<>();

    protected void add(T... implementations) {
        this.implementations.addAll(Arrays.asList(implementations));
    }

    void remove(T... implementations) {
        this.implementations.removeAll(Arrays.asList(implementations));
    }

    protected abstract void populate();
    protected abstract void execute();

    public void initialize() {
        populate();
        execute();
        System.out.printf("%s has been initialized.%n", identifier);
    }

    public List<T> getImplementations() {
        return implementations;
    }

}
