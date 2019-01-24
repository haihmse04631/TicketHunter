package com.example.macbookpro.ticketapp.viewmodels.fragments;

import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseFragmentVM;

import java.util.ArrayList;
import java.util.Calendar;
import com.example.macbookpro.ticketapp.R;
import java.util.List;

public class HomeFragmentVM extends BaseFragmentVM {

    public List<Category> categories = new ArrayList<>();

    public HomeFragmentVM() {
    }

    public void getCategories() {
        categories.add(new Category(R.drawable.category_sport, "Thể Thao"));
        categories.add(new Category(R.drawable.category_sport, "Du Lịch"));
        categories.add(new Category(R.drawable.category_sport, "Ẩm Thực"));
        categories.add(new Category(R.drawable.category_sport, "Game Show"));
        categories.add(new Category(R.drawable.category_sport, "Nghệ Thuật"));
        categories.add(new Category(R.drawable.category_sport, "Học Tập"));
        categories.add(new Category(R.drawable.category_sport, "Công Nghệ"));
        categories.add(new Category(R.drawable.category_sport, "Kinh Doanh"));
        categories.add(new Category(R.drawable.category_sport, "Khác"));
    }

}
