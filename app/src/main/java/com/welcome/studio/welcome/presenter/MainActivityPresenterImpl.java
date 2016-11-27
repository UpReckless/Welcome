package com.welcome.studio.welcome.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.activity.MainActivity;
import com.welcome.studio.welcome.view.fragment.BaseFragment;
import com.welcome.studio.welcome.view.fragment.HomeFragment;
import com.welcome.studio.welcome.view.fragment.PhotoFragment;
import com.welcome.studio.welcome.view.fragment.ProfileFragment;


public class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivity view;
    private boolean handledBack = true;
    private boolean lastFragmentIsHome;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;

    public MainActivityPresenterImpl(MainActivity view) {
        this.view = view;
    }

    @Override
    public void onCreate(SharedPreferences spf, boolean isAuth, Bundle savedInstanceState) { //Commented for view develop
        ModelServer modelServer = new ModelServerImpl();
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
    public void start() {
        homeFragment = (HomeFragment) this.view.getCurrentFragmentManager().findFragmentById(R.id.home_fragment);
        profileFragment = (ProfileFragment) this.view.getCurrentFragmentManager().findFragmentById(R.id.profile_fragment);
        view.getCurrentFragmentManager().beginTransaction()
                .hide(profileFragment)
                .show(homeFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onCentreButtonClick() {
        view.setNavigationMenuVisibility(false);
        lastFragmentIsHome = homeFragment.isVisible();
        view.getCurrentFragmentManager().beginTransaction().hide(lastFragmentIsHome ? homeFragment : profileFragment).commit();
        replaceFragmentWithAddingToBackStack(new PhotoFragment(), Constance.FragmentTagHolder.PHOTO_MAIN_TAG);
    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        switch (itemIndex) {
            case 0: {
                replaceFragmentVisibility(profileFragment, homeFragment);
                break;
            }
            case 1: {
                replaceFragmentVisibility(homeFragment, profileFragment);
                break;
            }
        }
        handledBack = false;
    }

    @Override
    public void onItemReselected(int itemIndex, String itemName) {
        Log.e("onItem", "resel");
    }

    @Override
    public void onBackPressed() {
        if (view.getCurrentFragmentManager().getBackStackEntryCount() > 0 || handledBack) {
            view.customBackPressed();
            view.setNavigationMenuVisibility(true);
            view.setOnClickListener(true);
            replaceFragmentVisibility(lastFragmentIsHome ? profileFragment : homeFragment, lastFragmentIsHome ? homeFragment : profileFragment);
            return;
        }
        if (homeFragment.isHidden()) {
            replaceFragmentVisibility(profileFragment, homeFragment);
            view.changeCurrentItem(0);
            handledBack = true;
        } else {
            replaceFragmentVisibility(homeFragment, profileFragment);
            view.changeCurrentItem(1);
            handledBack = true;
        }
    }

    private void replaceFragmentVisibility(BaseFragment hideFragment, BaseFragment showFragment) {
        view.getCurrentFragmentManager().beginTransaction().hide(hideFragment).commit();
        view.getCurrentFragmentManager().beginTransaction().show(showFragment).commit();
    }


    private void replaceFragmentWithAddingToBackStack(BaseFragment fragment, String fragmentName) {
        view.getCurrentFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, fragmentName)
                .addToBackStack(null)
                .commit();
    }
}
