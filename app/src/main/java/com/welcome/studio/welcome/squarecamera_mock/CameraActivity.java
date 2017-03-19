package com.welcome.studio.welcome.squarecamera_mock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.util.Constance;


public class CameraActivity extends AppCompatActivity {

    public static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //    setTheme(com.desmond.squarecamera.R.style.squarecamera__CameraFullScreenTheme);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(com.desmond.squarecamera.R.layout.squarecamera__activity_camera);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(com.desmond.squarecamera.R.id.fragment_container, CameraFragment.newInstance(), CameraFragment.TAG)
                    .commit();
        }
    }

    public void returnPhotoUri(Uri uri) {
//
//        Intent data = new Intent();
//        data.setData(uri);
//
//        if (getParent() == null) {
//            setResult(RESULT_OK, data);
//        } else {
//            getParent().setResult(RESULT_OK, data);
//        }
//
//        finish();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(com.desmond.squarecamera.R.id.fragment_container, CustomPhotoSettingsFragment.newInstance(uri), CameraFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    public void onCancel(View view) {
        getSupportFragmentManager().popBackStack();
    }

    public void returnPost(Post post) {
        Intent data = new Intent();
        data.putExtra(Constance.IntentKeyHolder.POST_KEY, post);
        if (getParent() == null)
            setResult(RESULT_OK, data);
        else getParent().setResult(RESULT_OK, data);
        finish();
    }
}
