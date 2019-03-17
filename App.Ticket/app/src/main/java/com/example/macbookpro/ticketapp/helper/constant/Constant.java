package com.example.macbookpro.ticketapp.helper.constant;

import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.models.TempEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class Constant {
    public static final String TK_SHARE_PREFERENCE = "tk_share_preference";

    //ACCOUNT
    public static final String USER_DATA = null;
    public static final String FACEBOOK_ACCOUNT = "facebook_account";
    public static final String TWITTER_ACCOUNT = "twitter_account";
    public static final String GOOGLE_ACCOUNT = "google_account";
    public static final String DEFAULT_ACCTION = "default_account";
    public static final String EMPTY_ACCOUNT = "emtyp_account";

    public static List<TempEvent> historyEvents = new ArrayList<>();
    public static List<Event> addedEvent = new ArrayList<>();
}
