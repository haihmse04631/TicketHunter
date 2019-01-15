package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class BaseApiModel extends BaseObservable {
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
