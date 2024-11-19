package com.example.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;

import java.io.IOException;

public class ImageHelper {
    private Context context;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri imageUri;

    public ImageHelper(Context context, ImageView imageView, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.imageView = imageView;
        this.activityResultLauncher = activityResultLauncher;
    }

    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Selecciona una imagen"));
    }

    public String handleActivityResult(int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);

                return getFileName(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getImageFileName() {
        if (imageUri != null) {
            return getFileName(imageUri);
        }

        return null;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex);
                    }
                }
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
}