package com.example.macbookpro.ticketapp.views.fragments.favoritescreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddedEventFragment extends BindingFragment {

    public static AddedEventFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AddedEventFragment fragment = new AddedEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddedEventFragment() {
        // Required empty public constructor
    }


    @Override
    protected int layoutRes() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_added_event, container, false);
    }

}
