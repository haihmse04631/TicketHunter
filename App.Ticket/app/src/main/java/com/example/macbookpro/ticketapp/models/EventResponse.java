package com.example.macbookpro.ticketapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hoang Hai on 3/14/19.
 */
public class EventResponse extends BaseApiModel {

    @SerializedName("data")
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
