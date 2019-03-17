package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hoang Hai on 3/8/19.
 */
public class Comment extends BaseObservable implements Serializable {

    private String eventId = "";
    private String userId = "";
    private String name = "";
    private String avatarUrl = "";
    private String content = "";
    private boolean isAddmin;

    public Comment() {

    }

    public Comment(String userId, String name, String avatarUrl, String content) {
        this.userId = userId;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.content = content;
    }

    public Comment(String eventId, String userId, String name, String avatarUrl, String content) {
        this.eventId = eventId;
        this.userId = userId;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.isAddmin =  eventId.equals(userId);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("content", content);
        return result;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Bindable
    public boolean isAddmin() {
        return isAddmin;
    }

    public void setAddmin(boolean addmin) {
        isAddmin = addmin;
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
