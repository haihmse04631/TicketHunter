package com.example.macbookpro.ticketapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hoang Hai on 3/18/19.
 */
public class UserResponse {

    @SerializedName("data")
    private UserParam userParam;

    public UserParam getUserParam() {
        return userParam;
    }

    public void setUserParam(UserParam userParam) {
        this.userParam = userParam;
    }
}
