package com.example.macbookpro.ticketapp.helper.ultility;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.macbookpro.ticketapp.R;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class Ultil {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    @BindingAdapter("imageCircle")
    public static void loadCirleImage(ImageView view, String imageCircle) {
        Glide.with(view.getContext())
                .applyDefaultRequestOptions(new RequestOptions()
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar))
                .load(imageCircle)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    public static Integer dpToPx(Resources resources , int dp) {
        Resources r = resources;
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
