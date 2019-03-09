package com.example.macbookpro.ticketapp.views.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityDetailEventBinding;
import com.example.macbookpro.ticketapp.helper.location.FetchURL;
import com.example.macbookpro.ticketapp.helper.location.TaskLoadedCallback;
import com.example.macbookpro.ticketapp.models.Comment;
import com.example.macbookpro.ticketapp.viewmodels.activitys.DetailEventActivityVM;
import com.example.macbookpro.ticketapp.views.adapter.CommentsAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.example.macbookpro.ticketapp.views.dialog.CommentDialog;
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

public class DetailEventActivity extends BindingActivity implements OnMapReadyCallback, TaskLoadedCallback, DetailEventActivityVM.DetailEventListened {

    private static final int LOCATION_PERMISSTION_REQUEST_CODE = 1;
    private static final String DIRECTION_MODE = "driving";
    public static final String EVENT_KEY = "event_key";

    private ActivityDetailEventBinding binding;
    private MapView mapView;
    private Boolean isLocationGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private Polyline polyline;
    private MarkerOptions targetLocation = new MarkerOptions().position(new LatLng(21.02271831, 105.85263135));
    private MarkerOptions currentMarkerOptions;
    private DetailEventActivityVM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityDetailEventBinding) getViewBinding();
        getPermission();
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        initViewBinding();
    }

    private void initViewBinding() {
        viewModel = new DetailEventActivityVM();
        binding.setListened(this);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_event;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(targetLocation);
        initViewBinding();
        updateLocationUI();
        getDeviceLocation();

    }

    @Override
    public void onTaskDone(Object... values) {
        if (polyline != null) {
            polyline.remove();
        }
        polyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LOCATION_PERMISSTION_REQUEST_CODE) {
            mapView.setClickable(false);
            isLocationGranted = false;
        } else {
            mapView.setClickable(true);
            isLocationGranted = true;
        }
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isLocationGranted = false;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSTION_REQUEST_CODE);
        } else {
            isLocationGranted = true;
        }
    }

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
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                currentMarkerOptions = new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())).title("Location 2");
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), 15.0f));
//                                new FetchURL(DetailEventActivity.this).execute(viewModel.getUrl(currentMarkerOptions.getPosition(),
//                                                                                        targetLocation.getPosition(),
//                                                                                        DIRECTION_MODE,
//                                                                                        getResources().getString(R.string.map_api_key)), DIRECTION_MODE);
                            }
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onCommentTapped(View view) {
        showCommentDialog();
    }

    private void showCommentDialog() {
        viewModel.event.setId("123456789");
        CommentDialog commentDialog = new CommentDialog(this, viewModel.event);
        commentDialog.show();
        Window window = commentDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.x = 0;
        layoutParams.y = 30;
        window.setAttributes(layoutParams);
    }

    @Override
    public void onBackButtonTapped(View view) {
        super.onBackPressed();
    }

}
