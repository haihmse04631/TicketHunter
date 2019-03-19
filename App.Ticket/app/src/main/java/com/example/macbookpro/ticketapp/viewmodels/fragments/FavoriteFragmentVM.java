package com.example.macbookpro.ticketapp.viewmodels.fragments;

import android.content.Context;
import android.databinding.Bindable;
import android.view.View;

import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.TempEvent;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.models.UserInfor;
import com.example.macbookpro.ticketapp.models.UserResponse;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseFragmentVM;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang Hai on 1/14/19.
 */
public class FavoriteFragmentVM extends BaseFragmentVM {

    private boolean isAddedEventTapped = false;
    private User currentUser;

    public FavoriteFragmentVM(Context mContext) {
        currentUser = Ultil.getUserFromShardPreference(mContext);
    }

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
