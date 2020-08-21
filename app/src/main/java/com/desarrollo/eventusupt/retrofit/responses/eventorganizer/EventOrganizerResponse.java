package com.desarrollo.eventusupt.retrofit.responses.eventorganizer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventOrganizerResponse {
    @SerializedName("event")
    @Expose
    private Event event;
    @SerializedName("participants")
    @Expose
    private List<Participant> participants = null;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventOrganizerResponse withEvent(Event event) {
        this.event = event;
        return this;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public EventOrganizerResponse withParticipants(List<Participant> participants) {
        this.participants = participants;
        return this;
    }
}
