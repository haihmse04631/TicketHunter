package com.example.macbookpro.ticketapp.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.CategoryRowItemBinding;
import com.example.macbookpro.ticketapp.models.Category;

import java.util.List;

/**
 * Created by Hoang Hai on 1/23/19.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private LayoutInflater layoutInflater;
    private CategoryAdapterListened listened;

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final CategoryRowItemBinding binding;

        public CategoryViewHolder(final CategoryRowItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public CategoryAdapter(List<Category> categories, CategoryAdapterListened listened) {
        this.categories = categories;
        this.listened = listened;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        CategoryRowItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.category_row_item, viewGroup, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        categoryViewHolder.binding.setCategory(categories.get(i));
        categoryViewHolder.binding.categotyView.setOnClickListener(new View.OnClickListener() {
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

    public interface CategoryAdapterListened {
        void onCategoryTapped(Category category);
    }

}

