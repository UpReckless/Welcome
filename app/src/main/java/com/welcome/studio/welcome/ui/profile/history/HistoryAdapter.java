package com.welcome.studio.welcome.ui.profile.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.ArchivePhoto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Royal on 17.01.2017.
 */

class HistoryAdapter extends BaseAdapter {

    private Context context;
    private List<ArchivePhoto> archivePhotoList;

    @Inject
    HistoryAdapter(Context context) {
        this.context = context;
        archivePhotoList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return archivePhotoList.size();
    }

    @Override
    public ArchivePhoto getItem(int position) {
        return archivePhotoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.history_grid_item, parent);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        Picasso.with(context).load(getItem(position).getPhotoPath()).into(viewHolder.imageView);
        return convertView;
    }

    public void setArchivePhotoList(List<ArchivePhoto> archivePhotoList) {
        this.archivePhotoList = archivePhotoList;
    }

    class ViewHolder {
        @Bind(R.id.img_view)
        ImageView imageView;

        ViewHolder() {
            ButterKnife.bind(this, imageView);
        }
    }
}
