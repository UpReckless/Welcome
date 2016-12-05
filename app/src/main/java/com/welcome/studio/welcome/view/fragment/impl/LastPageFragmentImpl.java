package com.welcome.studio.welcome.view.fragment.impl;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.dagger.FirstStartComponent;
import com.welcome.studio.welcome.presenter.LastPagePresenter;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.activity.MainActivityImpl;
import com.welcome.studio.welcome.view.fragment.LastPageFragment;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LastPageFragmentImpl extends Fragment implements View.OnTouchListener, LastPageFragment {

    @Inject
    LastPagePresenter presenter;
    private FirstStartComponent firstStartComponent;
    @Bind(R.id.button_go) ImageView btnGo;
    @Bind(R.id.edit_name) EditText editName;
    @Bind(R.id.edit_email) EditText editMail;
    @Bind(R.id.avatar_image_view) CircularImageView imgAvatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstStartComponent=((MainActivityImpl) getActivity()).getFirstStartComponent();
        firstStartComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_last_page_fragment, container, false);
        setComponents(view);
        return view;
    }

    private void setComponents(final View view) {
        ButterKnife.bind(this,view);
        imgAvatar.setBorderColor(getResources().getColor(R.color.colorWhite));
        imgAvatar.setBorderWidth(10,10);
        imgAvatar.setShadowRadius(15);
        imgAvatar.setShadowColor(Color.GRAY);
        imgAvatar.setOnClickListener(view1 -> {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constance.CallbackPermissionsHolder.REQUEST_WRITE_EXTERNAL_STORAGE);


            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Constance.IntentCodeHolder.LOAD_PHOTO_FROM_GALLERY);
            }
        });
        btnGo.setOnTouchListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onPhotoReturn(requestCode, resultCode, data, getContext());
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                btnGo.setImageDrawable(getResources().getDrawable(R.mipmap.tapped_img_btn_go));
                break;
            }
            case MotionEvent.ACTION_UP: {
                btnGo.setImageDrawable(getResources().getDrawable(R.mipmap.img_btn_go));
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_PHONE_STATE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                            Constance.CallbackPermissionsHolder.REQUEST_READ_PHONE_STATE);
                } else {
                    TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                    presenter.regBtnClick(editName.getText().toString(), editMail.getText().toString(), telephonyManager.getDeviceId());
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void showToast(int strId) {
        Toast.makeText(getContext(), strId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void drawPhoto(String path) {
        Picasso.with(getContext()).load(new File(path)).into(imgAvatar);
    }

    @Override
    public void savePreferences(String imei, String name) {
        SharedPreferences spf = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = spf.edit();
        ed.putString(Constance.SharedPreferencesHolder.NAME, name);
        ed.putString(Constance.SharedPreferencesHolder.IMEI, imei);
        ed.apply();
    }

    @Override
    public void start(boolean isAuth) {
        Intent intent = new Intent(getContext(), MainActivityImpl.class);
        intent.putExtra(Constance.IntentKeyHolder.KEY_IS_FIRST, isAuth);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public FirstStartComponent getComponent() {
        return firstStartComponent;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constance.CallbackPermissionsHolder.REQUEST_READ_PHONE_STATE: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                    presenter.regBtnClick(editName.getText().toString(), editMail.getText().toString(), telephonyManager.getDeviceId());
                    break;
                } else getActivity().finish();
            }
            case Constance.CallbackPermissionsHolder.REQUEST_WRITE_EXTERNAL_STORAGE: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, Constance.IntentCodeHolder.LOAD_PHOTO_FROM_GALLERY);
                }
            }
        }
    }
}
