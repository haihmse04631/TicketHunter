package com.example.macbookpro.ticketapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hoang Hai on 3/12/19.
 */
public class ResponseEvents extends BaseApiModel {

    @SerializedName("data")
    private List<Event> eventList;

    public ResponseEvents(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
