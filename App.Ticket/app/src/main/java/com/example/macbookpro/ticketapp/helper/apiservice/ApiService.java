package com.example.macbookpro.ticketapp.helper.apiservice;

import com.example.macbookpro.ticketapp.models.AppleMusic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public interface ApiService {

    @GET("us/rss/topsongs/limit=50/genre={id}/explicit=true/json")
    Call<AppleMusic> getAppleMusic(@Path("id") String id);

}
