package com.example.macbookpro.ticketapp.views.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.CustomCommentDialogBinding;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Comment;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.viewmodels.dialog.CommentDialogVM;
import com.example.macbookpro.ticketapp.views.adapter.CommentsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hoang Hai on 3/9/19.
 */
public class CommentDialog extends Dialog implements CommentDialogVM.CommentDialogListened, CommentsAdapter.CommentAdapterListened {

    private String COMMENT_KEY = "Event_Comments";

    private Activity mActivity;
    private Dialog mDialog;
    private Event event;
    private RecyclerView recyclerView;
    private CustomCommentDialogBinding binding;
    private CommentsAdapter adapter;
    private CommentDialogVM viewModel;
    private DatabaseReference mCommentReference;
    private boolean isEditState = false;
    private String currentNodeKey = "";

    public CommentDialog(Activity activity, Event event) {
        super(activity);
        this.mActivity = activity;
        this.event = event;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.custom_comment_dialog, null, false);
        setContentView(binding.getRoot());
        viewModel = new CommentDialogVM(mActivity);
        viewModel.event = event;

        binding.setListened(this);
        binding.setViewModel(viewModel);

        mCommentReference = FirebaseDatabase.getInstance().getReference()
                            .child(COMMENT_KEY).child(viewModel.event.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        configCommentRecycleView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.cleanupListened();
    }

    @Override
    public void onBackTapped(View view) {
        dismiss();
    }

    @Override
    public void onSendCommentTapped(View view) {
        String commentContent = binding.edtCommentContent.getText().toString();
        if (!Ultil.isEmpty(commentContent)) {
            if (isEditState) {
                sendCommentEditState(commentContent);
            } else {
                sendCommentNormalState(commentContent);
            }
        }
        binding.edtCommentContent.setText(null);
    }

    private void sendCommentNormalState(String commentContent) {
        Comment comment = new Comment(viewModel.event.getOwnId(), viewModel.user.getId(), viewModel.user.getFirstName(),
                viewModel.user.getAvatarUrl(), commentContent);
        sendComment(comment);
    }

    private void sendCommentEditState(String commentContent) {
        Map<String, Object> contentUpdate = new HashMap<>();
        contentUpdate.put("/content/", commentContent);
        mCommentReference.child(currentNodeKey).updateChildren(contentUpdate);
        isEditState = false;
    }

    private void configCommentRecycleView() {
        recyclerView = binding.rvListComment;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new CommentsAdapter(getContext(), mCommentReference, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditTapped(Comment comment, String key) {
        isEditState = true;
        binding.edtCommentContent.setText(comment.getContent());
        currentNodeKey = key;
    }

    @Override
    public void onDeleteTapped(final String commentNodeKey) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn chắc chắn muốn xoá?")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCommentReference.child(commentNodeKey).removeValue();
                    }
                })
                .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void sendComment(final Comment comment) {
        mCommentReference.child(COMMENT_KEY).child(viewModel.event.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("eventID", viewModel.event.getId());
                        Log.e("Comment:", comment.getContent() + " - " + comment.getName());
                        mCommentReference.push().setValue(comment);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
