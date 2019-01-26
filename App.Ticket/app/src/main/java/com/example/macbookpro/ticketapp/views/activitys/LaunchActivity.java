package com.example.macbookpro.ticketapp.views.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.models.User;
import com.google.gson.Gson;

public class LaunchActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.TK_SHARE_PREFERENCE, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constant.USER_DATA, null);
        if (json != null) {
            user = gson.fromJson(json, User.class);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(0,0);
        } else  {
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(0,0);
        }
    }

}
