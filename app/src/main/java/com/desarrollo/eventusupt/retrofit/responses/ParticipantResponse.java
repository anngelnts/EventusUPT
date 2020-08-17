package com.desarrollo.eventusupt.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParticipantResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("event_id")
    @Expose
    private int event_id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("assistance")
    @Expose
    private int assistance;

    @SerializedName("assistance_date")
    @Expose
    private String assistance_date;

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAssistance() {
        return assistance;
    }

    public void setAssistance(int assistance) {
        this.assistance = assistance;
    }

    public String getAssistance_date() {
        return assistance_date;
    }

    public void setAssistance_date(String assistance_date) {
        this.assistance_date = assistance_date;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
