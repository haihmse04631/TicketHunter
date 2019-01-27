package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.macbookpro.ticketapp.BR;

/**
 * Created by Hoang Hai on 1/27/19.
 */
public class Event extends BaseObservable {

    private Integer id;
    private String name;
    private String collection;
    private String price;
    private String imageUrl;

    public Event(Integer id, String name, String collection, String price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.collection = collection;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
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
}
