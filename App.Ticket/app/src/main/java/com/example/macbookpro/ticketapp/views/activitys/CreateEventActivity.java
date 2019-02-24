package com.example.macbookpro.ticketapp.views.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityCreateEventBinding;
import com.example.macbookpro.ticketapp.helper.ultility.GridSpacingItemDecoration;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Image;
import com.example.macbookpro.ticketapp.models.Navigation;
import com.example.macbookpro.ticketapp.viewmodels.activitys.CreateEventVM;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;
import com.example.macbookpro.ticketapp.views.adapter.ChoosedImageAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class CreateEventActivity extends BindingActivity implements ChoosedImageAdapter.ChoosedImageAdapterListened, CreateEventVM.CreateEventActivityListened {

    private static Integer CAMERA_MESSAGE_CODE = 1;

    private ActivityCreateEventBinding binding;
    private RecyclerView recyclerView;
    private ChoosedImageAdapter adapter;
    private List<Image> choosedImages;

    private int count = 0;
    private int numberOfImage = 0;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = (ActivityCreateEventBinding) getViewBinding();
        binding.setNavigation(new Navigation(R.drawable.ic_event, "Tạo Sự Kiện"));
        binding.setListened(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        choosedImages = new ArrayList<>();
        numberOfImage = choosedImages.size();

        initRecycleView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(numberOfImage != choosedImages.size()) uploadImage();
    }

    private void initRecycleView() {
        recyclerView = binding.rvListImage;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, Ultil.dpToPx(getResources(), 8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        adapter = new ChoosedImageAdapter(choosedImages, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_create_event;
    }

    @Override
    public void onImageItemTapped(Image image) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_MESSAGE_CODE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {

                int totalItemsSelected = data.getClipData().getItemCount();

                for (int i = 0; i < totalItemsSelected; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    String fileName = getFileName(uri);
                    Image image = new Image(i, uri, true, fileName);
                    choosedImages.add(image);
                    adapter.notifyDataSetChanged();
                }

            } else if (data.getData() != null) {
                Uri uri = data.getData();
                String fileName = getFileName(uri);
                Image image = new Image(count, uri, true, fileName);
                choosedImages.add(image);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void uploadImage() {
        Log.e("Count Upload Image: ", "" + count);
        Image image = choosedImages.get(count);
        StorageReference fileToUpload = storageReference.child("Libraries").child(image.getName());
        fileToUpload.putFile(image.getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Image imageItem = choosedImages.get(count);
                imageItem.setFlagIsLoading(false);
                adapter.notifyDataSetChanged();
                if (count < choosedImages.size() - 1) {
                    count++;
                    Log.e("Count Uploaded Image: ", "" + count);
                    uploadImage();
                } else {
                    Log.e("Count Uploaded Image: ", "" + count + " - Return");
                    return;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toastMessage("Upload Failed!");
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void openChooserIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CAMERA_MESSAGE_CODE);
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onSelectImageTapped(View view) {
        openChooserIntent();
    }
}
