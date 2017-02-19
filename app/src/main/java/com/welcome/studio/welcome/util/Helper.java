package com.welcome.studio.welcome.util;

import android.graphics.Bitmap;
import android.os.FileUriExposedException;

import com.welcome.studio.welcome.model.data.Rating;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Royal on 07.12.2016.
 */

public class Helper {
    public static String savePhotoToDirectory(Bitmap bitmap, String dir) throws IOException {
        File pictureFile= getOutputMediaFile(dir);
        try (FileOutputStream fos=new FileOutputStream(pictureFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            return pictureFile.getAbsolutePath();
        }
    }

    private static File getOutputMediaFile(String dir) {
        File storeDir=new File(dir);
        if (!storeDir.exists())
            if (!storeDir.mkdirs())
                throw new FileUriExposedException("Error creating file, check storage permission");
        String time=new SimpleDateFormat("ddMMyyyy_HHmm", Locale.getDefault()).format(new Date());
        String photoName="W_"+time+".jpg";
        return new File(storeDir.getPath()+File.separator+photoName);
    }

    public static double countRating(Rating rating){ //mock
        return rating.getLikeCount()+rating.getPostCount()+rating.getWillcomeCount()+rating.getVippostCount();
    }
}
