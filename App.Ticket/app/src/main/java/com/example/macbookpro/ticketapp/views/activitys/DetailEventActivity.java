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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityDetailEventBinding;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.helper.location.FetchURL;
import com.example.macbookpro.ticketapp.helper.location.TaskLoadedCallback;
import com.example.macbookpro.ticketapp.helper.ultility.GridSpacingItemDecoration;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Comment;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.models.EventResponse;
import com.example.macbookpro.ticketapp.models.TempEvent;
import com.example.macbookpro.ticketapp.viewmodels.activitys.DetailEventActivityVM;
import com.example.macbookpro.ticketapp.viewmodels.activitys.DetailUserVM;
import com.example.macbookpro.ticketapp.views.adapter.CommentsAdapter;
import com.example.macbookpro.ticketapp.views.adapter.DetailListImageAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;
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

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventActivity extends BindingActivity implements OnMapReadyCallback, TaskLoadedCallback, DetailEventActivityVM.DetailEventListened, DetailListImageAdapter.ImageListListened, DetailEventActivityVM.DetailEventApiListened {

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
    private RecyclerView recyclerView;
    private DetailListImageAdapter adapter;
    private boolean isLoadedDirection = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomProgress.getInstance().showLoading(this);
        binding = (ActivityDetailEventBinding) getViewBinding();
        getPermission();
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        initViewBinding();

        viewModel.eventKey = getIntent().getStringExtra(EVENT_KEY);
    }

    private void initViewBinding() {
        viewModel = new DetailEventActivityVM(this, this);
        binding.setListened(this);
        binding.setViewModel(viewModel);
    }

    private void initRecycleView() {
        recyclerView = binding.rvListImage;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, Ultil.dpToPx(getResources(), 8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        adapter = new DetailListImageAdapter(viewModel.event.getImageLinks(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
        getEvent();
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
                                if (targetLocation != null && currentMarkerOptions != null && !isLoadedDirection) {
                                    new FetchURL(DetailEventActivity.this).execute(viewModel.getUrl(currentMarkerOptions.getPosition(),
                                            targetLocation.getPosition(),
                                            DIRECTION_MODE,
                                            getResources().getString(R.string.map_api_key)), DIRECTION_MODE);
                                    isLoadedDirection = true;
                                }
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

    @Override
    public void onFollowButtonTapped(View view) {
        if (!viewModel.isFollowed) {
            CustomProgress.getInstance().showLoading(this);
            viewModel.updateFollowedEvent();
        }
    }

    private void getEvent() {
        Call<EventResponse> eventResponseCall = ApiClient.getInstance().getApi().getEventById(viewModel.eventKey);
        eventResponseCall.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse eventResponse = response.body();
                viewModel.event = eventResponse.getEvent();
                binding.setEvent(viewModel.event);
                initRecycleView();
                viewModel.getUserInfor();
                getTargetLocation();
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(DetailEventActivity.this, "Lấy thông tin sự kiện không thành công!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getTargetLocation() {
        Log.e("Event Location", viewModel.event.getLocation());
        if (!viewModel.event.getLocation().isEmpty()) {
            String locationResponse = viewModel.event.getLocation();
            String[] parts = locationResponse.split(":");
            if (parts.length == 2) {
                String strLocationLat = parts[0];
                String strLocationLng = parts[1];
                try {
                    double locationLat = Double.parseDouble(strLocationLat.trim());
                    double locationLng = Double.parseDouble(strLocationLng.trim());
                    targetLocation = new MarkerOptions().position(new LatLng(locationLat, locationLng));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                mMap.addMarker(targetLocation);
            }

        } else {
            mMap.addMarker(targetLocation);
        }

        if (targetLocation != null && currentMarkerOptions != null && !isLoadedDirection) {
            new FetchURL(DetailEventActivity.this).execute(viewModel.getUrl(currentMarkerOptions.getPosition(),
                    targetLocation.getPosition(),
                    DIRECTION_MODE,
                    getResources().getString(R.string.map_api_key)), DIRECTION_MODE);
            isLoadedDirection = true;
        }

    }

    @Override
    public void onImageItemTapped(String imgUrl) {

    }

    @Override
    public void onGetUserInforSuccess() {
        CustomProgress.getInstance().hideLoading();
    }

    @Override
    public void onGetUserInforFailled() {
        CustomProgress.getInstance().hideLoading();
    }

    @Override
    public void onUpdateUserInforSuccess() {
        CustomProgress.getInstance().hideLoading();
    }
}
