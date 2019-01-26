package com.example.macbookpro.ticketapp.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.AllCategoryRowItemBinding;
import com.example.macbookpro.ticketapp.models.Category;

import java.util.List;

/**
 * Created by Hoang Hai on 1/27/19.
 */
public class AllCategoiesAdapter extends RecyclerView.Adapter<AllCategoiesAdapter.AllCategoriesViewHolder> {

    private List<Category> categories;
    private LayoutInflater layoutInflater;
    private AllCategoryAdapterListened listened;

    public class AllCategoriesViewHolder extends RecyclerView.ViewHolder {

        private final AllCategoryRowItemBinding binding;

        public AllCategoriesViewHolder(AllCategoryRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public AllCategoiesAdapter(List<Category> categories, AllCategoryAdapterListened listened) {
        this.categories = categories;
        this.listened = listened;
    }


    @NonNull
    @Override
    public AllCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        AllCategoryRowItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.all_category_row_item, viewGroup, false);
        return new AllCategoriesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoriesViewHolder allCategoriesViewHolder, final int i) {
        allCategoriesViewHolder.binding.setCategory(categories.get(i));
        allCategoriesViewHolder.binding.categotyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listened != null) {
                    listened.onCategoryTapped(categories.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface AllCategoryAdapterListened {
        void onCategoryTapped(Category category);
    }
}
