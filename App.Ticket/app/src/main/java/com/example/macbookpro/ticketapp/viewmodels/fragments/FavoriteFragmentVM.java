package com.example.macbookpro.ticketapp.viewmodels.fragments;

import android.databinding.Bindable;
import android.view.View;

import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseFragmentVM;

/**
 * Created by Hoang Hai on 1/14/19.
 */
public class FavoriteFragmentVM extends BaseFragmentVM {

    private boolean isAddedEventTapped = false;

    @Bindable
    public boolean isAddedEventTapped() {
        return isAddedEventTapped;
    }

    public void setAddedEventTapped(boolean addedEventTapped) {
        isAddedEventTapped = addedEventTapped;
        notifyPropertyChanged(BR.addedEventTapped);
    }

    public interface FavoriteFragmentActionCallBack {
        void onClickDetailTicket(View view);
        void onHistoryEventTapped(View view);
        void onAddedEventTapped(View view);
    }

}
