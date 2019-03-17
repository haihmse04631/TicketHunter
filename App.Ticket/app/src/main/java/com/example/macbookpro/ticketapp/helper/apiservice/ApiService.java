package com.example.macbookpro.ticketapp.helper.apiservice;

import com.example.macbookpro.ticketapp.models.AppleMusic;
import com.example.macbookpro.ticketapp.models.EventParam;
import com.example.macbookpro.ticketapp.models.EventResponse;
import com.example.macbookpro.ticketapp.models.ResponseEvents;
import com.example.macbookpro.ticketapp.models.ResponseMessage;
import com.example.macbookpro.ticketapp.models.TestApi;
import com.example.macbookpro.ticketapp.models.UserParam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public interface ApiService {

    @GET("us/rss/topsongs/limit=20/genre={id}/explicit=true/json")
    Call<AppleMusic> getAppleMusic(@Path("id") String id);

    @GET("user/5c7a647fded80f004082b9ac")
    Call<TestApi> getUser();

    @POST("event")
    Call<ResponseMessage> pushEvent(@Body EventParam eventBody);

    @GET("event/category/{category}")
    Call<ResponseEvents> getEventWithCategory(@Path("category") String category);

    @GET("event/{id}")
    Call<EventResponse> getEventById(@Path("id") String id);

    @POST("user")
    Call<ResponseMessage> createUser(@Body UserParam userBody);

    @PUT("user")
    Call<ResponseMessage> updateUserInfor(@Body UserParam userBody);

}
