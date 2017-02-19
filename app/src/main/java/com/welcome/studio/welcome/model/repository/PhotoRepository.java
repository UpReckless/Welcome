package com.welcome.studio.welcome.model.repository;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;

import com.upreckless.support.portraitcamerasupport.CameraSupport;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;

import java.io.IOException;

/**
 * Created by Royal on 11.02.2017. !
 */
public interface PhotoRepository {
    String saveTmpPhoto(CameraSupport cameraSupport, byte[] bytes);

    Post saveFinishedPicture(Bitmap bitmap, Address address, Location location, User user) throws IOException;

    Post getSharedPost(String description, String[] tags, boolean dressCode,long deleteTime);
}
