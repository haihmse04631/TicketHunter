package com.example.macbookpro.ticketapp.views.fragments.favoritescreen;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentFavoriteBinding;
import com.example.macbookpro.ticketapp.viewmodels.fragments.FavoriteFragmentVM;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.fragments.DetailEventFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends BindingFragment implements FavoriteFragmentVM.FavoriteFragmentActionCallBack {

    private FragmentFavoriteBinding binding;

    public static FavoriteFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentFavoriteBinding) getViewBinding();
        binding.setListened(this);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected int containerViewId() {
        return R.id.navigation_favorite;
    }

    @Override
    public void onClickDetailTicket(View view) {
        showNext(DetailEventFragment.newInstance());
    }
}
