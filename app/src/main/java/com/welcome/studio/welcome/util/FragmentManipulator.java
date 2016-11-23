package com.welcome.studio.welcome.util;

import com.welcome.studio.welcome.view.fragment.BaseFragment;
import com.welcome.studio.welcome.view.fragment.HomeFragment;
import com.welcome.studio.welcome.view.fragment.PhotoFragment;
import com.welcome.studio.welcome.view.fragment.ProfileFragment;

/**
 * Created by Royal on 23.11.2016.
 */

public class FragmentManipulator {
    public static FragmentManipulator instance;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;

    private FragmentManipulator() {
    }

    public static FragmentManipulator getInstance() {
        if (instance == null) {
            instance = new FragmentManipulator();
        }
        return instance;
    }
    public BaseFragment getFragment(String tag){
        switch (tag){
            case Constance.FragmentTagHolder.HOME_MAIN_TAG:{
                //if (homeFragment==null)
                    homeFragment=new HomeFragment();
                return homeFragment;
            }
            case Constance.FragmentTagHolder.PROFILE_MAIN_TAG:{
              //  if (profileFragment==null)
                    profileFragment=new ProfileFragment();
                return profileFragment;
            }
            case Constance.FragmentTagHolder.PHOTO_MAIN_TAG:{
                return new PhotoFragment();
            }
        }
        throw new IllegalArgumentException("Illegal fragment name "+tag+" (FragmentManipulator)");
    }
}
