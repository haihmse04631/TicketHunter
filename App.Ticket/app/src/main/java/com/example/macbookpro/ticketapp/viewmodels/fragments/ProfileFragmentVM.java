package com.example.macbookpro.ticketapp.viewmodels.fragments;

import android.content.Context;
import android.view.View;

import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.User;

/**
 * Created by Hoang Hai on 1/22/19.
 */
public class ProfileFragmentVM {

    private Context mContext;

    public User user = new User();

    public ProfileFragmentVM(Context mContext) {
        this.mContext = mContext;
        user = Ultil.getUserFromShardPreference(mContext);
    }

    public interface ProfileFragmentListened {
        void onLogoutTapped(View view);
        void onCreateEventTapped(View view);
        void onShowMenu(View view);
    }

}
