package com.welcome.studio.welcome.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.welcome.studio.welcome.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Royal on 19.09.2016.
 */
public class FirstPageFragment extends Fragment {
    private String fileName;
    private ImageView backgroundImg;

    public static FirstPageFragment newInstance(String name) {
        FirstPageFragment firstPageFragment = new FirstPageFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        firstPageFragment.setArguments(args);
        return firstPageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fileName = getArguments().getString("name");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_page_fragment, container, false);
        backgroundImg = (ImageView) view.findViewById(R.id.first_image_fragment);
        loadImg(fileName);
        return view;
    }

    private void loadImg(String firstStartFirstImage) {
        if (Build.VERSION.SDK_INT >= 19) {
            try (InputStream is = getActivity().getAssets().open(firstStartFirstImage)) {
                Drawable drawable = Drawable.createFromStream(is, null);
                backgroundImg.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("catch", e.toString());
            }
        } else {
            InputStream is = null;
            try {
                is = getActivity().getAssets().open(firstStartFirstImage);
                Drawable drawable = Drawable.createFromStream(is, null);
                backgroundImg.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
