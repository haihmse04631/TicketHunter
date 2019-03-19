package com.example.macbookpro.ticketapp.views.fragments.favoritescreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentAddedEventBinding;
import com.example.macbookpro.ticketapp.models.TempEvent;
import com.example.macbookpro.ticketapp.viewmodels.fragments.AddedEventFragmentVM;
import com.example.macbookpro.ticketapp.views.adapter.AddedEventAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddedEventFragment extends BindingFragment<FragmentAddedEventBinding> implements AddedEventFragmentVM.AddedEventFragmentListened, AddedEventAdapter.AddedEventAdapterListened {

    private FragmentAddedEventBinding binding;
    private AddedEventFragmentVM viewModel;
    private RecyclerView recyclerView;
    private AddedEventAdapter adapter;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewBinding();
        viewModel = new AddedEventFragmentVM(getContext(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("added life cycle", "on resume");
        viewModel.getUserInfor();
    }

    private void initRecyleView() {
        recyclerView = binding.rcAddedEvent;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new AddedEventAdapter(viewModel.addedEvents, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_added_event;
    }


    @Override
    public void onGetApiSuccess() {
        initRecyleView();
    }

    @Override
    public void onGetApiFailed() {
        Toast.makeText(getContext(), "Cập nhật thông tin người dùng không thành công!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateOptionTapped(TempEvent event) {

    }

    @Override
    public void onDeleteOptionTapped(TempEvent event) {

    }
}
