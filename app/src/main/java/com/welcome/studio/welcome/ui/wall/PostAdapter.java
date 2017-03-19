package com.welcome.studio.welcome.ui.wall;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.util.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 16.02.2017. !
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<Post> postList;
    private PostAdapterListener listener;

    PostAdapter(Context context, PostAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        postList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);
        if (post != null) {
            if (post.getAuthor() != null) {
                Picasso.with(context).load(post.getAuthor().getThumbRef()).error(R.mipmap.img_avatar).into(holder.imgThumb);
                holder.txtRating.setText(String.valueOf(Helper.countRating(post.getAuthor().getRating())));
                holder.txtName.setText(post.getAuthor().getName());
            }
            if (post.getContentRef() != null)
                Picasso.with(context).load(Uri.parse(post.getContentRef())).error(R.drawable.drawer_header).into(holder.imgContent);
            else
                Picasso.with(context).load(R.drawable.drawer_header).into(holder.imgContent);
            holder.txtAdress.setText(post.getAddress());
            holder.txtLikeCount.setText(post.getLikes() != null ? String.valueOf(post.getLikes().size()) : String.valueOf(0));
            holder.imgLike.setImageDrawable(context.getResources().getDrawable(post.isLiked() ? R.mipmap.ic_heart_grey600_36dp
                    : R.mipmap.ic_heart_outline_grey600_36dp));
            holder.txtCommentCount.setText(post.getComments() == null ? "" : String.valueOf(post.getComments().size()));

            holder.imgLike.setOnClickListener(view -> listener.likeClicked(post));
            holder.imgWillcome.setOnClickListener(v -> listener.willcomeClicked(post));
            holder.imgReport.setOnClickListener(v -> listener.reportClicked(post));
            holder.imgComment.setOnClickListener(v -> listener.commentClicked(post));
            holder.imgThumb.setOnClickListener(v -> listener.userThumbClicked(post));
        }
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    void addUserPost(Post post) {
        postList.add(0, post);
        notifyItemInserted(0);
    }

    void updateUserPost(Post userPost) {
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getId() == null && postList.get(i).getAuthor().getuId() == userPost.getAuthor().getuId()) {
                postList.set(i, userPost);
                notifyItemChanged(i);
                break;
            }
        }
    }

    void addPosts(List<Post> posts) {
        postList.addAll(posts);
    }

    public Post getItemAtPosition(int position) {
        return position >= 0 && position < postList.size() ? postList.get(position) : null;
    }

    void removePost(Post post) {
        try {
            for (int i = 0; i < postList.size(); i++) {
                if (postList.get(i).getId().equals(post.getId())) {
                    postList.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, postList.size());
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("PostAdapterRemove", e.getMessage());
        }
    }

    void updatePost(Post post) {
        try {
            for (int i = 0; i < postList.size(); i++) {
                if (postList.get(i).getId().equals(post.getId())) {
                    postList.set(i, post);
                    notifyItemChanged(i);
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("PostAdapterUpdate", e.getMessage());
        }
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
