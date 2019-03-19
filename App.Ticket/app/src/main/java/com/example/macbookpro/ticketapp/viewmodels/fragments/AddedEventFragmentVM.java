package com.example.macbookpro.ticketapp.viewmodels.fragments;

import android.content.Context;
import android.util.Log;

import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.TempEvent;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.models.UserInfor;
import com.example.macbookpro.ticketapp.models.UserParam;
import com.example.macbookpro.ticketapp.models.UserResponse;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseFragmentVM;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang Hai on 3/18/19.
 */
public class AddedEventFragmentVM extends BaseFragmentVM {

    private Context mContent;
    private AddedEventFragmentListened listened;
    public UserParam userParam;
    public List<TempEvent> addedEvents = new ArrayList<>();
    private User currentUser;

    public AddedEventFragmentVM(Context mContent, AddedEventFragmentListened listened) {
        this.mContent = mContent;
        this.listened = listened;
        currentUser = Ultil.getUserFromShardPreference(mContent);
    }

    public void getUserInfor() {
        final Call<UserResponse> userParamCall = ApiClient.getInstance().getApi().getUserInforById(currentUser.getId());
        userParamCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                userParam = userResponse.getUserParam();
                if (userParam != null) {
                    UserInfor.getInstance().setUserParam(userParam);
                    List<String> tempEvents = userParam.getOwnEvents();
                    Gson gson = new Gson();
                    addedEvents.clear();
                    for ( String eventJson : tempEvents ) {
                        TempEvent tempEvent = gson.fromJson(eventJson, TempEvent.class);
                        addedEvents.add(tempEvent);
                    }
                    listened.onGetApiSuccess();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                listened.onGetApiFailed();

            }
        });
    }

    public interface AddedEventFragmentListened {
        void onGetApiSuccess();
        void onGetApiFailed();
    }

}
