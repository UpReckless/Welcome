package com.welcome.studio.welcome.view.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.ModelFirebase;
import com.welcome.studio.welcome.model.entity.ArchivePhoto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Royal on 28.11.2016.
 */

public class ArchivePhotoAdapter extends BaseAdapter implements ArchivePhotoModel, ArchivePhotoView {

    @Inject
    ModelFirebase modelFirebase;
    private final Context context;
    private List<ArchivePhoto> archivePhotoList;

    public ArchivePhotoAdapter(Context context) {
        this.context = context;
        archivePhotoList = new ArrayList<>();
    }

    @Override
    public void add(ArchivePhoto archivePhoto) {
        archivePhotoList.add(archivePhoto);
    }

    @Override
    public ArchivePhoto remove(int position) {
        return archivePhotoList.remove(position);
    }

    @Override
    public ArchivePhoto getPhoto(int position) {
        return archivePhotoList.get(position);
    }

    @Override
    public int getSize() {
        return archivePhotoList.size();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return getSize();
    }

    @Override
    public Object getItem(int i) {
        return getPhoto(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_img_item, viewGroup);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.grid_img_view);
            view.setTag(viewHolder);
        } else viewHolder = (ViewHolder) view.getTag();
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(((picasso, uri, exception) -> modelFirebase.downloadURl(archivePhotoList.get(i).getPhotoRef())
                        .addOnSuccessListener(uri1 -> Picasso.with(context).load(uri1).into(viewHolder.imageView))
                        .addOnFailureListener(failure -> Picasso.with(context).load(R.drawable.img_avatar).into(viewHolder.imageView))
        ));
        builder.build()
                .load(archivePhotoList.get(i).getPhotoPath())
                .into(viewHolder.imageView);
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
