package com.welcome.studio.welcome.ui.wall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.util.Helper;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 16.02.2017. !
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);
        Picasso.with(context).load(R.id.img_avatar).into(holder.imgThumb);
        holder.txtRating.setText(String.valueOf(Helper.countRating(post.getUserRating())));
        holder.txtName.setText(post.getUserName());
        Picasso.with(context).load(new File(post.getContentPath())).into(holder.imgContent);//need to write!!!
        holder.txtAdress.setText(post.getAddress());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_thumb)
        ImageView imgThumb;
        @Bind(R.id.txt_rating)
        TextView txtRating;
        @Bind(R.id.txt_name)
        TextView txtName;
        @Bind(R.id.txt_report_count)
        TextView txtReportCount;
        @Bind(R.id.img_report)
        ImageView imgReport;
        @Bind(R.id.txt_address)
        TextView txtAdress;
        @Bind(R.id.img_content)
        ImageView imgContent;
        @Bind(R.id.img_like)
        ImageView imgLike;
        @Bind(R.id.txt_like_count)
        TextView txtLikeCount;
        @Bind(R.id.img_comment)
        ImageView imgComment;
        @Bind(R.id.txt_comment_count)
        TextView txtCommentCount;
        @Bind(R.id.img_willcome)
        ImageView imgWillcome;
        @Bind(R.id.txt_willcome_count)
        TextView txtWillcomeCount;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
