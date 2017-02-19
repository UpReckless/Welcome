package com.welcome.studio.welcome.model.interactor;

import android.graphics.Bitmap;

import com.upreckless.support.portraitcamerasupport.CameraSupport;
import com.welcome.studio.welcome.model.data.Post;

import java.io.IOException;

import rx.Observable;

/**
 * Created by Royal on 11.02.2017. !
 */
public interface PhotoInteractor {
    Observable<String> takeTmpPicture(CameraSupport cameraSupport, byte[] bytes);

    Observable<Post> saveFinishedPicture(Bitmap outputBitmap) throws IOException;

    Observable<Boolean> sharePost(String description, String[] tags, boolean dressCode,long deleteTime);
}
