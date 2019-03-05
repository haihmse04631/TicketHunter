package com.example.macbookpro.ticketapp.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.R;

/**
 * Created by Hoang Hai on 2/23/19.
 */
public class Image extends BaseObservable {

    private int id;
    private Uri uri;
    private boolean flagIsLoading = false;
    private String name;

    public Image(int id, Uri uri, boolean flagIsLoading, String name) {
        this.id = id;
        this.uri = uri;
        this.flagIsLoading = flagIsLoading;
        this.name = name;
    }

    public Image(int id, Uri uri, Boolean flagIsLoading) {
        this.id = id;
        this.uri = uri;
        this.flagIsLoading = flagIsLoading;
    }

    public Image() {
    }

    @BindingAdapter("loadChoosedImage")
    public static void loadChoosedImage(ImageView view, Uri uri) {
        Glide.with(view.getContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.ic_avatar_circle)
                        .error(R.drawable.ic_avatar_circle))
                .load(uri)
                .into(view);
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        notifyPropertyChanged(BR.uri);
    }

    @Bindable
    public Boolean getFlagIsLoading() {
        return flagIsLoading;
    }

    public void setFlagIsLoading(Boolean flagIsLoading) {
        this.flagIsLoading = flagIsLoading;
        notifyPropertyChanged(BR.flagIsLoading);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
