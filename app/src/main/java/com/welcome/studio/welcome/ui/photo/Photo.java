package com.welcome.studio.welcome.ui.photo;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.photo.filter_screen.FilterPreview;
import com.welcome.studio.welcome.ui.photo.preview_screen.Preview;
import com.welcome.studio.welcome.ui.photo.settings_screen.CustomPhotoSettings;
import com.welcome.studio.welcome.app.Injector;

import javax.inject.Inject;

/**
 * Created by Royal on 12.01.2017. !
 */
@Layout(id= R.layout.fragment_photo)
public class Photo extends BaseMainFragment implements PhotoView, PhotoRouter {

    @Inject
    PhotoPresenter presenter;
    public static Photo newInstance(){
        return new Photo();
    }


    @Override
    public void onStart() {
        super.onStart();
        navigateToPreviewScreen();
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
        return getTag();
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new PhotoModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    public void navigateToPreviewScreen() {
        Preview preview=Preview.newInstance();
        getChildFragmentManager().beginTransaction()
                .add(R.id.container,preview,preview.getTag())
                .commit();
    }

    @Override
    public void navigateToCustomPhotoSettingsScreen(Post post) {
        CustomPhotoSettings customPhotoSettings=CustomPhotoSettings.newInstance(post);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container,customPhotoSettings,customPhotoSettings.getTag())
                .commit();
    }

    @Override
    public void navigateFilterScreen(String picturePath) {
        FilterPreview filterPreview=FilterPreview.newInstance(picturePath);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container,filterPreview,filterPreview.getTag())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateToWall() {
        presenter.navigateToWall();
    }
}
