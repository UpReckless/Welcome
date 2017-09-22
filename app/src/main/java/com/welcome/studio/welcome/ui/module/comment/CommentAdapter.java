package com.welcome.studio.welcome.ui.module.comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;
import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.CommentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by @mistreckless on 28.02.2017. !
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<CommentModel> comments;
    private Context context;
    private CommentAdapterListener listener;

    CommentAdapter(Context context, CommentAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        comments = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentModel comment = comments.get(position);
        Picasso.with(context).load(comment.getAuthor().getThumbRef()).error(R.mipmap.img_avatar).into(holder.imgThumb);
        holder.txtName.setText(comment.getAuthor().getName());
        holder.imgLike.setImageDrawable(context.getResources().getDrawable(comment.isLiked() ? R.mipmap.ic_heart_grey600_36dp : R.mipmap.ic_heart_outline_grey600_36dp));
        holder.txtLikeCount.setText(comment.getLikes() == null ? "" : String.valueOf(comment.getLikes().size()));
        holder.txtComment.setText(comment.getText());

        holder.imgThumb.setOnClickListener(v -> listener.userProfileClicked(comment));
        holder.txtName.setOnClickListener(v -> listener.userProfileClicked(comment));
        RxView.clicks(holder.imgLike)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(aVoid -> listener.likeClicked(comment, position));
        holder.txtLikeCount.setOnClickListener(v -> listener.likeCountClicked(comment));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    void addComments(List<CommentModel> comments) {
        this.comments.addAll(comments);
    }

    void addComment(CommentModel commentModel) {
        comments.add(commentModel);
    }

    void updateCommentEvent(CommentModel comment) {
        try {
            for (int i = 0; i < comments.size(); i++) {
                if (comments.get(i).getId().equals(comment.getId())) {
                    comments.set(i, comment);
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
        CircularImageView imgThumb;
        @Bind(R.id.txt_name)
        TextView txtName;
        @Bind(R.id.txt_comment)
        TextView txtComment;
        @Bind(R.id.img_like)
        ImageView imgLike;
        @Bind(R.id.txt_like_count)
        TextView txtLikeCount;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}