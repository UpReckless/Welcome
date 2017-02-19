package com.welcome.studio.welcome.model.repository;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Location;
import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upreckless.support.portraitcamerasupport.CameraSupport;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.Helper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.POST;

/**
 * Created by Royal on 11.02.2017. !
 */

public class PhotoRepositoryImpl implements PhotoRepository {
    private SharedPreferences spf;

    @Inject
    public PhotoRepositoryImpl(SharedPreferences spf) {
        this.spf = spf;
    }

    @Override
    public String saveTmpPhoto(CameraSupport cameraSupport, byte[] bytes) {
        File savedFile = cameraSupport.savePicture(bytes, "WEL_" + String.valueOf(System.currentTimeMillis()) + ".jpg",
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
        return savedFile.getAbsolutePath();
    }

    @Override
    public Post saveFinishedPicture(Bitmap bitmap, Address address, Location location, User user) throws IOException {
        String savedPhoto = Helper.savePhotoToDirectory(bitmap, Constance.AppDirectoryHolder.HISTORY_PHOTO_DIR_PATH);
        Post post = new Post();
        post.setOffset(getOffset());
        post.setContentPath(savedPhoto);
        post.setContentType(Constance.ContentType.PHOTO);
        post.setCity(address.getLocality());
        post.setCountry(address.getCountryName());
        post.setPostType(Constance.PostType.NORMAL);
        post.setTime(System.currentTimeMillis());
        post.setAddress(address.getThoroughfare());
        post.setLat(location.getLatitude());
        post.setLon(location.getLongitude());
        post.setUserId(user.getId());
        post.setUserName(user.getNickname());
        post.setUserRating(user.getRating());
        String cachePost = new ObjectMapper().writeValueAsString(post);
        spf.edit().putString(POST, cachePost).apply();
        return post;
    }

    @Override
    public Post getSharedPost(String description, String[] tags, boolean dressCode, long deleteTime) {
        String spPost = spf.getString(POST, null);
        assert spPost != null;
        try {
            Post post = new ObjectMapper().readValue(spPost, Post.class);
            post.setDescription(description);
            post.setDressCode(dressCode);
            post.setTags(Arrays.asList(tags));
            post.setDeleteTime(deleteTime);
            //need to cache maybe
            return post;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double getOffset() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        int defHour = calendar.get(Calendar.HOUR_OF_DAY);
        int defMinute = calendar.get(Calendar.MINUTE) + (defHour * 60);
        Date date = new Date(System.currentTimeMillis());
        int curHour = date.getHours();
        int curMinute = date.getMinutes() + (curHour * 60);
        double offset = ((double) curMinute - defMinute) / 60;
        return offset > 12? -24 + offset : offset;
    }

}
