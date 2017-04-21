package com.welcome.studio.welcome.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.squarecamera_mock.SquareImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 05.04.2017. !
 */

public class WillcomeAdapter extends RecyclerView.Adapter<WillcomeAdapter.ViewHolder> {
    private Context context;
    private List<Post> postList;
    private WillcomeAdapterListener listener;

    public WillcomeAdapter(@NonNull Context context,@NonNull WillcomeAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        postList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.willcome_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post=postList.get(position);
        holder.txtName.setText(post.getAuthor().getName());
        holder.txtAddress.setText(post.getAddress());
        holder.txtTime.setText(new SimpleDateFormat("HH:mm").format(new Date(post.getTime())) + " - "
                + new SimpleDateFormat("HH:mm").format(new Date(post.getDeleteTime())));

        Picasso.with(context).load(Uri.parse(post.getContentRef()))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.load_holder)
                .into(holder.imgContent, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).load(Uri.parse(post.getContentRef())).error(R.drawable.load_holder).into(holder.imgContent);
                    }
                });
        holder.txtName.setOnClickListener(v->listener.postClicked(post));
        holder.imgContent.setOnClickListener(v->listener.postClicked(post));
        holder.txtAddress.setOnClickListener(v->listener.addressClicked(post));
        holder.imgMapItem.setOnClickListener(v->listener.addressClicked(post));
        holder.imgCancel.setOnClickListener(v->listener.cancelClicked(post));

        holder.progressBar.setProgress(0);
        if (System.currentTimeMillis()<post.getDeleteTime() && System.currentTimeMillis()>post.getTime()) {
            holder.progressBar.setMax((int) (post.getDeleteTime() - post.getTime()) / 100);
            holder.progressBar.setProgress((int) (post.getDeleteTime() - System.currentTimeMillis()) / 100);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setPostList(List<Post> postList){
        this.postList=postList;
    }

    public void removePost(Post post){
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getId().equals(post.getId())) {
                postList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, postList.size());
                break;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_content)
        SquareImageView imgContent;
        @Bind(R.id.txt_name)
        TextView txtName;
        @Bind(R.id.img_cancel)
        ImageView imgCancel;
        @Bind(R.id.progress_bar)
        ProgressBar progressBar;
        @Bind(R.id.txt_address)
        TextView txtAddress;
        @Bind(R.id.map_item)
        ImageView imgMapItem;
        @Bind(R.id.txt_time)
        TextView txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
