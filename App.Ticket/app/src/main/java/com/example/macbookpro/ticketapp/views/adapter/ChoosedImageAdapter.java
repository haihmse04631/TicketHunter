package com.example.macbookpro.ticketapp.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ImageRowItemBinding;
import com.example.macbookpro.ticketapp.models.Image;

import java.util.List;

/**
 * Created by Hoang Hai on 2/23/19.
 */
public class ChoosedImageAdapter extends RecyclerView.Adapter<ChoosedImageAdapter.ChoosedImageViewHolder> {

    private List<Image> images;
    private LayoutInflater layoutInflater;
    private ChoosedImageAdapterListened listened;

    @NonNull
    @Override
    public ChoosedImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        ImageRowItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.image_row_item, viewGroup, false);
        return new ChoosedImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoosedImageViewHolder choosedImageViewHolder, final int i) {
        choosedImageViewHolder.binding.setImage(images.get(i));
        choosedImageViewHolder.binding.imageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public class ChoosedImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageRowItemBinding binding;

        public ChoosedImageViewHolder(ImageRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public ChoosedImageAdapter(List<Image> images, ChoosedImageAdapterListened listened) {
        this.images = images;
        this.listened = listened;
    }

    public interface ChoosedImageAdapterListened {
        void onImageItemTapped(Image image);
    }

}
