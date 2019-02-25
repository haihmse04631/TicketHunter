package com.example.macbookpro.ticketapp.helper.ultility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.models.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class Ultil {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.ic_avatar)
                        .error(R.drawable.ic_avatar))
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
        Resources res = resources;
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics()));
    }

    public static User getUserInfor(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.TK_SHARE_PREFERENCE, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(Constant.USER_DATA, null);
            if (json != null) {
                return gson.fromJson(json, User.class);
            }
        }
        return null;
    }

    public static boolean isEmpty(String content) {
        if (content == null) {
            return true;
        } else {
            return content.isEmpty();
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return  File.createTempFile(imageFileName, ".jpg", storageDir);
    }

}
