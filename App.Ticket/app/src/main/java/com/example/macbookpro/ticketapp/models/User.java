package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 1/24/19.
 */
public class User extends BaseObservable implements Serializable {

    private String id = "";
    private String firstName = "";
    private String lastName = "";
    private String email;
    private String phone;
    private String address;
    private String avatarUrl;
    private String description = "";
    private String fullName = "";
    private List<String> ownEvents = new ArrayList<>();
    private List<String> followedEvents = new ArrayList<>();
    private List<String> joinedEvents = new ArrayList<>();

    public User(String id, String email, String phone, String avatarUrl) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
    }

    public User(String id, String firstName, String lastName, String email, String phone, String avatarUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
    }

    public User() {
    }

    public User(String id, String firstName, String lastName,
                String email, String phone, String address, String avatarUrl,
                String description, List<String> ownEvents, List<String> followedEvents, List<String> joinedEvents) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.description = description;
        this.ownEvents = ownEvents;
        this.followedEvents = followedEvents;
        this.joinedEvents = joinedEvents;
    }

    @Bindable
    public String getFirstName() {
        return firstName + " " + lastName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOwnEvents() {
        return ownEvents;
    }

    public void setOwnEvents(List<String> ownEvents) {
        this.ownEvents = ownEvents;
    }

    public List<String> getFollowedEvents() {
        return followedEvents;
    }

    public void setFollowedEvents(List<String> followedEvents) {
        this.followedEvents = followedEvents;
    }

    public List<String> getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(List<String> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Bindable
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        notifyPropertyChanged(BR.avatarUrl);
    }

    public void notifyUserDataChanged() {
        notifyPropertyChanged(BR.email);
        notifyPropertyChanged(BR.firstName);
        notifyPropertyChanged(BR.phone);
        notifyPropertyChanged(BR.description);
    }
}
