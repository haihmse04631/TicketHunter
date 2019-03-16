package com.example.macbookpro.ticketapp.views.fragments.profilescreen;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentProfileBinding;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.viewmodels.fragments.ProfileFragmentVM;
import com.example.macbookpro.ticketapp.views.activitys.CreateEventActivity;
import com.example.macbookpro.ticketapp.views.activitys.LoginActivity;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.orhanobut.dialogplus.DialogPlus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BindingFragment implements ProfileFragmentVM.ProfileFragmentListened {

    private User user;

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
        getUserData();
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
        if (user != null) {
            doLogout(user);
        }
    }

    @Override
    public void onShowDialog(View view) {
        Intent intent = new Intent(getActivity(), CreateEventActivity.class);
        startActivity(intent);
    }

    @Override
    public void onShowMenu(View view) {

    }

    private void doLogout(User user) {
        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("Login Activity", "Logout Success");
                    }
                });
    }

    private void backToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    private void getUserData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constant.TK_SHARE_PREFERENCE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constant.USER_DATA, null);
        if (json != null) {
            user = gson.fromJson(json, User.class);
        }
    }

    private void clearUserData() {
        SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(Constant.TK_SHARE_PREFERENCE, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(null);
        sharedPreferences.putString(Constant.USER_DATA, json);
        sharedPreferences.apply();
    }

}
