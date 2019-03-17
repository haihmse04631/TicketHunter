package com.example.macbookpro.ticketapp.views.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityDetailUserBinding;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Navigation;
import com.example.macbookpro.ticketapp.viewmodels.activitys.DetailUserVM;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.example.macbookpro.ticketapp.views.customviews.SimpleDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class DetailUserActivity extends BindingActivity<ActivityDetailUserBinding> implements Navigation.NavBarButtonListened, DetailUserVM.DetailUserActivityListened, DetailUserVM.ApiListened {

    private static final String TAG = "DetailUserActivity";
    private static final int PERMISSION_REQUEST_CODE = 2;
    private static final int INPUT_AVATAR_IMAGE_REQUEST_CODE = 3;

    private ActivityDetailUserBinding binding;
    private DetailUserVM viewModel;
    private String filePath = "";
    private String fileName = "";

    private StorageReference storageReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        viewModel = new DetailUserVM(this, this);
        binding.setUser(viewModel.user);
        binding.setNavigation(new Navigation(R.drawable.ic_back_button, "Thông tin cá nhân"));
        binding.navigationBar.setListened(this);
        binding.setListened(this);
        binding.setViewModel(viewModel);

        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_user;
    }

    @Override
    public void onLeftBarButtonTapped(View view) {
        viewModel.user.notifyUserDataChanged();
        super.onBackPressed();
    }

    @Override
    public void onUpdateProfileTapped(View view) {
        viewModel.setFlagIsUpdateState(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onUpdateAvatarTapped(View view) {
        pickImageIntent(INPUT_AVATAR_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onCompleteButtonTapped(View view) {
        if (!viewModel.isFlagIsUploadingImage()) {
            viewModel.completeUpdateProfile();
        } else {
            SimpleDialog.getInstance().show(this, "Bạn cần phải chờ việc upload ảnh thành công!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==  RESULT_OK) {
            if (requestCode == INPUT_AVATAR_IMAGE_REQUEST_CODE) {
                pickAvatarProcess(data);
            }
        }
    }

    private void pickAvatarProcess(Intent data) {
        if (data.getData() != null) {
            Uri uri = data.getData();
            fileName = Ultil.getFileName(uri, getContentResolver());
            viewModel.user.setAvatarUrl(uri.toString());
            viewModel.setFlagIsRequestUploadImage(true);
            viewModel.setFlagIsUploadingImage(true);
            uploadImage(uri);
        }
    }

    private void uploadImage(Uri uri) {
        StorageReference fileToUpload = storageReference.child("UserAvatars").child(fileName);
        fileToUpload.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getAvatarLink("UserAvatars/" + fileName);
                viewModel.setFlagIsUploadingImage(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailUserActivity.this, "Upload image failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAvatarLink(String path) {
        storageReference.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                viewModel.user.setAvatarUrl(uri.toString());
                Log.e("user avatar link: ", viewModel.user.getAvatarUrl());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void pickImageIntent(int requestCode) {
        Intent[] intents;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = Ultil.createImageFile();
                takePictureIntent.putExtra("PhotoPath", filePath);
            } catch (IOException ex) {
                Log.e(TAG, "Create Image Failed", ex);
            }

            if (photoFile != null) {
                filePath = "file:" + photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            } else {
                takePictureIntent = null;
            }
        }
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intents = new Intent[]{takePictureIntent};
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Chọn Ảnh");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            startActivityForResult(chooserIntent, requestCode);
        }
    }

    @Override
    public void onSuccessResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        viewModel.setFlagIsUpdateState(false);
    }

    @Override
    public void onFailedResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
