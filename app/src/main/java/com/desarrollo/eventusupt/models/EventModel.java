package com.desarrollo.eventusupt.models;

public class EventModel {
    private int id;
    private String provider;
    private String title;
    private String image;
    private String dateString;

    public EventModel(int id, String provider, String title, String image, String dateString) {
        this.id = id;
        this.provider = provider;
        this.title = title;
        this.image = image;
        this.dateString = dateString;
    }

    public EventModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
