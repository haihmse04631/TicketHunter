package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.databinding.Bindable;
import android.view.View;

import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;
import com.example.macbookpro.ticketapp.views.activitys.DetailEventActivity;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Hoang Hai on 2/12/19.
 */
public class DetailEventActivityVM extends BaseActivityVM {

    public Event event = new Event();

    public DetailEventActivityVM() {
        event.setName("Flutter Xu Thế Tương Lai?");
        event.setId("123456");
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

    public interface DetailEventListened {
        void onCommentTapped(View view);
        void onBackButtonTapped(View view);
    }

}
