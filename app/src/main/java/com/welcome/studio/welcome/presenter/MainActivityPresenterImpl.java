package com.welcome.studio.welcome.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.model.entity.DaoSession;
import com.welcome.studio.welcome.model.entity.RaitingDao;
import com.welcome.studio.welcome.model.entity.UserDao;
import com.welcome.studio.welcome.util.App;
import com.welcome.studio.welcome.util.AuthService;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.activity.MainActivity;
import com.welcome.studio.welcome.view.fragment.BaseFragment;
import com.welcome.studio.welcome.view.fragment.HomeFragment;
import com.welcome.studio.welcome.view.fragment.PhotoFragment;
import com.welcome.studio.welcome.view.fragment.ProfileFragment;

/**
 * Created by Royal on 28.10.2016.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter, FragmentManager.OnBackStackChangedListener {

    private MainActivity view;
    private ModelServer modelServer;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private PhotoFragment photoFragment;
    private FragmentManager fm;
    private boolean handled = false;

    public MainActivityPresenterImpl(MainActivity view) {
        this.view = view;
        modelServer = new ModelServerImpl();
    }

    @Override
    public void onCreate(SharedPreferences spf, boolean isAuth, Bundle savedInstanceState) { //Commented for view develop
        if (spf.contains(Constance.SharedPreferencesHolder.NAME) && spf.contains(Constance.SharedPreferencesHolder.IMEI)) {
            if (!isAuth) {
//                String imei = spf.getString(Constance.SharedPreferencesHolder.IMEI, null);
//                modelServer.authUser(imei).subscribe(token -> {
//                    AuthService.auth(token)
//                            .addOnCompleteListener(task -> Log.e("MainPres onComplete",task.toString()))
//                            .addOnFailureListener(failure -> Log.e("Fail to auth", failure.getMessage()));
//                }, failure -> {
//                    Log.e("MainActivityPres", failure.getMessage());
//                });
                view.start(savedInstanceState);
            } else {
                view.start(savedInstanceState);
            }
        } else view.setFirstStart();
    }

    @Override
    public void onBackPressed() {
        int count = fm.getBackStackEntryCount();
        if (count == 0) {
            view.closeApp();
            return;
        }
        String fragmentName = fm.getBackStackEntryAt(count - 1).getName();
        switch (fragmentName) {
            case Constance.FragmentTagHolder.HOME_MAIN_TAG: {
                if (count == 2) {
                    handled = true;
                    view.changeCurrentItem(1);
                }
                break;
            }
            case Constance.FragmentTagHolder.PROFILE_MAIN_TAG: {
                if (count == 1) {
                    if (handled) view.closeApp();
                    else {
                        handled = true;
                        view.changeCurrentItem(0);
                    }
                }
                break;
            }
            case Constance.FragmentTagHolder.PHOTO_MAIN_TAG: {
                view.setNavigationMenuVisibility(true);
                handled = true;
                if (count == 1) view.changeCurrentItem(0);
                else view.changeCurrentItem(fm.getBackStackEntryAt(count - 2).getName()
                        .equals(Constance.FragmentTagHolder.HOME_MAIN_TAG) ? 0 : 1);
                break;
            }
        }
    }

    @Override
    public void start(FragmentManager fm) {
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        photoFragment = new PhotoFragment();
        this.fm = fm;
        this.fm.addOnBackStackChangedListener(this);
        this.fm.beginTransaction()
                .add(R.id.container, homeFragment, Constance.FragmentTagHolder.HOME_MAIN_TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onCentreButtonClick() {
        view.setNavigationMenuVisibility(false);
        replaceFragmentWithAddingToBackStack(photoFragment, Constance.FragmentTagHolder.PHOTO_MAIN_TAG);
    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        int count = fm.getBackStackEntryCount();
        if (count == 0 && itemIndex == 1) {
            replaceFragmentWithAddingToBackStack(profileFragment, itemName);
            handled = false;
        }
        if (count == 1 && itemIndex == 0) {
            if (handled) fm.popBackStack();
            else replaceFragmentWithAddingToBackStack(homeFragment, itemName);
        }
        if (count > 1) fm.popBackStack();

    }

    @Override
    public void onItemReselected(int itemIndex, String itemName) {
        if (handled) {
            handled = false;
            fm.popBackStack();
        }
    }

    @Override
    public void onBackStackChanged() { // for debug mode
        Log.e("BACKSTACK P count is ", fm.getBackStackEntryCount() + "");
        for (int i = fm.getBackStackEntryCount(); i > 0; i--) {
            Log.e("Backstack P entry " + i, fm.getBackStackEntryAt(i - 1).getName());
        }
    }

    private void replaceFragmentWithAddingToBackStack(BaseFragment fragment, String fragmentName) {
        fm.beginTransaction()
                .replace(R.id.container, fragment, fragmentName)
                .addToBackStack(fragmentName)
                .commit();
    }
}
