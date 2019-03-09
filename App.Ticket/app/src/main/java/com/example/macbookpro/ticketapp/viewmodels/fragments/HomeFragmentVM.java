package com.example.macbookpro.ticketapp.viewmodels.fragments;

import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.models.AppleMusic;
import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.models.Entry;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseFragmentVM;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentVM extends BaseFragmentVM {

    public List<Category> categories = new ArrayList<>();
    public AppleMusic appleMusic = new AppleMusic();
    public List<Event> events = new ArrayList<>();

    public HomeFragmentVM() {
    }

    public void getCategories() {
        categories.add(new Category(R.drawable.category_sport, "Thể Thao", "1"));
        categories.add(new Category(R.drawable.category_sport, "Du Lịch", "2"));
        categories.add(new Category(R.drawable.category_sport, "Ẩm Thực", "3"));
        categories.add(new Category(R.drawable.category_sport, "Game Show", "4"));
        categories.add(new Category(R.drawable.category_sport, "Nghệ Thuật", "5"));
        categories.add(new Category(R.drawable.category_sport, "Học Tập", "6"));
        categories.add(new Category(R.drawable.category_sport, "Công Nghệ", "7"));
        categories.add(new Category(R.drawable.category_sport, "Kinh Doanh", "8"));
        categories.add(new Category(R.drawable.category_sport, "Khác", "9"));
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
