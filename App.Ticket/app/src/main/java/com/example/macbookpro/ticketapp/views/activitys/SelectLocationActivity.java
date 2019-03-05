package com.example.macbookpro.ticketapp.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.helper.location.FetchURL;
import com.example.macbookpro.ticketapp.helper.location.TaskLoadedCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class SelectLocationActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private MapView mapView;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    Button getLocation;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        getLocation = findViewById(R.id.btn_get_location);
        place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(SelectLocationActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(place1);
        mMap.addMarker(place2);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
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
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.map_api_key);
        return url;
    }

}
