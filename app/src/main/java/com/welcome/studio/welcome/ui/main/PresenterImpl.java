package com.welcome.studio.welcome.ui.main;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelFirebase;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.pojo.AuthRequest;
import com.welcome.studio.welcome.ui.BaseFragment;
import com.welcome.studio.welcome.ui.history.History;
import com.welcome.studio.welcome.ui.profile.Profile;
import com.welcome.studio.welcome.ui.rating.Rating;
import com.welcome.studio.welcome.ui.registry.SignUp;
import com.welcome.studio.welcome.ui.search.Search;
import com.welcome.studio.welcome.ui.settings.Settings;
import com.welcome.studio.welcome.ui.vip.Vip;
import com.welcome.studio.welcome.ui.wall.Wall;
import com.welcome.studio.welcome.util.Helper;

import java.io.IOException;

import javax.inject.Inject;

import static com.welcome.studio.welcome.util.Constance.AppDirectoryHolder.MAIN_PHOTO_DIR_PATH;
import static com.welcome.studio.welcome.util.Constance.FragmentTagHolder.*;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.*;

public class PresenterImpl implements Presenter {
    private static final String TAG = "ui.main.PresenterImpl";
    private View view;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    ModelServer modelServer;
    @Inject
    ModelFirebase modelFirebase;

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (from.equals(Picasso.LoadedFrom.NETWORK)){
                view.updateProfile(buildProfile(bitmap));
                new Thread(()->{
                    try {
                        sharedPreferences.edit().putString(PHOTO_PATH, Helper.savePhotoToDirectory(bitmap,MAIN_PHOTO_DIR_PATH)).apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }else view.setDrawer(buildProfile(bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Inject
    public PresenterImpl(View view) {
        this.view = view;
        view.getComponent().inject(this);
    }

    @Override
    public void onCreate(boolean isAuth) {
        if (sharedPreferences.contains(NAME) && sharedPreferences.contains(IMEI)) {
            if (!isAuth)
                modelServer.authUser(new AuthRequest(Double.longBitsToDouble(sharedPreferences.getLong(LAT, 0)),
                        Double.longBitsToDouble(sharedPreferences.getLong(LON, 0)), sharedPreferences.getString(IMEI, null)))
                        .subscribe(authResponse -> {
                                    modelFirebase.auth(authResponse.getToken())
                                            .addOnCompleteListener(task -> start())
                                            .addOnFailureListener(fail -> Log.e(TAG, fail.getMessage()));
                                    sharedPreferences.edit().putString(CITY, authResponse.getCity()).apply();
                                }
                                , failure -> Log.e(TAG, failure.getMessage()));
            else
                start();

        } else
            view.getCurrentFragmentManager().beginTransaction().add(R.id.container, new SignUp()).commitAllowingStateLoss();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean onDrawerItemCLick(android.view.View view, int position, IDrawerItem drawerItem) {
        Log.e(TAG,position+"");
        switch (position){
            case 1: {
                replaceFragment(new Wall(), WALL);
                break;
            }
            case 2:{
                replaceFragment(new History(),HISTORY);
                break;
            }
            case 3:{
                replaceFragment(new Vip(),VIP);
                break;
            }
            case 4:{
                replaceFragment(new Rating(),RATING);
                break;
            }
            case 5:{
                replaceFragment(new Search(),SEARCH);
                break;
            }
            case 7:{
                replaceFragment(new Settings(),SETTINGS);
                break;
            }
        }
        return true;
    }

    private void replaceFragment(BaseFragment fragment,String tag) {
        view.getCurrentFragmentManager().beginTransaction().replace(R.id.container,fragment,tag).commit();
    }

    @Override
    public boolean onHeaderClick(android.view.View view) {
        this.view.getCurrentFragmentManager().beginTransaction().replace(R.id.container,new Profile(),PROFILE).commit();
        return true;
    }

    private void start() {
        String photoPath = sharedPreferences.getString(PHOTO_PATH, null);
        if (photoPath != null)
            view.loadProfileImage((picasso, uri, exception) -> {
                view.loadProfileImage(target,R.mipmap.img_avatar);
                String photoRef = sharedPreferences.getString(PHOTO_REF, null);
                if (photoRef != null)
                    modelFirebase.downloadURl(photoRef)
                            .addOnSuccessListener(uri1 -> view.loadProfileImage(target, uri1))
                            .addOnFailureListener(fail -> view.loadProfileImage(target, R.mipmap.img_avatar));
            }, target, photoPath);
        view.getCurrentFragmentManager().beginTransaction().add(R.id.container,new Wall(),WALL).commitAllowingStateLoss();
    }

    private IProfile buildProfile(Bitmap bitmap) {
        return new ProfileDrawerItem()
                .withIcon(bitmap)
                .withIdentifier(1)
                .withName(sharedPreferences.getString(NAME, null))
                .withEmail(sharedPreferences.getString(CITY, null));
    }
}
