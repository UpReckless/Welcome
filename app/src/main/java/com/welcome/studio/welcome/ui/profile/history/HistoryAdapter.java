package com.welcome.studio.welcome.ui.profile.history;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.welcome.studio.welcome.R;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Royal on 17.01.2017.
 */

public class HistoryAdapter extends BaseAdapter implements AdapterView {
    @Inject
    HistoryPresenter presenter;
    @Inject
    Context context;

    private ViewHolder viewHolder;
    private Target target=new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            presenter.onBitmapLoaded(bitmap,from);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Inject
    HistoryAdapter(HistoryView view) {
        view.getComponent().inject(this);
    }

    @Override
    public int getCount() {
        return presenter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return presenter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.history_grid_item, parent);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        presenter.onGetView(position);
        return convertView;
    }

    @Override
    public void loadImage(Picasso.Listener listener, String path) {
        new Picasso.Builder(context).listener(listener).build()
                .load(new File(path))
                .into(target);
    }

    @Override
    public void loadImage(Uri uri) {
        Picasso.with(context).load(uri).into(target);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        viewHolder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    private class ViewHolder {
        @Bind(R.id.img_view)
        ImageView imageView;

        ViewHolder() {
            ButterKnife.bind(this, imageView);
        }
    }
}
