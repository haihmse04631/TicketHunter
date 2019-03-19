package com.example.macbookpro.ticketapp.viewmodels.fragments;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.models.AppleMusic;
import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.models.Entry;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.models.ResponseEvents;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseFragmentVM;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentVM extends BaseFragmentVM {

    public List<Category> categories = new ArrayList<>();
    public AppleMusic appleMusic = new AppleMusic();
    public List<Event> events = new ArrayList<>();

    public HomeFragmentVM() {
    }

    public void getCategories() {
        categories.add(new Category(R.drawable.category_sport, "Thể Thao", "sport" , "1"));
        categories.add(new Category(R.drawable.travel, "Du Lịch", "travel" ,"2"));
        categories.add(new Category(R.drawable.cuisine, "Ẩm Thực", "food","3"));
        categories.add(new Category(R.drawable.gamshow, "Game Show", "gameshow","4"));
        categories.add(new Category(R.drawable.art, "Nghệ Thuật", "act","5"));
        categories.add(new Category(R.drawable.study, "Học Tập", "study","6"));
        categories.add(new Category(R.drawable.technology, "Công Nghệ", "technology","7"));
        categories.add(new Category(R.drawable.business, "Kinh Doanh", "economic","8"));
        categories.add(new Category(R.drawable.other, "Khác", "other","9"));
    }

    public void prepareDataEventList() {
        if (appleMusic.getFeed() != null) {
            List<Entry> entries = appleMusic.getFeed().getEntries();
            for (int i = 0; i < entries.size(); i++) {
                Entry entry = entries.get(i);
                String id = i + "";
                String name = entry.getName().getLabel();
                String collection = entry.getCollection().getName().getLabel();
                String price = entry.getPrice().getLabel();
                String imageUrl = entry.getImages().get(1).getLabel();
                Event event = new Event(id,name,collection,price,imageUrl);
                events.add(event);
            }
        }
    }

    public interface HomeFragmentListened {
        void onShowAllCategoriesTapped(View view);
    }

}
