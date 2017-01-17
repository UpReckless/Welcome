package com.welcome.studio.welcome.ui.profile;

import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.pojo.Rating;
import com.welcome.studio.welcome.util.Helper;

import java.io.IOException;

import javax.inject.Inject;

import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.NAME;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PHOTO_PATH;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.RATING;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.TOWN;

/**
 * Created by Royal on 15.01.2017.
 */

public class ProfilePresenterImpl implements ProfilePresenter {
    private ProfileView view;
    private static final String TAG="ProfilePresenter";
    @Inject
    SharedPreferences preferences;
    @Inject
    ModelServer modelServer;

    @Inject
    ProfilePresenterImpl(ProfileView view){
        this.view=view;
        view.getComponent().inject(this);
    }

    @Override
    public void onStart() {
        view.loadMainPhoto(preferences.getString(PHOTO_PATH,null));
        try {
            Rating rating = new ObjectMapper().readValue(preferences.getString(RATING, ""), Rating.class);
            modelServer.getRating(rating.getId())
                    .subscribe(newRating -> {
                                view.setData(preferences.getString(NAME, "Welcome"), Helper.countRating(newRating),
                                        preferences.getString(TOWN, "welcome"), newRating.getLikeCount(), newRating.getPostCount()
                                        , newRating.getWillcomeCount(), newRating.getVippostCount());
                                new Thread(()->saveRating(newRating)).start();
                            }
                            , fail -> Log.e(TAG, fail.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"cannot parse json to rating");
        }
    }

    @Override
    public void onCreate() {
        view.setToolbar(preferences.getString(NAME,"Welcome"));
    }

    private void saveRating(Rating newRating) {
        try {
            String raiting=new ObjectMapper().writeValueAsString(newRating);
            preferences.edit().putString(RATING,raiting).apply();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
