package com.example.macbookpro.ticketapp.helper.ultility;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.models.UserParam;
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
                .placeholder(R.drawable.ic_avatar_circle)
                .error(R.drawable.ic_avatar_circle))
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

    public static String getFileName(Uri uri, ContentResolver contentResolver) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static void saveUserToSharedPreference(User user, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.TK_SHARE_PREFERENCE, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(Constant.USER_DATA, json);
        editor.apply();
    }

    public static User getUserFromShardPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.TK_SHARE_PREFERENCE, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constant.USER_DATA, null);
        if (json != null) {
            return gson.fromJson(json, User.class);
        }
        return new User();
    }

    public static void clearUserData(Context context) {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(Constant.TK_SHARE_PREFERENCE, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(null);
        sharedPreferences.putString(Constant.USER_DATA, json);
        sharedPreferences.apply();
    }

}
