package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Hoang Hai on 1/23/19.
 */
public class Category extends BaseObservable {
    private Integer imgCategory;
    private String categoryName;

    public Category(Integer imgCategory, String categoryName) {
        this.imgCategory = imgCategory;
        this.categoryName = categoryName;
    }

    @BindingAdapter("imgCategory")
    public static void loadImage(ImageView view, Integer imgCategory) {
        Glide.with(view.getContext())
                .load(imgCategory)
                .into(view);
    }

    public Integer getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(Integer imgCategory) {
        this.imgCategory = imgCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
