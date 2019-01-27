package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hoang Hai on 1/27/19.
 */
public class Entry extends BaseObservable implements Serializable {

    @SerializedName("im:name")
    private Name name;

    public class Name {
        private String label;

        public String getLabel() {
            return "Name: " + label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    @SerializedName("im:image")
    private List<Image> images;
    public class Image {
        private String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    @SerializedName("im:collection")
    private Collection collection;
    public class Collection {

        @SerializedName("im:name")
        private Name name;
        public class Name {
            private String label;

            public String getLabel() {
                return "Collection: " + label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }
    }

    @SerializedName("im:price")
    private Price price;
    public class Price {
        private String label;

        public String getLabel() {
            return "Price: " + label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
