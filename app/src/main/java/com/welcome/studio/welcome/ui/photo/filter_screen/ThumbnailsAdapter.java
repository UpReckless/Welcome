package com.welcome.studio.welcome.ui.photo.filter_screen;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.welcome.studio.welcome.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Royal on 11.02.2017. !
 */

public class ThumbnailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "THUMBNAILS_ADAPTER";
    private static int lastPosition = -1;
    private ThumbnailCallback thumbnailCallback;
    private List<ThumbnailItem> dataSet;

    public ThumbnailsAdapter(List<ThumbnailItem> dataSet, ThumbnailCallback thumbnailCallback) {
        Log.e(TAG, "Thumbnails Adapter has " + dataSet.size() + " items");
        this.dataSet = dataSet;
        this.thumbnailCallback = thumbnailCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(TAG, "On Create View Holder Called");
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.list_thumbnail_item, parent, false);
        return new ThumbnailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ThumbnailItem thumbnailItem = dataSet.get(position);
        Log.v(TAG, "On Bind View Called");
        ThumbnailsViewHolder thumbnailsViewHolder = (ThumbnailsViewHolder) holder;
        thumbnailsViewHolder.thumbnail.setImageBitmap(thumbnailItem.image);
        thumbnailsViewHolder.thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
        thumbnailsViewHolder.thumbnail.setOnClickListener(v -> {
            if (lastPosition != position) {
                thumbnailCallback.onThumbnailClick(thumbnailItem.filter);
                lastPosition = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class ThumbnailsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.thumbnail)
        ImageView thumbnail;

        ThumbnailsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
