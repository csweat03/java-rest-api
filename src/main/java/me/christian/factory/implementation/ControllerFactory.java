package me.christian.factory.implementation;

import me.christian.controller.Controller;
import me.christian.controller.implementation.ProjectController;
import me.christian.factory.Factory;

public class ControllerFactory extends Factory<Controller> {

    public ControllerFactory() {
        super("Controller Factory");
    }

    @Override
    protected void populate() {
        add(new ProjectController());
    }

    @Override
    protected void execute() {

    }
}
