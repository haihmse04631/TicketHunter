package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.content.Context;
import android.databinding.Bindable;
import android.view.View;

import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.ResponseEvents;
import com.example.macbookpro.ticketapp.models.ResponseMessage;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.models.UserParam;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang Hai on 3/17/19.
 */
public class DetailUserVM extends BaseActivityVM {

    public User user;
    private Context mContext;
    private boolean flagIsUpdateState = false;
    private boolean flagIsUploadingImage = false;
    private boolean flagIsRequestUploadImage = false;
    private ApiListened listened;

    public DetailUserVM(Context mContext, ApiListened listened) {
        this.mContext = mContext;
        user = Ultil.getUserFromShardPreference(mContext);
        this.listened = listened;
    }

    public void completeUpdateProfile() {
        final UserParam userParam = new UserParam(user.getId(), user.getFirstName(), "", user.getEmail(), user.getPhone(), user.getAddress(), user.getAvatarUrl(), user.getDescription());
        Call<ResponseMessage> updateUserInforCall = ApiClient.getInstance().getApi().updateUserInfor(userParam);
        updateUserInforCall.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                Ultil.saveUserToSharedPreference(user, mContext);
                listened.onSuccessResponse("Update thông tin thành công!");
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                listened.onFailedResponse("Update thông tin không thành công!");
            }
        });
    }

    public void afterEdtNameChanged(CharSequence content) {
        String name = content.toString();
        user.setFirstName(name);
    }

    public void afterEdtEmailChanged(CharSequence content) {
        String email = content.toString();
        user.setEmail(email);
    }

    public void afterEdtPhoneChanged(CharSequence content) {
        String phone = content.toString();
        user.setPhone(phone);
    }

    public void afterEdtDescriptionChanged(CharSequence content) {
        String description = content.toString();
        user.setDescription(description);
    }

    @Bindable
    public boolean isFlagIsRequestUploadImage() {
        return flagIsRequestUploadImage;
    }

    public void setFlagIsRequestUploadImage(boolean flagIsRequestUploadImage) {
        this.flagIsRequestUploadImage = flagIsRequestUploadImage;
        notifyPropertyChanged(BR.flagIsRequestUploadImage);
    }

    @Bindable
    public boolean isFlagIsUploadingImage() {
        return flagIsUploadingImage;
    }

    public void setFlagIsUploadingImage(boolean flagIsUploadingImage) {
        this.flagIsUploadingImage = flagIsUploadingImage;
        notifyPropertyChanged(BR.flagIsUploadingImage);
    }

    @Bindable
    public boolean isFlagIsUpdateState() {
        return flagIsUpdateState;
    }

    public void setFlagIsUpdateState(boolean flagIsUpdateState) {
        this.flagIsUpdateState = flagIsUpdateState;
        notifyPropertyChanged(BR.flagIsUpdateState);
    }

    public interface ApiListened {
        void onSuccessResponse(String message);
        void onFailedResponse(String message);
    }

    public interface DetailUserActivityListened {
        void onUpdateProfileTapped(View view);
        void onUpdateAvatarTapped(View view);
        void onCompleteButtonTapped(View view);
    }
}
