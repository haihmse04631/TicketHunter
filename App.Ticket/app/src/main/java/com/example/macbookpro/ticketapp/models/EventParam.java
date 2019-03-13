package com.example.macbookpro.ticketapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 3/10/19.
 */
public class EventParam implements Serializable {

    @SerializedName("id")
    private String id = "";
    @SerializedName("title")
    private String name = "";
    @SerializedName("description")
    private String content = "";
    @SerializedName("avatar_url")
    private String imageUrl = "";
    private String location = "21.032180:105.782500";
    private String category = "";
    @SerializedName("image_url")
    private List<String> imageLinks = new ArrayList<>();
    @SerializedName("own_id")
    private String ownId = "";
    private String time = "10/10/2019 - 10:10";
    @SerializedName("ticket_number")
    private int numberOfTicket = -1;
    @SerializedName("ticket_price")
    private int price = -1;
    private String phone = "";
    private String email = "";
    @SerializedName("joined_users")
    private List<String> userJoined = new ArrayList<>();
    @SerializedName("followed_users")
    private List<String> followUser = new ArrayList<>();

    public EventParam() {
    }

    public EventParam(String id, String name, String content, String imageUrl, String location, String category, List<String> imageLinks, String ownId, String time, int numberOfTicket, int price, String phone, String email, List<String> userJoined, List<String> followUser) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imageUrl = imageUrl;
        this.location = location;
        this.category = category;
        this.imageLinks = imageLinks;
        this.ownId = ownId;
        this.time = time;
        this.numberOfTicket = numberOfTicket;
        this.price = price;
        this.phone = phone;
        this.email = email;
        this.userJoined = userJoined;
        this.followUser = followUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getOwnId() {
        return ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumberOfTicket() {
        return numberOfTicket;
    }

    public void setNumberOfTicket(int numberOfTicket) {
        this.numberOfTicket = numberOfTicket;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getUserJoined() {
        return userJoined;
    }

    public void setUserJoined(List<String> userJoined) {
        this.userJoined = userJoined;
    }

    public List<String> getFollowUser() {
        return followUser;
    }

    public void setFollowUser(List<String> followUser) {
        this.followUser = followUser;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + numberOfTicket + " - " + price + " - " + location + " - " + category + " - " + imageLinks.size() + " - " + imageUrl + " - " + ownId + " - " + time + " - " + phone;
    }
}
