package com.example.macbookpro.ticketapp.models;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Hoang Hai on 1/20/19.
 */
public class Navigation extends BaseObservable {

    private Integer imageResource;
    private String title;

    public Navigation(Integer imageResource, String title) {
        this.imageResource = imageResource;
        this.title = title;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    @Bindable
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
