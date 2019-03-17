package com.example.macbookpro.ticketapp.viewmodels.dialog;

import android.content.Context;
import android.view.View;

import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Comment;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 3/9/19.
 */
public class CommentDialogVM {

    public List<Comment> comments = new ArrayList<>();
    public Event event = new Event();
    private Context mContext;
    public User user;

    public CommentDialogVM(Context mContext) {
        this.mContext = mContext;
        user = Ultil.getUserFromShardPreference(mContext);
    }

    public void getComments() {
        comments.add(new Comment("1234566","2", "Ice Tea", "https://images-na.ssl-images-amazon.com/images/I/81MQcUJi-UL._SX425_.jpg", "Ahihi Ahoho"));
        comments.add(new Comment("2123","12", "Apple", "https://images-na.ssl-images-amazon.com/images/I/81MQcUJi-UL._SX425_.jpg", "Hoang Hai Handsome"));
        comments.add(new Comment("123456","1", "Apple", "https://images-na.ssl-images-amazon.com/images/I/81MQcUJi-UL._SX425_.jpg", "Hoang Hai Handsome Hoang Hai Handsome Hoang Hai Handsome Hoang Hai Handsome "));
        comments.add(new Comment("123312","3", "La La La", "https://images-na.ssl-images-amazon.com/images/I/81MQcUJi-UL._SX425_.jpg", "Ahihihihi"));
    }

    public interface CommentDialogListened {
        void onBackTapped(View view);
        void onSendCommentTapped(View view);
    }

}
