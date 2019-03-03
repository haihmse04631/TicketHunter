package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.util.Log;
import android.view.View;

import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 2/24/19.
 */
public class CreateEventVM extends BaseActivityVM {

    public Event event = new Event();

    public CreateEventVM() {
        event.setCategory(CategoryTag.getValueWith(CategoryTag.index(CategoryTag.SPORT)));
    }

    public interface CreateEventActivityListened {
        void onSelectImageTapped(View view);
        void onDateTapped(View view);
        void onTimeTapped(View view);
        void onCheckboxTapped(View view);
        void onAvatarImageTapped(View view);
    }

    public void setCategoryOfEventAt(int index) {
        event.setCategory(CategoryTag.getValueWith(index));
        Log.e("category: ", CategoryTag.getValueWith(index));
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
