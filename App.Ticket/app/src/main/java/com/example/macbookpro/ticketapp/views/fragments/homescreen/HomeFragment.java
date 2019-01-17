package com.example.macbookpro.ticketapp.views.fragments.homescreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentHomeBinding;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiFacebook;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BindingFragment {

    private FragmentHomeBinding binding;
    private ApiFacebook apiFacebook;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentHomeBinding) getViewBinding();

        apiFacebook = new ApiFacebook();
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "loginning", Toast.LENGTH_SHORT).show();
                apiFacebook.processLogin();
                Log.i("FbMessage: ", "")
            }
        });

    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected int containerViewId() {
        return R.id.navigation_home;
    }
}
