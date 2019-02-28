package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.macbookpro.ticketapp.BR;

import java.util.List;

/**
 * Created by Hoang Hai on 1/27/19.
 */
public class Event extends BaseObservable {

    private int id;
    private String name;
    private String collection;
    private String price;
    private String imageUrl;
    private String location;
    private String date = "10/10/2019";
    private String time = "10:10";
    private int numberOfTicket;
    private String category;
    private String email;
    private String phone;
    private String content;
    private List<String> imageLinks;
    private boolean flagIsCheckboxContactChecked = false;

    public Event() {
    }

    public Event(int id, String name, String collection, String price, String imageUrl, String location, String date,
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

    public Event(Integer id, String name, String collection, String price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.collection = collection;
        this.price = price;
        this.imageUrl = imageUrl;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    public int getNumberOfTicket() {
        return numberOfTicket;
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
    public boolean isFlagIsCheckboxContactChecked() {
        return flagIsCheckboxContactChecked;
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    public void setFlagIsCheckboxContactChecked(boolean flagIsCheckboxContactChecked) {
        this.flagIsCheckboxContactChecked = flagIsCheckboxContactChecked;
        notifyPropertyChanged(BR.flagIsCheckboxContactChecked);
    }
}
