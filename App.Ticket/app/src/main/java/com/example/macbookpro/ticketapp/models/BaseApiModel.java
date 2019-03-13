package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class BaseApiModel extends BaseObservable implements Serializable {
    @SerializedName("status")
    private int resultCode;
    private String message;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
