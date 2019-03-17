package com.example.macbookpro.ticketapp.views.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.icu.util.ULocale;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.CommentRowItemBinding;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Comment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 3/9/19.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentListViewHolder> {

    private List<Comment> comments = new ArrayList<>();
    private List<String> commentIds = new ArrayList<>();
    private Context mContext;
    private String userId;
    private LayoutInflater layoutInflater;
    private CommentAdapterListened listened;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListened;

    @NonNull
    @Override
    public CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        CommentRowItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.comment_row_item, viewGroup, false);
        return new CommentListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListViewHolder commentListViewHolder, final int i) {
        commentListViewHolder.binding.setComment(comments.get(i));
        commentListViewHolder.binding.editViewContainer.setVisibility(userId.equals(comments.get(i).getUserId()) ? View.VISIBLE : View.GONE);
        commentListViewHolder.binding.starImg.setVisibility(comments.get(i).isAddmin() ? View.VISIBLE : View.GONE);
        commentListViewHolder.binding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listened.onEditTapped(comments.get(i), commentIds.get(i));
            }
        });
        commentListViewHolder.binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listened.onDeleteTapped(commentIds.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentListViewHolder extends RecyclerView.ViewHolder {

        private final CommentRowItemBinding binding;

        public CommentListViewHolder(CommentRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public CommentsAdapter(final Context context, DatabaseReference ref, CommentAdapterListened listened) {
        this.mContext = context;
        this.listened = listened;
        mDatabaseReference = ref;
        userId = Ultil.getUserFromShardPreference(mContext).getId();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                commentIds.add(dataSnapshot.getKey());
                comments.add(comment);
                notifyItemInserted(comments.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                int commentIndex = commentIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    comments.set(commentIndex, newComment);
                    notifyItemChanged(commentIndex);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String commentKey = dataSnapshot.getKey();

                int commentIndex = commentIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    commentIds.remove(commentIndex);
                    comments.remove(commentIndex);

                    notifyItemRemoved(commentIndex);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("Comment", "Move");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Load data failed!", Toast.LENGTH_LONG).show();
            }
        };

        ref.addChildEventListener(childEventListener);

        mChildEventListened = childEventListener;
    }

    public void cleanupListened() {
        if (mChildEventListened != null) {
            mDatabaseReference.removeEventListener(mChildEventListened);
        }
    }

    public interface CommentAdapterListened {
        void onEditTapped(Comment comment, String key);
        void onDeleteTapped(final String commentNodeKey);
    }

}
