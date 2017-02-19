package com.welcome.studio.welcome.ui.registry.singup.last_screen;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.registry.RegistryModule;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.app.Injector;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Royal on 09.02.2017.
 */
@Layout(id = R.layout.fragment_next_step)
public class NextStep extends BaseMainFragment implements NextStepView {

    @Inject
    NextStepPresenter presenter;
    @Bind(R.id.img_avatar)
    CircularImageView imgAvatar;
    @Bind(R.id.txt_header)
    TextView txtHeader;

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
        return null;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new RegistryModule()).inject(this);
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public void setHeaderText(String text) {
        txtHeader.setText(text);
    }

    @Override
    public void setMainPhoto(String path) {
        Picasso.with(getContext()).load(new File(path)).into(imgAvatar);
    }

    @Override
    public void sendIntentToGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constance.IntentCodeHolder.LOAD_PHOTO_FROM_GALLERY);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.galleryResult(requestCode,resultCode,data);
    }

    @OnClick(R.id.btn_finish)
    public void onFinishClick(){
        presenter.finishRegistry();
    }
    @OnClick(R.id.img_avatar)
    public void onImgClick(){
        presenter.choosePhoto();
    }
}
