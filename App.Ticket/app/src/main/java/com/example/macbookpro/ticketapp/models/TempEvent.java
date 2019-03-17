package com.example.macbookpro.ticketapp.models;

/**
 * Created by Hoang Hai on 3/18/19.
 */
public class TempEvent {

    private String id;
    private String name;
    private String avatarUrl;
    private String time;
    private String numberOfTicket;
    private String price;

    public TempEvent(String id, String name, String avatarUrl, String time, String numberOfTicket, String price) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.time = time;
        this.numberOfTicket = numberOfTicket;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumberOfTicket() {
        return numberOfTicket;
    }

    public void setNumberOfTicket(String numberOfTicket) {
        this.numberOfTicket = numberOfTicket;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
