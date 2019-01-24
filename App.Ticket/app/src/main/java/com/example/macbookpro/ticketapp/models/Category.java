package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Hoang Hai on 1/23/19.
 */
public class Category extends BaseObservable {
    private String imgCategory;
    private String categoryName;

    @BindingAdapter("imgCategory")
    public static void loadImage(ImageView view, String imgCategory) {
        Glide.with(view.getContext())
                .load(imgCategory)
                .into(view);
    }

    public String getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
