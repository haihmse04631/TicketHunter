package com.example.macbookpro.ticketapp.views.fragments.homescreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentNavHomeBinding;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.base.NavigationFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavHomeFragment extends NavigationFragment {

    private FragmentNavHomeBinding binding;
    private User userInfor;

    public static NavHomeFragment newInstance() {

        Bundle args = new Bundle();

        NavHomeFragment fragment = new NavHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentNavHomeBinding) getViewBinding();
        userInfor = Ultil.getUserInfor(getActivity());
        setupContentView(userInfor, binding.imgAvatar);
    }

    @Override
    protected BindingFragment onCreateMainFragment() {
        return HomeFragment.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.id.navigation_home;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_nav_home;
    }

}
