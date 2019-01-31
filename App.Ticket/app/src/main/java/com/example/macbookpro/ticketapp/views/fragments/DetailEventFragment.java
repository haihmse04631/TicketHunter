package com.example.macbookpro.ticketapp.views.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentDetailEventBinding;
import com.example.macbookpro.ticketapp.helper.location.FetchURL;
import com.example.macbookpro.ticketapp.helper.location.TaskLoadedCallback;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEventFragment extends BindingFragment implements OnMapReadyCallback, LocationListener {

    private static final int LOCATION_PERMISSTION_REQUEST_CODE = 1;
    private static final String DIRECTION_MODE  = "driving";

    private FragmentDetailEventBinding binding;
    private MapView mapView;
    private Boolean isLocationGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private Polyline polyline;
    private MarkerOptions targetLocation = new MarkerOptions().position(new LatLng(20.995315343029425, 105.80840885639189));
    private MarkerOptions currentMarkerOptions;
    private Marker targetMarker;

    public static DetailEventFragment newInstance() {

        Bundle args = new Bundle();

        DetailEventFragment fragment = new DetailEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentDetailEventBinding) getViewBinding();
        getPermission();
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getCurrentContext());
    }

    private void getPermission() {
        if (getActivity() != null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                isLocationGranted = false;
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSTION_REQUEST_CODE);
            } else {
                isLocationGranted = true;
            }
        }
    }

    private void initMapEvent() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                if (targetMarker != null) {
//                    targetMarker.remove();
//                }
//                targetMarker = mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LOCATION_PERMISSTION_REQUEST_CODE) {
            mapView.setClickable(false);
            isLocationGranted = false;
        } else {
            mapView.setClickable(true);
            isLocationGranted = true;
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_detail_event;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        initMapEvent();

        updateLocationUI();

        getDeviceLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

//    @Override
//    public void onTaskDone(Object... values) {
//        if (polyline != null) {
//            polyline.remove();
//        }
//        polyline = mMap.addPolyline((PolylineOptions) values[0]);
//    }

    private void updateLocationUI() {
        if (mapView == null) {
            return;
        }

        try {
            if (isLocationGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                getPermission();
            }

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        try {
            if (isLocationGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            currentMarkerOptions = new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())).title("Location 2");
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), 15.0f));
                           // new FetchURL(getCurrentContext()).execute(getUrl(currentMarkerOptions.getPosition(), targetLocation.getPosition(), DIRECTION_MODE), DIRECTION_MODE);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
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
