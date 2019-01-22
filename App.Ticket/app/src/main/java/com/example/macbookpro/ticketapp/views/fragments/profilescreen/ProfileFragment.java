package com.example.macbookpro.ticketapp.views.fragments.profilescreen;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentProfileBinding;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.viewmodels.fragments.ProfileFragmentVM;
import com.example.macbookpro.ticketapp.views.activitys.LoginActivity;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BindingFragment implements ProfileFragmentVM.ProfileFragmentListened {

    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentProfileBinding) getViewBinding();
        binding.setListened(this);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_profile;
    }

    @Override
    protected int containerViewId() {
        return R.layout.fragment_nav_profile;
    }

    @Override
    public void onLogoutTapped(View view) {
        SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(Constant.TK_SHARE_PREFERENCE, Context.MODE_PRIVATE).edit();
        sharedPreferences.putString(Constant.USER_ID, null);
        sharedPreferences.apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().finish();
        startActivity(intent);

    }
}
