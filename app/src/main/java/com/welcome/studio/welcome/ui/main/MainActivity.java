package com.welcome.studio.welcome.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.squarecamera_mock.CameraActivity;
import com.welcome.studio.welcome.ui.comment.Comment;
import com.welcome.studio.welcome.ui.profile.Profile;
import com.welcome.studio.welcome.ui.registry.Registry;
import com.welcome.studio.welcome.ui.wall.Wall;
import com.welcome.studio.welcome.util.Constance;

import javax.inject.Inject;

import static com.welcome.studio.welcome.util.Constance.IntentCodeHolder.CAMERA_CODE;

public class MainActivity extends AppCompatActivity implements View, AccountHeader.OnAccountHeaderProfileImageListener, MainRouter {
    @Inject
    Presenter presenter;
    @Inject
    RxBus bus;

    private Drawer drawer;
    private AccountHeader accountHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injector.getInstance().plus(new MainModule(this)).inject(this);
        presenter.setRouter(this);
        presenter.create();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Injector.getInstance().clearMainComponent();
    }

    @Override
    public void setDrawer(User user) {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withOnAccountHeaderProfileImageListener(this)
                .withHeaderBackground(R.drawable.drawer_header)
                .withSelectionListEnabled(false)
                .addProfiles(new ProfileDrawerItem()
                        .withName(user.getNickname())
                        .withIdentifier(1)
                        .withEmail(user.getCity())
                        .withIcon(Uri.parse(user.getPhotoRef())))
                .build();
        accountHeader.getHeaderBackgroundView().setOnClickListener((v -> {
            presenter.onProfileClick();
            drawer.deselect();
            drawer.closeDrawer();

        }));
        initDrawer();
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
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(false)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    drawer.closeDrawer();
                    presenter.onDrawerItemCLick(position);
                    return true;
                })
                .withOnDrawerNavigationListener(clickedView -> {
                    Toast.makeText(this, "Nav", Toast.LENGTH_SHORT).show();
                    return true;
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
        presenter.onProfileClick();
        return true;
    }

    @Override
    public boolean onProfileImageLongClick(android.view.View view, IProfile profile, boolean current) {
        drawer.deselect();
        drawer.closeDrawer();
        presenter.onProfileClick();
        return true;
    }

    @Override
    public void setToolbarToDrawer(Toolbar toolbar, String title, boolean isAddedToBackStack) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (drawer == null) initDrawer();
            if (isAddedToBackStack) {
                toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
                drawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            } else if (drawer != null) {
                toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
                drawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                drawer.setToolbar(this, toolbar);
            }
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setTitle(title);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_CODE: {
                if (resultCode == RESULT_OK && data != null) {
                    Post post = (Post) data.getSerializableExtra(Constance.IntentKeyHolder.POST_KEY);
                    bus.sendUserPost(post);
                }
                break;
            }
        }
    }

    @Override
    public void navigateFromRegistry() {
        presenter.start();
    }

    @Override
    public void navigateToWall() {
        Wall wall = Wall.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, wall, wall.getFragmentTag())
                .commitAllowingStateLoss();
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void navigateToRegistry() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new Registry(), null)
                .commitAllowingStateLoss();
    }

    @Override
    public void navigateToProfile(User user) {
        Profile profile = Profile.newInstance(user);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, profile, profile.getTag())
                .commitAllowingStateLoss();

    }

    @Override
    public void navigateToPhoto() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, CAMERA_CODE);
    }

    @Override
    public void navigateToComment(Post post) {
        Comment comment = Comment.newInstance(post);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, comment, comment.getTag())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateToPostWatcher(Post post) {
//        PostWatcher postWatcher=PostWatcher.newInstance(post);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container,postWatcher,postWatcher.getTag())
//                .addToBackStack(null)
//                .commit();
    }
}
