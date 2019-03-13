package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.macbookpro.ticketapp.BR;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 1/27/19.
 */
public class Event extends BaseObservable implements Serializable {

    @SerializedName("id")
    private String id = "";
    @SerializedName("title")
    private String name = "";
    @SerializedName("description")
    private String content;
    @SerializedName("avatar_url")
    private String imageUrl;
    private String location = "";
    private String category = "";
    @SerializedName("image_url")
    private List<String> imageLinks = new ArrayList<>();
    @SerializedName("own_id")
    private String ownId = "";
    private String time = "10:10";
    @SerializedName("ticket_number")
    private int numberOfTicket;
    @SerializedName("ticket_price")
    private String price;
    private String phone;
    @SerializedName("joined_users")
    private List<String> userJoined = new ArrayList<>();
    @SerializedName("followed_users")
    private List<String> followUser = new ArrayList<>();
    private String date = "10/10/2019";
    private String email;
    private String collection;

    public Event() {
    }

    public Event(String id, String name, String collection, String price, String imageUrl, String location, String date,
                 String time, int numberOfTicket, String category, String email, String phone, String content, List<String> imageLinks) {
        this.id = id;
        this.name = name;
        this.collection = collection;
        this.price = price;
        this.imageUrl = imageUrl;
        this.location = location;
        this.date = date;
        this.time = time;
        this.numberOfTicket = numberOfTicket;
        this.category = category;
        this.email = email;
        this.phone = phone;
        this.content = content;
        this.imageLinks = imageLinks;
    }

    public Event(String id, String name, String collection, String price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.collection = collection;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getOwnId() {
        return ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
        notifyPropertyChanged(BR.collection);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
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
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        notifyPropertyChanged(BR.location);
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public String getNumberOfTicket() {
        return numberOfTicket + "";
    }

    public void setNumberOfTicket(int numberOfTicket) {
        this.numberOfTicket = numberOfTicket;
        notifyPropertyChanged(BR.numberOfTicket);
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Bindable
    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
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
}
