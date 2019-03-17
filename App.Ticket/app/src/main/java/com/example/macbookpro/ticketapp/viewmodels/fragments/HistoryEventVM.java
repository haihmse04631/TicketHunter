package com.example.macbookpro.ticketapp.viewmodels.fragments;

import android.content.Context;

import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.TempEvent;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.models.UserParam;
import com.example.macbookpro.ticketapp.models.UserResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang Hai on 3/18/19.
 */
public class HistoryEventVM {

    private Context mContext;
    private HistoryEventListened listened;
    public UserParam userParam;
    public List<TempEvent> followedEvents = new ArrayList<>();
    private User currentUser;

    public HistoryEventVM(Context mContext, HistoryEventListened listened) {
        this.mContext = mContext;
        this.listened = listened;
        currentUser = Ultil.getUserFromShardPreference(mContext);
    }

    public void getUserInfor() {
        final Call<UserResponse> userParamCall = ApiClient.getInstance().getApi().getUserInforById(currentUser.getId());
        userParamCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                userParam = userResponse.getUserParam();
                List<String> tempEvents = userParam.getFollowedEvents();
                Gson gson = new Gson();
                followedEvents.clear();
                for ( String eventJson : tempEvents ) {
                    TempEvent tempEvent = gson.fromJson(eventJson, TempEvent.class);
                    followedEvents.add(tempEvent);
                }
                listened.onGetApiSuccess();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                listened.ongetApiFailed();

            }
        });
    }

    public interface HistoryEventListened {
        void onGetApiSuccess();
        void ongetApiFailed();
    }

}
