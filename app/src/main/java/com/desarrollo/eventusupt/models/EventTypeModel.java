package com.desarrollo.eventusupt.models;

public class EventTypeModel {
    private int id;
    private String name;

    public EventTypeModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public EventTypeModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
