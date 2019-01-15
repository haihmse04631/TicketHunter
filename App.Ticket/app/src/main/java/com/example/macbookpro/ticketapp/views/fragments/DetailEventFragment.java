package com.example.macbookpro.ticketapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentDetailEventBinding;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEventFragment extends BindingFragment {

    FragmentDetailEventBinding binding;

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
        binding.navView.navTitle.setText("Detail Ticket");
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_detail_event;
    }
}
