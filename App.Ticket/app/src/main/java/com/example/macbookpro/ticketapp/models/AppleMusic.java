package com.example.macbookpro.ticketapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 1/27/19.
 */
public class AppleMusic extends BaseApiModel implements Serializable {

    private Feed feed = new Feed();

    public class Feed {

        @SerializedName("entry")
        private List<Entry> entries;

        public Feed() {
            this.entries = new ArrayList<>();
        }

        public List<Entry> getEntries() {
            return entries;
        }

        public void setEntries(List<Entry> entries) {
            this.entries = entries;
        }
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
