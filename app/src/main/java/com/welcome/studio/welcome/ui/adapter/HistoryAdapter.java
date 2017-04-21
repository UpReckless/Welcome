package com.welcome.studio.welcome.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 17.01.2017. !
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Post> postList;
    private Context context;
    private HistoryAdapterListener listener;
    private final int cellSize;

    public HistoryAdapter(Context context, HistoryAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        postList = new ArrayList<>();
        cellSize= Helper.getScreenWidth(context)/ Constance.ConstHolder.MAX_HISTORY_COLUMNS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_grid_item, parent, false);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        layoutParams.height = cellSize;
        layoutParams.width = cellSize;
        layoutParams.setFullSpan(false);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);
        Picasso.with(context).load(Uri.parse(post.getContentRef())).networkPolicy(NetworkPolicy.OFFLINE)
                .resize(cellSize,cellSize)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).load(Uri.parse(post.getContentRef())).error(R.drawable.load_holder).into(holder.imageView);
                    }
                });
        holder.imageView.setOnClickListener(v -> listener.postClicked(post));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setPostList(List<Post> postList){
        this.postList=postList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_view)
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
