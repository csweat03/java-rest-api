package me.christian.factory.implementation;

import me.christian.controller.Controller;
import me.christian.controller.implementation.ProjectController;
import me.christian.controller.implementation.UserController;
import me.christian.factory.Factory;

public class ControllerFactory extends Factory<Controller> {

    public ControllerFactory() {
        super("Controller Factory");
    }

    @Override
    protected void populate() {
        add(new UserController(), new ProjectController());
    }

    @Override
    protected void execute() {
        getImplementations().forEach(Controller::initialize);
    }
}
