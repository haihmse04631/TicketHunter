package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.content.Context;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;

import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.models.EventResponse;
import com.example.macbookpro.ticketapp.models.ResponseMessage;
import com.example.macbookpro.ticketapp.models.TempEvent;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.models.UserInfor;
import com.example.macbookpro.ticketapp.models.UserParam;
import com.example.macbookpro.ticketapp.models.UserResponse;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;
import com.example.macbookpro.ticketapp.views.activitys.DetailEventActivity;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang Hai on 2/12/19.
 */
public class DetailEventActivityVM extends BaseActivityVM {

    public Event event = new Event();
    public String eventKey = "";
    public boolean isFollowed = false;
    private Context mContext;
    public User currentUser;
    public UserParam userParam;
    private DetailEventApiListened apiListened;

    public DetailEventActivityVM(Context context, DetailEventApiListened apiListened) {
        this.mContext = context;
        currentUser = Ultil.getUserFromShardPreference(mContext);
        this.apiListened = apiListened;
    }

    public void getUserInfor() {
        final Call<UserResponse> userParamCall = ApiClient.getInstance().getApi().getUserInforById(currentUser.getId());
        userParamCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                userParam = userResponse.getUserParam();
                UserInfor.getInstance().setUserParam(userParam);
                List<String> tempEvents = userParam.getFollowedEvents();
                Gson gson = new Gson();
                for ( String eventJson : tempEvents ) {
                    TempEvent tempEvent = gson.fromJson(eventJson, TempEvent.class);
                    if (tempEvent.getId().equals(event.getId())) {
                        setFollowed(true);
                    }
                }
                apiListened.onGetUserInforSuccess();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                apiListened.onGetUserInforSuccess();
            }
        });
    }

    public void updateFollowedEvent() {
        TempEvent tempEvent = new TempEvent(event.getId(), event.getName(), event.getImageUrl(), event.getTime(), event.getNumberOfTicket(), event.getPrice());
        Gson gson = new Gson();
        String json = gson.toJson(tempEvent);
        if (userParam != null) {
            userParam.setFollowedEvents(json);
            Call<ResponseMessage> updateFollowedCall = ApiClient.getInstance().getApi().updateUserInfor(userParam);
            updateFollowedCall.enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    setFollowed(true);
                    apiListened.onUpdateUserInforSuccess();
                }

                @Override
                public void onFailure(Call<ResponseMessage> call, Throwable t) {
                    apiListened.onGetUserInforFailled();
                }
            });
        }
    }

    @Bindable
    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
        notifyPropertyChanged(BR.followed);
    }

    public String getUrl(LatLng origin, LatLng dest, String directionMode, String mapApiKey) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + mapApiKey;
        return url;
    }

    public interface DetailEventApiListened {
        void onGetUserInforSuccess();
        void onGetUserInforFailled();
        void onUpdateUserInforSuccess();
    }

    public interface DetailEventListened {
        void onCommentTapped(View view);
        void onBackButtonTapped(View view);
        void onFollowButtonTapped(View view);
    }

}
