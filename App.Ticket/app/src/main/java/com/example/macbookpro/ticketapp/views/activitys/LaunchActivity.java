package com.example.macbookpro.ticketapp.views.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.helper.constant.Constant;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.TK_SHARE_PREFERENCE, MODE_PRIVATE);
        String userID = sharedPreferences.getString(Constant.USER_ID, null);
        if (userID == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        } else  {
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }

    }
}
