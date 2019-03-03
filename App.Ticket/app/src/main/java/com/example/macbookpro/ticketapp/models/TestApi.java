package com.example.macbookpro.ticketapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hoang Hai on 3/3/19.
 */
public class TestApi extends BaseApiModel implements Serializable {

    private Data data;



    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String id;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("user_name")
        private String userName;
        private String email;
        private String phone;
        private String address;
        @SerializedName("avatar_url")
        private String avatarUrl;
        private String description;
        private String accountType;
        @SerializedName("own_events")
        private List<Integer> ownEvents;
        @SerializedName("followed_events")
        private List<Integer> followedEvents;
        @SerializedName("joined_events")
        private List<Integer> joinedEvents;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public List<Integer> getOwnEvents() {
            return ownEvents;
        }

        public void setOwnEvents(List<Integer> ownEvents) {
            this.ownEvents = ownEvents;
        }

        public List<Integer> getFollowedEvents() {
            return followedEvents;
        }

        public void setFollowedEvents(List<Integer> followedEvents) {
            this.followedEvents = followedEvents;
        }

        public List<Integer> getJoinedEvents() {
            return joinedEvents;
        }

        public void setJoinedEvents(List<Integer> joinedEvents) {
            this.joinedEvents = joinedEvents;
        }
    }

}
