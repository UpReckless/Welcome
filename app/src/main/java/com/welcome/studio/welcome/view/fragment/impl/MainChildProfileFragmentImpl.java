package com.welcome.studio.welcome.view.fragment.impl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.GridView;

import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.mock.MockGridAdapter;
import com.welcome.studio.welcome.presenter.MainChildProfilePresenter;
import com.welcome.studio.welcome.presenter.impl.MainChildProfilePresenterImpl;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.view.fragment.BaseFragment;
import com.welcome.studio.welcome.view.fragment.MainChildProfileFragment;

/**
 * Created by Royal on 18.11.2016.
 */

public class MainChildProfileFragmentImpl extends BaseFragment implements MainChildProfileFragment {

    private GridView gridImgView;
    private CircularImageView mainPhoto;
    private MockGridAdapter adapter;
    private MainChildProfilePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new MainChildProfilePresenterImpl(this,getActivity().getApplicationContext());
        adapter=new MockGridAdapter(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        gridImgView = (GridView) view.findViewById(R.id.grid_img_view);
        mainPhoto = (CircularImageView) view.findViewById(R.id.main_profile_img);
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constance.CallbackPermissionsHolder.REQUEST_WRITE_EXTERNAL_STORAGE);
        } else
            gridImgView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constance.CallbackPermissionsHolder.REQUEST_WRITE_EXTERNAL_STORAGE) {
            if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gridImgView.setAdapter(adapter);
            }
        }
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.main_profile;
    }
}
