package com.example.macbookpro.ticketapp.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.DetailListImageRowItemBinding;
import com.example.macbookpro.ticketapp.models.Image;

import java.util.List;

/**
 * Created by Hoang Hai on 3/14/19.
 */
public class DetailListImageAdapter extends RecyclerView.Adapter<DetailListImageAdapter.DetailListImageViewHolder> {

    private List<String> images;
    private LayoutInflater layoutInflater;
    private ImageListListened listened;

    @NonNull
    @Override
    public DetailListImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        DetailListImageRowItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.detail_list_image_row_item, viewGroup, false);
        return new DetailListImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailListImageViewHolder detailListImageViewHolder, final int i) {
        detailListImageViewHolder.binding.setImageUrl(images.get(i));
        detailListImageViewHolder.binding.imageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listened != null) {
                    listened.onImageItemTapped(images.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class DetailListImageViewHolder extends RecyclerView.ViewHolder {

        private final DetailListImageRowItemBinding binding;

        public DetailListImageViewHolder(DetailListImageRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public DetailListImageAdapter(List<String> images, ImageListListened listened) {
        this.images = images;
        this.listened = listened;
    }

    public interface ImageListListened {
        void onImageItemTapped(String imgUrl);
    }

}
