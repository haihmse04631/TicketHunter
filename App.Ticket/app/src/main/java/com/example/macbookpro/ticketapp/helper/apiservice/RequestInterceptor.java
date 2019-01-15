package com.example.macbookpro.ticketapp.helper.apiservice;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
//        Put your header request here
//        builder.addHeader("name", "value");
        return chain.proceed(builder.build());
    }
}
