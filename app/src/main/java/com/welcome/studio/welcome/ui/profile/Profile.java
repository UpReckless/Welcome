package com.welcome.studio.welcome.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseFragment;
import com.welcome.studio.welcome.ui.main.MainActivity;
import com.welcome.studio.welcome.util.SlidingTabLayout;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Royal on 12.01.2017.
 */

public class Profile extends BaseFragment implements ProfileView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.img_main_photo)
    CircularImageView mainPhoto;
    @Bind(R.id.txt_rating)
    TextView txtRating;
    @Bind(R.id.txt_city)
    TextView txtCity;
    @Bind(R.id.txt_likes)
    TextView txtLikes;
    @Bind(R.id.txt_posts)
    TextView txtPosts;
    @Bind(R.id.txt_come)
    TextView txtCome;
    @Bind(R.id.txt_vip)
    TextView txtVip;

    @Inject
    ProfilePresenter presenter;
    @Inject
    Context context;
    @Inject
    ProfileAdapter adapter;

    private ProfileComponent component;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        component=((MainActivity)getActivity()).getComponent().plus(new ProfileModule(this));
        component.inject(this);
        ButterKnife.bind(this, view);
        presenter.onCreate();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager.setAdapter(adapter);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    public ProfileComponent getComponent() {
        return component;
    }

    @Override
    public void setData(String title, double rating, String city, long likes, long posts, long come, long vip) {
        txtRating.setText(String.valueOf(rating));
        txtCity.setText(city);
        txtLikes.setText(String.valueOf(likes));
        txtPosts.setText(String.valueOf(posts));
        txtCome.setText(String.valueOf(come));
        txtVip.setText(String.valueOf(vip));
    }

    @Override
    public void loadMainPhoto(String photoPath) {
        Picasso.Builder builder=new Picasso.Builder(context);
        builder.listener(((picasso, uri, exception) -> Log.e("s",exception.toString())));
        builder.build().load(new File(photoPath)).into(mainPhoto);
    }

    @Override
    public void setToolbar(String title) {
        ((MainActivity)getActivity()).setToolbarToDrawer(toolbar,title);
    }
}
