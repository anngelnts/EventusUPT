package com.desarrollo.eventusupt.models;

public class ParticipantModel {
    private int id;
    private String name;
    private int assistance;
    private String assistanceTime;

    public ParticipantModel(int id, String name, int assistance, String assistanceTime) {
        this.id = id;
        this.name = name;
        this.assistance = assistance;
        this.assistanceTime = assistanceTime;
    }

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

    public int getAssistance() {
        return assistance;
    }

    public void setAssistance(int assistance) {
        this.assistance = assistance;
    }

    public String getAssistanceTime() {
        return assistanceTime;
    }

    public void setAssistanceTime(String assistanceTime) {
        this.assistanceTime = assistanceTime;
    }
}
