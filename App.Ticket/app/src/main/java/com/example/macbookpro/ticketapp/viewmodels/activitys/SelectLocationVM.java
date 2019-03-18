package com.example.macbookpro.ticketapp.viewmodels.activitys;

import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;

/**
 * Created by Truong.Han on 3/18/19.
 * VIETIS Corporation
 * truong.han@vietis.com.vn
 */
public class SelectLocationVM extends BaseActivityVM {
    public interface IClickItemCallBack {

        void onBack();

        void onSearchLocation();
    }
}
