package com.example.macbookpro.ticketapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 3/16/19.
 */
public class UserParam implements Serializable {

    private String id = "";
    @SerializedName("first_name")
    private String firstName = "";
    @SerializedName("last_name")
    private String lastName = "";
    @SerializedName("user_name")
    private String userName = "";
    private String email = "";
    private String phone = "";
    private String address = "";
    @SerializedName("avatar_url")
    private String avatarUrl = "";
    private String description = "";
    @SerializedName("own_events")
    private List<String> ownEvents = new ArrayList<>();
    @SerializedName("followed_events")
    private List<String> followedEvents = new ArrayList<>();
    @SerializedName("joined_events")
    private List<String> joinedEvents = new ArrayList<>();

    public UserParam() {
    }

    public UserParam(String id, String firstName, String lastName, String userName, String email, String phone, String address, String avatarUrl, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.description = description;
    }

    public UserParam(String id, String firstName, String lastName, String email, String phone, String address, String avatarUrl, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.description = description;
    }

    public UserParam(String id) {
        this.id = id;
    }

    public UserParam(String id, String email, String phone, String avatarUrl) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOwnEvents() {
        return ownEvents;
    }

    public void setOwnEvents(String ownEventJson) {
        this.ownEvents.add(ownEventJson);
    }

    public List<String> getFollowedEvents() {
        return followedEvents;
    }

    public void setFollowedEvents(String eventId) {
        this.followedEvents.add(eventId);
    }

    public List<String> getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(List<String> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

}
