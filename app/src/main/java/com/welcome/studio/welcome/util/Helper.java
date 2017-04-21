package com.welcome.studio.welcome.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.FileUriExposedException;
import android.view.Display;
import android.view.WindowManager;

import com.welcome.studio.welcome.model.data.Rating;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by @mistreckless on 07.12.2016. !
 */

public class Helper {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static String savePhotoToDirectory(Bitmap bitmap, String dir) throws IOException {
        File pictureFile = getOutputMediaFile(dir);
        try (FileOutputStream fos = new FileOutputStream(pictureFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            return pictureFile.getAbsolutePath();
        }
    }

    private static File getOutputMediaFile(String dir) {
        File storeDir = new File(dir);
        if (!storeDir.exists())
            if (!storeDir.mkdirs())
                throw new FileUriExposedException("Error creating file, check storage permission");
        String time = new SimpleDateFormat("ddMMyyyy_HHmm", Locale.getDefault()).format(new Date());
        String photoName = "W_" + time + ".jpg";
        return new File(storeDir.getPath() + File.separator + photoName);
    }

    public static double countRating(Rating rating) { //mock
        return rating.getLikeCount() + rating.getPostCount() + rating.getWillcomeCount() + rating.getVippostCount();
    }


    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static boolean isAndroidLOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
