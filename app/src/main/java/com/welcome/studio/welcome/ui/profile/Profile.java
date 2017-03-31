package com.welcome.studio.welcome.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.Rating;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.Helper;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.util.SlidingTabLayout;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Royal on 12.01.2017. !
 */
@Layout(id=R.layout.fragment_profile)
public class Profile extends BaseMainFragment implements ProfileView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout tabLayout;
    @Bind(R.id.img_toolbar_header)
    ImageView imgToolbarBackground;
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

    private User user;
    private ProfileAdapter adapter;

    public static Profile newInstance(User user){
        Profile profile=new Profile();
        Bundle args=new Bundle();
        args.putSerializable("user",user);
        profile.setArguments(args);
        return profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user=(User) getArguments().get("user");
        adapter = new ProfileAdapter(getChildFragmentManager(),
                getResources().getString(R.string.now), getResources().getString(R.string.history));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager.setAdapter(adapter);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter=null;
        user=null;
        presenter.destroy();
    }

    @Override
    protected Object getRouter() {
        return getActivity();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getFragmentTag() {
        return Constance.FragmentTagHolder.PROFILE;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new ProfileModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return user.getNickname();
    }

    @Override
    public void setData(String city, Rating rating) {
        txtCity.setText(city);
        updateData(rating);
    }

    @Override
    public void loadMainPhoto(File photoFile) {
        Picasso.with(getContext()).load(R.drawable.toolbar_background).into(imgToolbarBackground);
        Picasso.with(getContext()).load(photoFile).error(R.mipmap.img_avatar).into(mainPhoto);

    }

    @Override
    public void updateData(Rating rating) {
        txtRating.setText(String.valueOf(Helper.countRating(rating)));
        txtLikes.setText(String.valueOf(rating.getLikeCount()));
        txtPosts.setText(String.valueOf(rating.getPostCount()));
        txtCome.setText(String.valueOf(rating.getWillcomeCount()));
        txtVip.setText(String.valueOf(rating.getVippostCount()));
    }

}
