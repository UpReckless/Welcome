package com.welcome.studio.welcome.ui.registry;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseFragment;
import com.welcome.studio.welcome.ui.main.MainActivity;
import com.welcome.studio.welcome.util.Constance;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Royal on 06.01.2017.
 */

public class SignUp extends BaseFragment implements SignUpView {
    private RegistryComponent registryComponent;
    @Inject
    SignUpPresenter presenter;
    @Inject
    Context context;
    @Bind(R.id.photo)
    CircularImageView imgPhoto;
    @Bind(R.id.edit_name)
    EditText edName;
    @Bind(R.id.edit_mail)
    EditText edMail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registryComponent = ((MainActivity) getActivity()).getComponent().plus(new RegistryModule(this));
        registryComponent.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registryComponent = null;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public RegistryComponent getComponent() {
        return registryComponent;
    }

    @Override
    public boolean permissionCheck(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void sendIntentToGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constance.IntentCodeHolder.LOAD_PHOTO_FROM_GALLERY);
    }

    @Override
    public void requestPermission(String[] permissions, int requestCode) {
        requestPermissions(permissions, requestCode);
    }

    @Override
    public ContentResolver getContentResolver() {
        return context.getContentResolver();
    }

    @Override
    public void drawPhoto(String pathToPhoto) {
        Picasso.with(context).load(new File(pathToPhoto)).into(imgPhoto);
    }

    @SuppressLint("HardwareIds")
    @Override
    public String getImei() {
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public String getEdName() {
        return edName.getText().toString();
    }

    @Override
    public String getEdEmail() {
        return edMail.getText().toString();
    }

    @Override
    public void showToast(int toast_error_message) {
        Toast.makeText(context, toast_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void start() {
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra(Constance.IntentKeyHolder.KEY_IS_AUTH,true);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.btn_go)
    public void onBtnGoClick(View view) {
        presenter.onBtnGoClick();
    }

    @OnClick(R.id.photo)
    public void onImgPhotoTouch(View view) {
        presenter.onImgPhotoTouch();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }
}
