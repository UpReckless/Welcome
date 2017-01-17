package com.welcome.studio.welcome.ui.main;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelFirebase;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.pojo.AuthRequest;
import com.welcome.studio.welcome.ui.BaseFragment;
import com.welcome.studio.welcome.ui.city.City;
import com.welcome.studio.welcome.ui.profile.Profile;
import com.welcome.studio.welcome.ui.rating.Rating;
import com.welcome.studio.welcome.ui.registry.SignUp;
import com.welcome.studio.welcome.ui.search.Search;
import com.welcome.studio.welcome.ui.settings.Settings;
import com.welcome.studio.welcome.ui.vip.Vip;
import com.welcome.studio.welcome.ui.wall.Wall;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.Helper;
import com.welcome.studio.welcome.util.LocationService;

import java.io.IOException;

import javax.inject.Inject;

import static com.welcome.studio.welcome.util.Constance.AppDirectoryHolder.MAIN_PHOTO_DIR_PATH;
import static com.welcome.studio.welcome.util.Constance.FragmentTagHolder.PROFILE;
import static com.welcome.studio.welcome.util.Constance.FragmentTagHolder.SEARCH;
import static com.welcome.studio.welcome.util.Constance.FragmentTagHolder.SETTINGS;
import static com.welcome.studio.welcome.util.Constance.FragmentTagHolder.VIP;
import static com.welcome.studio.welcome.util.Constance.FragmentTagHolder.WALL;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.IMEI;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.LAT;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.LON;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.NAME;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PHOTO_PATH;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.PHOTO_REF;
import static com.welcome.studio.welcome.util.Constance.SharedPreferencesHolder.TOWN;

public class PresenterImpl implements Presenter {
    private static final String TAG = "ui.main.PresenterImpl";
    private View view;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    ModelServer modelServer;
    @Inject
    ModelFirebase modelFirebase;
    @Inject
    LocationService locationService;

    @Inject
    PresenterImpl(View view) {
        this.view = view;
        view.getComponent().inject(this);
    }

    @Override
    public void onStart(boolean isAuth) {
        if (sharedPreferences.contains(NAME) && sharedPreferences.contains(IMEI)) {
            if (!isAuth) {
                try {
                    Location location = locationService.getLocation();
                    AuthRequest authRequest = new AuthRequest(location.getLatitude(),location.getLongitude(),sharedPreferences.getString(IMEI,null));
                    modelServer.authUser(authRequest)
                            .subscribe(authResponse -> {
                                        modelFirebase.auth(authResponse.getToken())
                                                .addOnCompleteListener(task -> start())
                                                .addOnFailureListener(fail -> Log.e(TAG, fail.getMessage()));
                                        sharedPreferences.edit()
                                                .putString(TOWN, authResponse.getCity())
                                                .putLong(LAT,Double.doubleToRawLongBits(location.getLatitude()))
                                                .putLong(LON,Double.doubleToRawLongBits(location.getLongitude()))
                                                .apply();
                                    }
                                    , failure -> Log.e(TAG, failure.getMessage()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();//network is disabled
                }
            }
            else
                start();

        } else
            view.getCurrentFragmentManager().beginTransaction().add(R.id.container, new SignUp()).commitAllowingStateLoss();
    }

    @Override
    public boolean onDrawerItemCLick(android.view.View view, int position, IDrawerItem drawerItem) {
        Log.e(TAG, position + "");
        switch (position) {
            case 1: {
                replaceFragment(new Wall(), WALL);
                break;
            }
            case 2: {
                replaceFragment(new City(), Constance.FragmentTagHolder.CITY);
                break;
            }
            case 3: {
                replaceFragment(new Vip(), VIP);
                break;
            }
            case 4: {
                replaceFragment(new Rating(), Constance.FragmentTagHolder.RATING);
                break;
            }
            case 5: {
                replaceFragment(new Search(), SEARCH);
                break;
            }
            case 7: {
                replaceFragment(new Settings(), SETTINGS);
                break;
            }
        }
        return true;
    }

    private void replaceFragment(BaseFragment fragment, String tag) {
        view.getCurrentFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
    }

    @Override
    public boolean onHeaderClick(android.view.View view) {
        this.view.getCurrentFragmentManager().beginTransaction().replace(R.id.container, new Profile(), PROFILE).commit();
        return true;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (from.equals(Picasso.LoadedFrom.NETWORK)) {
            view.updateProfile(buildProfile(bitmap));
            new Thread(() -> {
                try {
                    sharedPreferences.edit().putString(PHOTO_PATH, Helper.savePhotoToDirectory(bitmap, MAIN_PHOTO_DIR_PATH)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } else{
            view.setDrawer(buildProfile(bitmap));
            view.getCurrentFragmentManager().beginTransaction().add(R.id.container, new Wall(), WALL).commitAllowingStateLoss();
        }
    }

    private void start() {
        String photoPath = sharedPreferences.getString(PHOTO_PATH, null);
        if (photoPath != null)
            view.loadProfileImage((picasso, uri, exception) -> {
                view.loadProfileImage(R.mipmap.img_avatar);
                String photoRef = sharedPreferences.getString(PHOTO_REF, null);
                if (photoRef != null)
                    modelFirebase.downloadURl(photoRef)
                            .addOnSuccessListener(uri1 -> view.loadProfileImage(uri1))
                            .addOnFailureListener(fail -> view.loadProfileImage(R.mipmap.img_avatar));
            }, photoPath);
        else {
            view.setDrawer(buildProfile(BitmapFactory.decodeResource(view.getResources(),R.mipmap.img_avatar)));
            view.getCurrentFragmentManager().beginTransaction().add(R.id.container,new Wall(),WALL).commitAllowingStateLoss();
        }

    }

    private IProfile buildProfile(Bitmap bitmap) {
        return new ProfileDrawerItem()
                .withIcon(bitmap)
                .withIdentifier(1)
                .withName(sharedPreferences.getString(NAME, null))
                .withEmail(sharedPreferences.getString(TOWN, null));
    }
}
