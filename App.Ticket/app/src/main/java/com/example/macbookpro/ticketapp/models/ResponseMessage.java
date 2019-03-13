package com.example.macbookpro.ticketapp.models;

import java.io.Serializable;

/**
 * Created by Hoang Hai on 3/10/19.
 */
public class ResponseMessage implements Serializable {

    private int status;
    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
