package com.welcome.studio.welcome.ui.wall;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.welcome.studio.welcome.util.Helper;
import com.welcome.studio.welcome.util.tagview.TagsView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public PostAdapter(Context context, PostAdapterListener listener) {
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
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (payloads != null && payloads.size() > 0) {
            Post post = postList.get(position);
            if (post != null) {
                initPost(holder, post, false);
                initItems(holder, post);
            }
        } else onBindViewHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = postList.get(position);
        if (post != null) {
            if (post.getContentRef() == null && post.getContentPath() != null)
                initUserHolderPost(holder, post, position);
            else initPost(holder, post, true);
            initItems(holder, post);
        }
    }

    private void initItems(ViewHolder holder, Post post) {
        holder.txtLikeCount.setText(post.getLikes() != null ? String.valueOf(post.getLikes().size()) : "");
        holder.imgLike.setImageDrawable(context.getResources().getDrawable(post.isLiked() ? R.mipmap.ic_like
                : R.mipmap.ic_like_outline));
        holder.txtCommentCount.setText(post.getComments() != null ? String.valueOf(post.getComments().size()) : "");
        holder.txtWillcomeCount.setText(post.getWillcomes() != null ? String.valueOf(post.getWillcomes().size()) : "");
        holder.imgWillcome.setImageDrawable(context.getResources().getDrawable(post.isWillcomed() ? R.mipmap.ic_willcome_clicked
                : R.mipmap.ic_willcome));
        holder.txtReportCount.setText(post.getReports() != null ? String.valueOf(post.getReports().size()) : "");
        holder.imgReport.setImageDrawable(context.getResources().getDrawable(post.isReported() ? R.mipmap.ic_report_clicked
                : R.mipmap.ic_report));

        holder.txtAdress.setText(post.getAddress());
        holder.txtDesc.setText(post.getDescription());
        holder.txtTime.setText(new SimpleDateFormat("HH:mm").format(new Date(post.getTime())) + " - "
                + new SimpleDateFormat("HH:mm").format(new Date(post.getDeleteTime())));
        if (post.getTags() != null) {
            holder.tagsView.removeAllTags();
            holder.tagsView.addTags(post.getTags().toArray(new String[post.getTags().size()]));
        }
    }

    private void initPost(ViewHolder holder, Post post, boolean needToLoadImage) {
        holder.progressBar.setVisibility(View.INVISIBLE);
        holder.imgRestore.setVisibility(View.INVISIBLE);
        holder.imgDelete.setVisibility(View.INVISIBLE);
        initAuthor(holder, post);
        if (needToLoadImage)
            Picasso.with(context).load(Uri.parse(post.getContentRef()))
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .error(R.drawable.load_holder)
                    .into(holder.imgContent, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.imgContent.clearAnimation();
                            if (holder.imgContent.getAnimation() != null)
                                holder.imgContent.getAnimation().cancel();
                        }

                        @Override
                        public void onError() {
                            Picasso.with(context).load(Uri.parse(post.getContentRef())).error(R.drawable.load_holder).into(holder.imgContent);
                        }
                    });
        holder.imgContent.clearAnimation();
        if (holder.imgContent.getAnimation() != null)
            holder.imgContent.getAnimation().cancel();

        holder.imgLike.setOnClickListener(view -> listener.likeClicked(post));
        holder.imgWillcome.setOnClickListener(v -> listener.willcomeClicked(post));
        holder.imgReport.setOnClickListener(v -> listener.reportClicked(post));
        holder.imgComment.setOnClickListener(v -> listener.commentClicked(post));
        holder.imgThumb.setOnClickListener(v -> listener.userThumbClicked(post));
        holder.tagsView.setOnTagClickListener((i, tag) -> {
        });
    }

    private void initUserHolderPost(ViewHolder holder, Post post, int position) {
        Log.e("Content path", post.getContentPath());
        if (!post.isTryToUpload()) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.imgRestore.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);
        } else {
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.imgRestore.setVisibility(View.VISIBLE);
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.imgRestore.setOnClickListener(v -> listener.tryAgainClicked(post));
            holder.imgDelete.setOnClickListener(v -> {
                postList.remove(position);
                notifyItemRemoved(position);
            });
        }
        initAuthor(holder, post);
        Picasso.with(context).load(new File(post.getContentPath()))
                .error(R.drawable.load_holder)
                .into(holder.imgContent);
        holder.imgContent.animate().alpha(0.2f).setDuration(1000);
    }

    private void initAuthor(ViewHolder holder, Post post) {
        Picasso.with(context).load(post.getAuthor().getThumbRef()).networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.imgThumb, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).load(Uri.parse(post.getContentRef())).error(R.drawable.load_holder).into(holder.imgContent);
                    }
                });
        holder.txtRating.setText(String.valueOf(Helper.countRating(post.getAuthor().getRating())));
        holder.txtName.setText(post.getAuthor().getName());
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    void addUserPost(Post post) {
        if (postList.size() > 0)
            if (postList.get(0).getId() == null && post.getContentPath().equals(postList.get(0).getContentPath())) {
                postList.set(0, post);
                notifyItemChanged(0);
            } else {
                postList.add(0, post);
                notifyItemInserted(0);
            }
        else {
            postList.add(0, post);
            notifyItemInserted(0);
        }
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

    public void addPosts(List<Post> posts) {
        postList.addAll(posts);
    }

    public Post getItemAtPosition(int position) {
        return position >= 0 && position < postList.size() ? postList.get(position) : null;
    }

    public void removePost(Post post) {
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

    public void updatePost(Post post) {
        try {
            for (int i = 0; i < postList.size(); i++) {
                if (postList.get(i).getId().equals(post.getId())) {
                    postList.set(i, post);
                    notifyItemChanged(i, 0);
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
        @Bind(R.id.tags_view)
        TagsView tagsView;
        @Bind(R.id.txt_description)
        TextView txtDesc;
        @Bind(R.id.txt_time)
        TextView txtTime;
        @Bind(R.id.progress_bar)
        ProgressBar progressBar;
        @Bind(R.id.img_restore)
        ImageView imgRestore;
        @Bind(R.id.img_delete)
        ImageView imgDelete;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
