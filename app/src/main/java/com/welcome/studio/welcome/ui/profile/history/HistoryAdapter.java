package com.welcome.studio.welcome.ui.profile.history;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 17.01.2017. !
 */

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Post> postList;
    private Context context;
    private HistoryAdapterListener listener;

    HistoryAdapter(Context context, HistoryAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        postList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_grid_item, parent, false);
        return new ViewHolder(itemVIew);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);
        Picasso.with(context).load(Uri.parse(post.getContentRef())).networkPolicy(NetworkPolicy.OFFLINE)
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

    void setPostList(List<Post> postList){
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
