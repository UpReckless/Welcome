package com.welcome.studio.welcome.ui.photo.preview_screen;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.TextureView;
import android.widget.ImageView;

import com.upreckless.support.portraitcamerasupport.AutoFitTextureView;
import com.upreckless.support.portraitcamerasupport.CameraSupport;
import com.upreckless.support.portraitcamerasupport.CameraSupportFactory;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.photo.PhotoModule;
import com.welcome.studio.welcome.app.Injector;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Royal on 11.02.2017. !
 */
@Layout(id = R.layout.fragment_preview)
public class Preview extends BaseMainFragment implements PreviewView, TextureView.SurfaceTextureListener {

    @Inject
    PreviewPresenter presenter;
    @Bind(R.id.texture)
    AutoFitTextureView autoFitTextureView;
    @Bind(R.id.img_flash)
    ImageView imgFlash;

    public static Preview newInstance(){
        return new Preview();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CameraSupport cameraSupport = CameraSupportFactory.getInstance().getCameraSupport(getActivity(), autoFitTextureView);
        presenter.setCameraSupport(cameraSupport);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!autoFitTextureView.isAvailable())
            autoFitTextureView.setSurfaceTextureListener(this);
        else presenter.resume(autoFitTextureView.getSurfaceTexture(),
                autoFitTextureView.getWidth(), autoFitTextureView.getHeight());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        presenter.available(surface, width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        presenter.resume(surface, width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    protected Object getRouter() {
        return getParentFragment();
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

    @OnClick(R.id.picture)
    public void onTakePictureClick() {
        presenter.takePicture();
    }

    @OnClick(R.id.img_reselect)
    public void onCameraReseectClick() {
        presenter.cameraReselect();
    }

    @OnClick(R.id.img_flash)
    public void onFlashClick() {
        presenter.flashClick();
    }

    @Override
    public void setFlashImage(boolean b) {
        imgFlash.setImageDrawable(getResources().getDrawable(b ?
                R.mipmap.ic_flash_auto_white_24dp : R.mipmap.ic_flash_off_white_24dp, null));
    }
}
