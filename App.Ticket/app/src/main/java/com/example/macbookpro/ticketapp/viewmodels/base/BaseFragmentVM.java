package com.example.macbookpro.ticketapp.viewmodels.base;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.macbookpro.ticketapp.BR;

/**
 * Created by Hoang Hai on 1/15/19.
 */
public class BaseFragmentVM extends BaseObservable {

    private String title = "";

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public boolean isTitleEmpty() {
        if (title == "") {
            return true;
        } else {
            return false;
        }
    }
}
