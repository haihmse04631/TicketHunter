package com.example.macbookpro.ticketapp.models;

/**
 * Created by Hoang Hai on 3/19/19.
 */
public class UserInfor {

    private static UserInfor shared;
    private UserParam userParam;

    public UserParam getUserParam() {
        return userParam;
    }

    public void setUserParam(UserParam userParam) {
        this.userParam = userParam;
    }

    public static UserInfor getInstance() {
        if (shared == null) {
            shared = new UserInfor();
        }
        return shared;
    }

}
