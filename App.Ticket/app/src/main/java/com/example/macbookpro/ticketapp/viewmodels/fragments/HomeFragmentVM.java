package com.example.macbookpro.ticketapp.viewmodels.fragments;

import android.util.Log;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.models.AppleMusic;
import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseFragmentVM;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentVM extends BaseFragmentVM {

    public List<Category> categories = new ArrayList<>();
    public AppleMusic appleMusic;

    public HomeFragmentVM() {
    }

    public void getCategories() {
        categories.add(new Category(R.drawable.category_sport, "Thể Thao", "1"));
        categories.add(new Category(R.drawable.category_sport, "Du Lịch","2"));
        categories.add(new Category(R.drawable.category_sport, "Ẩm Thực","3"));
        categories.add(new Category(R.drawable.category_sport, "Game Show","4"));
        categories.add(new Category(R.drawable.category_sport, "Nghệ Thuật", "5"));
        categories.add(new Category(R.drawable.category_sport, "Học Tập", "6"));
        categories.add(new Category(R.drawable.category_sport, "Công Nghệ", "7"));
        categories.add(new Category(R.drawable.category_sport, "Kinh Doanh", "8"));
        categories.add(new Category(R.drawable.category_sport, "Khác", "9"));
    }

    public interface HomeFragmentListened {
        void onShowAllCategoriesTapped(View view);
    }

    public void getAppleMusic(String id) {
        final Call<AppleMusic> appleMusicCall = ApiClient.getInstance().getApi().getAppleMusic(id);
        appleMusicCall.enqueue(new Callback<AppleMusic>() {
            @Override
            public void onResponse(Call<AppleMusic> call, Response<AppleMusic> response) {
                //appleMusic = response.body();
                Log.e("Response", response.isSuccessful() + "");
                CustomProgress.getInstance().hideLoading();
            }

            @Override
            public void onFailure(Call<AppleMusic> call, Throwable t) {

            }
        });
    }

}
