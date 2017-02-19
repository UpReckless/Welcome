package com.welcome.studio.welcome.ui.photo.filter_screen;

import android.content.Context;
import android.graphics.Bitmap;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.util.CircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Royal on 11.02.2017. !
 */

public final class ThumbnailsManager {
    private static List<ThumbnailItem> filterThumbs = new ArrayList<>(10);
    private static List<ThumbnailItem> processedThumbs = new ArrayList<>(10);

    private ThumbnailsManager() {
    }

    public static void addThumb(ThumbnailItem thumbnailItem) {
        filterThumbs.add(thumbnailItem);
    }

    public static List<ThumbnailItem> processThumbs(Context context) {
        for (ThumbnailItem thumb : filterThumbs) {
            // scaling down the image
            float size = context.getResources().getDimension(R.dimen.thumbnail_size);
            thumb.image = Bitmap.createScaledBitmap(thumb.image, (int) size, (int) size, false);
            thumb.image = thumb.filter.processFilter(thumb.image);
            //cropping circle
            thumb.image = new CircleTransform().transform(thumb.image);
            processedThumbs.add(thumb);
        }
        return processedThumbs;
    }

    public static void clearThumbs() {
        filterThumbs = new ArrayList<>();
        processedThumbs = new ArrayList<>();
    }
}
