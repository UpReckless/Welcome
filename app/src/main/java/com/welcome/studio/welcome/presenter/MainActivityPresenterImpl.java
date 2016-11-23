package com.welcome.studio.welcome.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelServer;
import com.welcome.studio.welcome.model.ModelServerImpl;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.FragmentManipulator;
import com.welcome.studio.welcome.view.activity.MainActivity;
import com.welcome.studio.welcome.view.fragment.BaseFragment;
import com.welcome.studio.welcome.view.fragment.HomeFragment;
import com.welcome.studio.welcome.view.fragment.PhotoFragment;


public class MainActivityPresenterImpl implements MainActivityPresenter, FragmentManager.OnBackStackChangedListener {

    private MainActivity view;
    private FragmentManager fm;
    private boolean handled = false;
    private FragmentManipulator fragmentManipulator;

    public MainActivityPresenterImpl(MainActivity view) {
        this.view = view;
        fragmentManipulator = FragmentManipulator.getInstance();
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
    public void onBackPressed() {
        int count = fm.getBackStackEntryCount();
        if (count == 0 || handled) {
            view.closeApp();
            return;
        }
        String fragmentName = fm.getBackStackEntryAt(count - 1).getName();
        fm.popBackStack();
        if (fragmentName.equals(Constance.FragmentTagHolder.PHOTO_MAIN_TAG)) {
            view.changeCurrentItem(fragmentName.equals(Constance.FragmentTagHolder.HOME_MAIN_TAG) ? 0 : 1);
            view.setNavigationMenuVisibility(true);
        } else {
            view.changeCurrentItem(fragmentName.equals(Constance.FragmentTagHolder.HOME_MAIN_TAG) ? 1 : 0);
            handled = true;
        }
    }

    @Override
    public void start(FragmentManager fm) {
        this.fm = fm;
        this.fm.addOnBackStackChangedListener(this);
        this.fm.beginTransaction()
                .add(R.id.container, new HomeFragment(), Constance.FragmentTagHolder.HOME_MAIN_TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onCentreButtonClick() {
        view.setNavigationMenuVisibility(false);
        replaceFragmentWithAddingToBackStack(new PhotoFragment(), Constance.FragmentTagHolder.PHOTO_MAIN_TAG);
    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        int count = fm.getBackStackEntryCount();
        handled = false;
        if (count < 2)
            replaceFragmentWithAddingToBackStack(fragmentManipulator.getFragment(itemName), itemName);
        else fm.popBackStack();

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
