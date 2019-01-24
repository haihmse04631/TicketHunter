package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.macbookpro.ticketapp.BR;

/**
 * Created by Hoang Hai on 1/24/19.
 */
public class User extends BaseObservable {

    private String id;
    private String userName;
    private String avatarUrl;
    private String accountType;

    public User(String id, String userName, String avatarUrl) {
        this.id = id;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
    }

    public User(String id, String userName, String avatarUrl, String accountType) {
        this.id = id;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
        this.accountType = accountType;
    }

    @Bindable
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
        notifyPropertyChanged(BR.accountType);
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
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        notifyPropertyChanged(BR.avatarUrl);
    }
}
