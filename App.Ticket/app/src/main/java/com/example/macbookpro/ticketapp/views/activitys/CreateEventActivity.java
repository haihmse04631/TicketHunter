package com.example.macbookpro.ticketapp.views.activitys;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityCreateEventBinding;
import com.example.macbookpro.ticketapp.helper.ultility.GridSpacingItemDecoration;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Image;
import com.example.macbookpro.ticketapp.models.Navigation;
import com.example.macbookpro.ticketapp.models.TestApi;
import com.example.macbookpro.ticketapp.viewmodels.activitys.CreateEventVM;
import com.example.macbookpro.ticketapp.views.adapter.ChoosedImageAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateEventActivity extends BindingActivity implements ChoosedImageAdapter.ChoosedImageAdapterListened, CreateEventVM.CreateEventActivityListened,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private String TAG = getClass().getName();
    private int PICK_AVATAR_REQUEST_CODE = 1;
    private int PERMISSION_REQUEST_CODE = 2;
    private int INPUT_FILE_REQUEST_CODE = 3;

    private ActivityCreateEventBinding binding;
    private CreateEventVM viewModel = new CreateEventVM();
    private RecyclerView recyclerView;
    private ChoosedImageAdapter adapter;
    private List<Image> choosedImages;
    private List<Uri> uploadedImageLink;
    private int lastCategoryChoosedIndex = 0;
    private List<TextView> textViews = new ArrayList<>();
    private int count = 0;
    private int numberOfImage = 0;
    private String filePath = "";
    private Image avatarImage;
    private boolean isUploadImageDone = false;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = (ActivityCreateEventBinding) getViewBinding();
        binding.setNavigation(new Navigation(R.drawable.ic_event, "Tạo Sự Kiện"));
        binding.setEvent(viewModel.event);
        binding.setListened(this);
        avatarImage = new Image();
        binding.setAvatarImage(avatarImage);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        choosedImages = new ArrayList<>();
        uploadedImageLink = new ArrayList<>();
        numberOfImage = choosedImages.size();

        initRecycleView();
        getCategoryList();
        configCategoryFlowLayoutTapped();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();

        if (numberOfImage != choosedImages.size()) uploadImage(false);
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
    public void onCloseIconTapped(int index) {
        if (uploadedImageLink.size() == choosedImages.size()) {
            uploadedImageLink.remove(index);
            choosedImages.remove(index);
            count--;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == INPUT_FILE_REQUEST_CODE) {
                inputImageProcess(data);
            } else {
                pickAvatarProcess(data);
            }
        }
    }

    private void inputImageProcess(Intent data) {
        if (data.getClipData() != null) {

            int totalItemsSelected = data.getClipData().getItemCount();

            for (int i = 0; i < totalItemsSelected; i++) {
                Uri uri = data.getClipData().getItemAt(i).getUri();
                String fileName = Ultil.getFileName(uri, getContentResolver());
                Image image = new Image(i, uri, true, fileName);
                choosedImages.add(image);
                adapter.notifyDataSetChanged();
            }

        } else if (data.getData() != null) {
            Uri uri = data.getData();
            String fileName = Ultil.getFileName(uri, getContentResolver());
            Image image = new Image(count, uri, true, fileName);
            choosedImages.add(image);
            adapter.notifyDataSetChanged();
        }
    }

    private void pickAvatarProcess(Intent data) {
        if (data.getData() != null) {
            Uri uri = data.getData();
            String fileName = Ultil.getFileName(uri, getContentResolver());
            avatarImage.setId(0);
            avatarImage.setName(fileName);
            avatarImage.setUri(uri);
            avatarImage.setFlagIsLoading(true);
            uploadImage(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == INPUT_FILE_REQUEST_CODE) {
                pickImageIntent(INPUT_FILE_REQUEST_CODE);
            } else {
                pickImageIntent(PICK_AVATAR_REQUEST_CODE);
            }
        }
    }

    private void uploadImage(boolean isUploadAvatar) {
        if (count < choosedImages.size() && !isUploadAvatar) {
            final Image image = choosedImages.get(count);
            StorageReference fileToUpload = storageReference.child("Libraries").child(image.getName());
            fileToUpload.putFile(image.getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Image imageItem = choosedImages.get(count);
                    imageItem.setFlagIsLoading(false);
                    adapter.notifyDataSetChanged();
                    count++;
                    addImageDownloadLink("Libraries/" + image.getName(), false);
                    uploadImage(false);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    toastMessage("Upload Failed!");
                }
            });
        } else if (isUploadAvatar) {
            StorageReference fileToUpload = storageReference.child("Avatars").child(avatarImage.getName());
            fileToUpload.putFile(avatarImage.getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    addImageDownloadLink("Avatars/" + avatarImage.getName(), true);
                    avatarImage.setFlagIsLoading(false);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    toastMessage("Upload Failed!");
                }
            });
        }

    }

    private void addImageDownloadLink(String path, boolean isAvatarUri) {
        if (!isAvatarUri) {
            storageReference.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    uploadedImageLink.add(uri);
                }
            });
        } else {
            storageReference.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    viewModel.event.setImageUrl(uri.toString());
                }
            });
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void getCategoryList() {
        for (int index = 0; index < binding.categoryFlowLayout.getChildCount(); index++) {
            final View childView = binding.categoryFlowLayout.getChildAt(index);
            if (childView instanceof TextView) {
                textViews.add((TextView) childView);
                if (index == 0) {
                    childView.setBackground(getResources().getDrawable(R.drawable.custom_textview_category_checked));
                    ((TextView) childView).setTextColor(getResources().getColor(R.color.whiteColor));
                } else {
                    childView.setBackground(getResources().getDrawable(R.drawable.custom_textview_category_uncheck));
                    ((TextView) childView).setTextColor(getResources().getColor(R.color.normalText));
                }
            }
        }
    }

    private void configCategoryFlowLayoutTapped() {
        for (int index = 0; index < textViews.size(); index++) {
            final TextView childView = textViews.get(index);
            final int finalIndex = index;
            childView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    viewModel.setCategoryOfEventAt(finalIndex);
                    childView.setBackground(getResources().getDrawable(R.drawable.custom_textview_category_checked));
                    ((TextView) childView).setTextColor(getResources().getColor(R.color.whiteColor));
                    TextView lastChoosedTextView = textViews.get(lastCategoryChoosedIndex);
                    lastChoosedTextView.setBackground(getResources().getDrawable(R.drawable.custom_textview_category_uncheck));
                    lastChoosedTextView.setTextColor(getResources().getColor(R.color.normalText));
                    lastCategoryChoosedIndex = finalIndex;
                }
            });
        }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSelectImageTapped(View view) {
        pickImageIntent(INPUT_FILE_REQUEST_CODE);
    }

    @Override
    public void onDateTapped(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, CreateEventActivity.this, 2019, 10, 10);
        datePickerDialog.show();
    }

    @Override
    public void onTimeTapped(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,CreateEventActivity.this, 10, 00, true);
        timePickerDialog.show();
    }

    @Override
    public void onCheckboxTapped(View view) {
        viewModel.event.setFlagIsCheckboxContactChecked(!viewModel.event.isFlagIsCheckboxContactChecked());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAvatarImageTapped(View view) {
        pickImageIntent(PICK_AVATAR_REQUEST_CODE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        viewModel.event.setDate(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        viewModel.event.setTime(time);
    }
}

