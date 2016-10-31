package com.welcome.studio.welcome.view.fragment.firststart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.activity.MainActivity;
import com.welcome.studio.welcome.view.activity.MainActivityImpl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class LastPageFragmentImpl extends Fragment implements View.OnTouchListener,LastPageFragment {

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
        presenter=new LastPagePresenterImpl(this);
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
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Constance.IntentCodeHolder.LOAD_PHOTO_FROM_GALLERY);
        });
        btnGo.setOnTouchListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onPhotoReturn(requestCode,resultCode,data,getContext());
    }


    private String getDeviceImei() {
        TelephonyManager telephonyManager=(TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:{
                btnGo.setImageDrawable(getResources().getDrawable(R.mipmap.tapped_img_btn_go));
                break;
            }
            case MotionEvent.ACTION_UP:{
                btnGo.setImageDrawable(getResources().getDrawable(R.mipmap.img_btn_go));
                presenter.regBtnClick(editName.getText().toString(),editMail.getText().toString(),getDeviceImei());
                break;
            }
        }
        return true;
    }

    @Override
    public void showToast(int strId) {
        Toast.makeText(getContext(),strId,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void drawPhoto(Bitmap bitmap) {
        imgAvatar.setImageBitmap(bitmap);
    }

    @Override
    public void savePreferences(String imei, String name) {
        SharedPreferences spf=getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putString(Constance.SharedPreferencesHolder.NAME,name);
        ed.putString(Constance.SharedPreferencesHolder.IMEI,imei);
        ed.apply();
    }

    @Override
    public void start(boolean isAuth) {
        Intent intent=new Intent(getContext(), MainActivityImpl.class);
        intent.putExtra(Constance.IntentKeyHolder.KEY_IS_FIRST,isAuth);
        startActivity(intent);
        getActivity().finish();
    }
}
