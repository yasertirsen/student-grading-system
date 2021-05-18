package controller;

import model.Rubric;

import java.util.ArrayList;

public class Controller {

    public Rubric createRubric(String name) {
        return new Rubric(name, new ArrayList<>());
    }
}
