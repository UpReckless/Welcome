package com.welcome.studio.welcome.view.fragment.firststart;

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
import android.support.v4.app.ActivityCompat;
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

import com.mikhaellopez.circularimageview.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.presenter.LastPagePresenter;
import com.welcome.studio.welcome.presenter.LastPagePresenterImpl;
import com.welcome.studio.welcome.util.App;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.activity.MainActivity;
import com.welcome.studio.welcome.view.activity.MainActivityImpl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class LastPageFragmentImpl extends Fragment implements View.OnTouchListener, LastPageFragment {

    private LastPagePresenter presenter;

    private ImageView btnGo;
    private EditText editName;
    private EditText editMail;
    private CircularImageView imgAvatar;

    public static LastPageFragmentImpl newInstance() {
        return new LastPageFragmentImpl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_last_page_fragment, container, false);
        presenter = new LastPagePresenterImpl(this);
        setComponents(view);
        return view;
    }

    private void setComponents(final View view) {
        btnGo = (ImageView) view.findViewById(R.id.button_go);
        editName = (EditText) view.findViewById(R.id.edit_name);
        editMail = (EditText) view.findViewById(R.id.edit_email);
        imgAvatar = (CircularImageView) view.findViewById(R.id.avatar_image_view);
        imgAvatar.setBorderColor(getResources().getColor(R.color.colorWhite));
        imgAvatar.setBorderWidth(10);
        imgAvatar.addShadow();
        imgAvatar.setShadowRadius(15);
        imgAvatar.setShadowColor(Color.GRAY);
        imgAvatar.addShadow();
        imgAvatar.setOnClickListener(view1 -> {
            int permissionCheck = ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

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
                int permissionCheck = ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.READ_PHONE_STATE);

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
    public void drawPhoto(Bitmap bitmap) {
        imgAvatar.setImageBitmap(bitmap);
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
