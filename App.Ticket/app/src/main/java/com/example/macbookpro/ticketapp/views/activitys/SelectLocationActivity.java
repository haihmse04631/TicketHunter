package com.example.macbookpro.ticketapp.views.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivitySelectLocationBinding;
import com.example.macbookpro.ticketapp.viewmodels.activitys.SelectLocationVM;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class SelectLocationActivity extends BindingActivity<ActivitySelectLocationBinding>
        implements OnMapReadyCallback, SelectLocationVM.IClickItemCallBack {

    private static final String TAG = SelectLocationActivity.class.getSimpleName();
    private MapView mMapView;
    private GoogleMap mMap;
    private ActivitySelectLocationBinding mBinding;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private PlacesClient placesClient;
    private Place placeResult;
    private LatLng currentLocation;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_select_location;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewBinding();
        mMapView = mBinding.mapViewSelect;
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        String apiKey = getResources().getString(R.string.map_api_key);
        if (apiKey.isEmpty()) {
            return;
        }
        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        placesClient = Places.createClient(this);
        mBinding.setEvent(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                placeResult = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + placeResult.getName() + ", " + placeResult.getLatLng());
                showMarker(placeResult.getName(), placeResult.getLatLng());
            }
        }
    }

    private void moveMapToMyLocation() {
        LocationManager locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria crit = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = locMan.getLastKnownLocation(locMan.getBestProvider(crit, false));
        currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
        CameraPosition camPos = new CameraPosition.Builder()
                .target(currentLocation)
                .zoom(15.0f)
                .build();

        CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.moveCamera(camUpdate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Check to ensure coordinates aren't null, probably a better way of doing this...
        // mMapView.setCenterCoordinate(new LatLngZoom(mapView.getMyLocation().getLatitude(), mapView.getMyLocation().getLongitude(), 20), true);
        moveMapToMyLocation();
    }

    private void showMarker(String title, LatLng latLng) {
        if (latLng == null) {
            return;
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        if (placeResult != null && placeResult.getLatLng() != null) {
            returnIntent.putExtra("LocationName", placeResult.getName());
            returnIntent.putExtra("LocationLat", placeResult.getLatLng().latitude);
            returnIntent.putExtra("LocationLng", placeResult.getLatLng().longitude);
        } else {
            returnIntent.putExtra("LocationLat", currentLocation.latitude);
            returnIntent.putExtra("LocationLng", currentLocation.longitude);
        }
        setResult(CreateEventActivity.MAP_BUTTON_REQUEST_CODE, returnIntent);
        finish();
    }

    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void onSearchLocation() {
        List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(SelectLocationActivity.this);
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    }
}
