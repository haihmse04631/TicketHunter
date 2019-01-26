package com.example.macbookpro.ticketapp.views.fragments.favoritescreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentNavFavoriteBinding;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.base.NavigationFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavFavoriteFragment extends NavigationFragment {

    private FragmentNavFavoriteBinding binding;
    private User userInfor;

    public static NavFavoriteFragment newInstance() {

        Bundle args = new Bundle();

        NavFavoriteFragment fragment = new NavFavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentNavFavoriteBinding) getViewBinding();
        userInfor = Ultil.getUserInfor(getActivity());
        setupContentView(userInfor, binding.imgAvatar);
    }

    @Override
    protected BindingFragment onCreateMainFragment() {
        return FavoriteFragment.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.id.navigation_favorite;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_nav_favorite;
    }

}
