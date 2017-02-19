package com.welcome.studio.welcome.ui.registry;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.welcome.studio.welcome.ui.registry.choose_screen.ChooseFragment;
import com.welcome.studio.welcome.ui.registry.singup.first_screen.SignUp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Royal on 07.02.2017.
 */

public class RegistryFragmentAdapter extends FragmentStatePagerAdapter {
    private ChooseFragment chooseFragment;
    private List<Fragment> fragmentList= new ArrayList<>();

    public RegistryFragmentAdapter(FragmentManager fm) {
        super(fm);
        chooseFragment=new ChooseFragment();
        fragmentList.add(SignUp.newInstance());
        fragmentList.add(chooseFragment);
        fragmentList.add(SignUp.newInstance());
    }

    public void clearChoose(int index){
        fragmentList.remove(chooseFragment);
        fragmentList.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
