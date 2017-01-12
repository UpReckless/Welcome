package com.welcome.studio.welcome.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.App;
import com.welcome.studio.welcome.util.CircleTransform;
import com.welcome.studio.welcome.util.Constance;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View, AccountHeader.OnAccountHeaderProfileImageListener {
    private MainComponent mainComponent;
    @Inject
    Presenter presenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Drawer drawer;
    private AccountHeader accountHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainComponent = App.getComponent().plus(new MainModule(this));
        mainComponent.inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        presenter.onCreate(getIntent().getBooleanExtra(Constance.IntentKeyHolder.KEY_IS_AUTH, false));
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }

    @Override
    public void setDrawer(IProfile profile) {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withOnAccountHeaderProfileImageListener(this)
                .withHeaderBackground(R.drawable.drawer_header)
                .withSelectionListEnabled(false)
                .addProfiles(profile)
                .build();
        accountHeader.getHeaderBackgroundView().setOnClickListener((v -> {
            drawer.deselect();
            drawer.closeDrawer();
            presenter.onHeaderClick(v);
        }));
        initDrawer();
    }

    @Override
    public FragmentManager getCurrentFragmentManager() {
        return getSupportFragmentManager();
    }


    @Override
    public void loadProfileImage(Picasso.Listener listener, Target target, String photoPath) {
        new Picasso.Builder(this).listener(listener).build()
                .load(new File(photoPath))
                .transform(new CircleTransform())
                .into(target);
    }

    @Override
    public void loadProfileImage(Target target, Uri uri) {
        Picasso.with(this).load(uri).memoryPolicy(MemoryPolicy.NO_STORE).transform(new CircleTransform()).into(target);
    }

    @Override
    public void loadProfileImage(Target target, @DrawableRes int res) {
        Picasso.with(this).load(res).transform(new CircleTransform()).into(target);
    }

    @Override
    public void updateProfile(IProfile profile) {
        accountHeader.updateProfile(profile);

    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen())
            drawer.closeDrawer();
        else
            super.onBackPressed();
    }

    private void initDrawer() {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    drawer.closeDrawer();
                    return presenter.onDrawerItemCLick(view, position, drawerItem);
                })
                .addDrawerItems(new PrimaryDrawerItem()
                                .withName(R.string.wall)
                                .withIcon(R.mipmap.ic_home_black_24dp)
                                .withIdentifier(1),
                        new PrimaryDrawerItem()
                                .withName(R.string.history)
                                .withIcon(R.mipmap.ic_motorcycle_black_24dp)
                                .withIdentifier(2),
                        new PrimaryDrawerItem()
                                .withName(R.string.vip)
                                .withIcon(R.mipmap.ic_stars_black_24dp)
                                .withIdentifier(3),
                        new PrimaryDrawerItem()
                                .withName(R.string.rating)
                                .withIcon(R.mipmap.ic_timeline_black_24dp)
                                .withIdentifier(4),
                        new PrimaryDrawerItem()
                                .withName(R.string.search)
                                .withIcon(R.mipmap.ic_search_black_24dp)
                                .withIdentifier(5),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem()
                                .withName(R.string.settings)
                                .withIcon(R.mipmap.ic_build_black_24dp)
                                .withIdentifier(6))
                .build();
    }

    @Override
    public boolean onProfileImageClick(android.view.View view, IProfile profile, boolean current) {
        drawer.deselect();
        drawer.closeDrawer();
        return presenter.onHeaderClick(view);
    }

    @Override
    public boolean onProfileImageLongClick(android.view.View view, IProfile profile, boolean current) {
        drawer.deselect();
        drawer.closeDrawer();
        return presenter.onHeaderClick(view);
    }
}
