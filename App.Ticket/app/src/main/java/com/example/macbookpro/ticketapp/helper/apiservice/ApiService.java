package com.example.macbookpro.ticketapp.helper.apiservice;

import com.example.macbookpro.ticketapp.models.AppleMusic;
import com.example.macbookpro.ticketapp.models.TestApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public interface ApiService {

    @GET("us/rss/topsongs/limit=20/genre={id}/explicit=true/json")
    Call<AppleMusic> getAppleMusic(@Path("id") String id);

    @GET("user/5c7a647fded80f004082b9ac")
    Call<TestApi> getUser();

}
