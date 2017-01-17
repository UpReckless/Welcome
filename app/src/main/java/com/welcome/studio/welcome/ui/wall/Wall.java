package com.welcome.studio.welcome.ui.wall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseFragment;
import com.welcome.studio.welcome.ui.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Royal on 12.01.2017.
 */

public class Wall extends BaseFragment implements WallView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        ((MainActivity)getActivity()).setToolbarToDrawer(toolbar,R.string.app_name);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_wall;
    }
}
