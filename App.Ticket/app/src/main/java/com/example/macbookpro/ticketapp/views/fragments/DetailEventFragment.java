package com.example.macbookpro.ticketapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentDetailEventBinding;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEventFragment extends BindingFragment implements OnMapReadyCallback {

    private  FragmentDetailEventBinding binding;
    private MapView mapView;

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
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_detail_event;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
