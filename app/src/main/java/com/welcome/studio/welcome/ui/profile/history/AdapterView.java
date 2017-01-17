package com.welcome.studio.welcome.ui.profile.history;

import android.graphics.Bitmap;
import android.net.Uri;

import com.squareup.picasso.Picasso;

/**
 * Created by Royal on 17.01.2017.
 */

public interface AdapterView {
    void loadImage(Picasso.Listener listener, String path);
    void loadImage(Uri uri);
    void setImage(Bitmap bitmap);
    void refresh();
}
