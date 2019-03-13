package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.models.EventParam;
import com.example.macbookpro.ticketapp.models.ResponseMessage;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang Hai on 2/24/19.
 */
public class CreateEventVM extends BaseActivityVM {

    public Event event = new Event();
    public ResponseMessage responseMessage = new ResponseMessage();
    private CreateEventActivityListened listened;
    private Context mContext;
    public EventParam eventParam = new EventParam();
    public boolean isUsingMyContactChecked = false;

    @Bindable
    public boolean flagIsEventNameEmpty = false;
    @Bindable
    public boolean flagIsNumberTicketEmpty = false;
    @Bindable
    public boolean flagIsPriceTicketEmpty = false;
    @Bindable
    public boolean flagIsEmailEmpty = false;
    @Bindable
    public boolean flagIsPhoneEmpty = false;
    @Bindable
    public boolean flagIsConentEmpty = false;

    public CreateEventVM(Context context) {
        this.mContext = context;
        event.setCategory(CategoryTag.getValueWith(CategoryTag.index(CategoryTag.SPORT)));
    }

    public void pushEventToServer() {
        Call<ResponseMessage> call = ApiClient.getInstance().getApi().pushEvent(eventParam);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                responseMessage = response.body();
                Toast.makeText(mContext, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(mContext, "Call Api Failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void afterEventNameTextChanged(CharSequence content) {
        String eventName = content.toString();
        eventParam.setName(eventName);
    }

    public void afterNumberOfTicketChanged(CharSequence content) {
        String numberOfTicket = content.toString();
        if (!numberOfTicket.isEmpty()) {
            Log.e("numberTicket", numberOfTicket);
            eventParam.setNumberOfTicket(Integer.parseInt(numberOfTicket));
        } else {
            Log.e("numberTicket", numberOfTicket);
            eventParam.setNumberOfTicket(-1);
        }
    }

    public void afterTicketPriceChanged(CharSequence content) {
        String ticketPrice = content.toString();
        if (!ticketPrice.isEmpty()) {
            eventParam.setPrice(Integer.parseInt(ticketPrice));
        } else {
            eventParam.setPrice(-1);
        }
    }

    public void afterEmailChanged(CharSequence content) {
        String email = content.toString();
        eventParam.setEmail(email);
    }

    public void afterPhoneChanged(CharSequence content) {
        String phone = content.toString();
        eventParam.setPhone(phone);
    }

    public void afterContentChanged(CharSequence content) {
        String eventContent = content.toString();
        eventParam.setContent(eventContent);
    }

    public interface CreateEventActivityListened {
        void onSelectImageTapped(View view);
        void onDateTapped(View view);
        void onTimeTapped(View view);
        void onCheckboxTapped(View view);
        void onAvatarImageTapped(View view);
        void onMapViewTapped(View view);
        void onCreateEventTapped(View view);
    }

    public void notifyWarningTextViewStateChange() {
        flagIsEventNameEmpty = eventParam.getName().isEmpty();
        flagIsNumberTicketEmpty = eventParam.getNumberOfTicket() == -1;
        flagIsPriceTicketEmpty = eventParam.getPrice() == -1;
        flagIsConentEmpty = eventParam.getContent().isEmpty();
        flagIsEmailEmpty = eventParam.getEmail().isEmpty();
        flagIsPhoneEmpty = eventParam.getPhone().isEmpty();

        notifyPropertyChanged(BR.flagIsEventNameEmpty);
        notifyPropertyChanged(BR.flagIsNumberTicketEmpty);
        notifyPropertyChanged(BR.flagIsPriceTicketEmpty);
        notifyPropertyChanged(BR.flagIsConentEmpty);
        if (!isUsingMyContactChecked) {
            notifyPropertyChanged(BR.flagIsEmailEmpty);
            notifyPropertyChanged(BR.flagIsPhoneEmpty);
        }
    }

    public boolean isAllFeildFilled() {
        boolean isPassContactCheck = false;
        if (isUsingMyContactChecked) {
            isPassContactCheck = true;
        } else {
            isPassContactCheck = !flagIsEmailEmpty && !flagIsPhoneEmpty;
        }
        return !flagIsEventNameEmpty && !flagIsNumberTicketEmpty && !flagIsPriceTicketEmpty && !flagIsConentEmpty && isPassContactCheck;

    }

    public void setCategoryOfEventAt(int index) {
        event.setCategory(CategoryTag.getValueWith(index));
        eventParam.setCategory(CategoryTag.getValueWith(index));
        Log.e("category: ", CategoryTag.getValueWith(index));
    }

    public User getUserData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constant.TK_SHARE_PREFERENCE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constant.USER_DATA, null);
        if (json != null) {
            return gson.fromJson(json, User.class);
        }
        return new User();
    }

    public enum CategoryTag {
        SPORT,
        TRAVEL,
        FOOD,
        GAMESHOW,
        ACT,
        STUDY,
        TECHNOLOGY,
        OTHER,
        ECONOMIC;

        public static String getValueWith(int index) {
            CategoryTag[] categoryTagList = CategoryTag.values();
            CategoryTag categoryTag = categoryTagList[index];
            switch (categoryTag) {
                case SPORT:
                    return "Thể Thao";
                case TRAVEL:
                    return "Du Lịch";
                case FOOD:
                    return "Ẩm Thực";
                case GAMESHOW:
                    return "Game Show";
                case ACT:
                    return "Nghệ Thuật";
                case STUDY:
                    return "Học Tập";
                case TECHNOLOGY:
                    return "Công Nghệ";
                case OTHER:
                    return "Khác";
                case ECONOMIC:
                    return "Kinh Doanh";
                default:
                    return "";
            }
        }

        public static int index(CategoryTag tag) {
            CategoryTag[] categoryTagList = CategoryTag.values();
            for (int i = 0; i <= categoryTagList.length; i++) {
                if (tag == categoryTagList[i]) {
                    return i;
                }
            }
            return 0;
        }

    }

}
