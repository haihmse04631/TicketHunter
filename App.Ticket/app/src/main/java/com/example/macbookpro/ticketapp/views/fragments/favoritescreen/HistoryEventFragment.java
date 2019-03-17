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
import com.example.macbookpro.ticketapp.databinding.FragmentHistoryEventBinding;
import com.example.macbookpro.ticketapp.models.TempEvent;
import com.example.macbookpro.ticketapp.viewmodels.fragments.HistoryEventVM;
import com.example.macbookpro.ticketapp.views.adapter.CategoryAdapter;
import com.example.macbookpro.ticketapp.views.adapter.EventListAdapter;
import com.example.macbookpro.ticketapp.views.adapter.HistoryEventsAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryEventFragment extends BindingFragment<FragmentHistoryEventBinding> implements HistoryEventVM.HistoryEventListened, HistoryEventsAdapter.HistoryEventListened {

    private FragmentHistoryEventBinding binding;
    private HistoryEventVM viewModel;
    private RecyclerView recyclerView;
    private HistoryEventsAdapter adapter;

    public static HistoryEventFragment newInstance() {

        Bundle args = new Bundle();

        HistoryEventFragment fragment = new HistoryEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewBinding();
        viewModel = new HistoryEventVM(getContext(),this);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getUserInfor();
    }

    private void initRecycleView() {
        recyclerView = binding.rcHistoryEvent;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new HistoryEventsAdapter(viewModel.followedEvents, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_history_event;
    }

    @Override
    public void onGetApiSuccess() {
        initRecycleView();
        CustomProgress.getInstance().hideLoading();
    }

    @Override
    public void ongetApiFailed() {
        Toast.makeText(getContext(), "Cập nhật thông tin người dùng không thành công!", Toast.LENGTH_LONG).show();
        CustomProgress.getInstance().hideLoading();
    }

    @Override
    public void onEventTapped(TempEvent event) {

    }
}
