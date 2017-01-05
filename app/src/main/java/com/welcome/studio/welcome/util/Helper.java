package com.welcome.studio.welcome.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

/**
 * Created by Royal on 07.12.2016.
 */

public class Helper {
    public static String getPhotoPathFromGallery(Intent data, Context context) throws IOException{
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null)){
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                return cursor.getString(columnIndex);
            }
            throw new IOException();
        }
    }
}
