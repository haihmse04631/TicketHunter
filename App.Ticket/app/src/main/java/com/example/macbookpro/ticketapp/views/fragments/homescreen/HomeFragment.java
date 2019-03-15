package com.example.macbookpro.ticketapp.views.fragments.homescreen;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentHomeBinding;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.models.AppleMusic;
import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.models.ResponseEvents;
import com.example.macbookpro.ticketapp.viewmodels.activitys.CreateEventVM;
import com.example.macbookpro.ticketapp.viewmodels.fragments.HomeFragmentVM;
import com.example.macbookpro.ticketapp.views.activitys.DetailEventActivity;
import com.example.macbookpro.ticketapp.views.adapter.CategoryAdapter;
import com.example.macbookpro.ticketapp.views.adapter.EventListAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;
import com.example.macbookpro.ticketapp.views.fragments.DetailEventFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BindingFragment implements CategoryAdapter.CategoryAdapterListened, HomeFragmentVM.HomeFragmentListened, EventListAdapter.EventListAdapterListened {

    private FragmentHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;
    private RecyclerView eventsRecycleView;
    private HomeFragmentVM homeFragmentVM;
    private EventListAdapter eventListAdapter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomProgress.getInstance().showLoading(getActivity());
        binding = (FragmentHomeBinding) getViewBinding();
        binding.setListened(this);
        homeFragmentVM = new HomeFragmentVM();
        homeFragmentVM.getCategories();
        initRecycleView();
        initEventsRecycleView();
        getEventWith("sport");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initRecycleView() {
        recyclerView = binding.categotiesView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        categoryAdapter = new CategoryAdapter(homeFragmentVM.categories, this);
        recyclerView.setAdapter(categoryAdapter);
    }

    private void initEventsRecycleView() {
        eventsRecycleView = binding.eventsView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        eventsRecycleView.setLayoutManager(layoutManager);
        eventsRecycleView.setNestedScrollingEnabled(false);
        eventListAdapter = new EventListAdapter(homeFragmentVM.events, this);
        eventsRecycleView.setAdapter(eventListAdapter);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected int containerViewId() {
        return R.id.navigation_home;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCategoryTapped(Category category) {
        getEventWith(category.getCategoryCode());
        CustomProgress.getInstance().showLoading(getContext());
    }

    @Override
    public void onShowAllCategoriesTapped(View view) {
        showNext(AllCategoriesFragment.newInstance());
    }

    @Override
    public void onEventTapped(Event event) {
        Intent intent = new Intent(getActivity(), DetailEventActivity.class);
        intent.putExtra(DetailEventActivity.EVENT_KEY, event.getId());
        startActivity(intent);
    }

    public void getEventWith(String category) {
        final Call<ResponseEvents> eventsCall = ApiClient.getInstance().getApi().getEventWithCategory(category);
        eventsCall.enqueue(new Callback<ResponseEvents>() {
            @Override
            public void onResponse(Call<ResponseEvents> call, Response<ResponseEvents> response) {
                ResponseEvents responseEvents = response.body();
                homeFragmentVM.events.clear();
                for (Event event : responseEvents.getEventList()){
                    homeFragmentVM.events.add(event);
                }
                Log.e("eventList", homeFragmentVM.events.size() + " - " + homeFragmentVM.events.get(0).getName());
                eventListAdapter.notifyDataSetChanged();
                eventsRecycleView.smoothScrollToPosition(0);
                CustomProgress.getInstance().hideLoading();
            }

            @Override
            public void onFailure(Call<ResponseEvents> call, Throwable t) {
                Toast.makeText(getContext(), "Get Api Failed!", Toast.LENGTH_LONG).show();
                CustomProgress.getInstance().hideLoading();
            }
        });
    }

}
