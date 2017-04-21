package com.welcome.studio.welcome.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.entity.Author;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 12.04.2017. !
 */

public class AuthorWatcherAdapter extends RecyclerView.Adapter<AuthorWatcherAdapter.ViewHolder> {
    private Context context;
    private List<Author> authors;
    private AuthorWatcherAdapterListener listener;

    public AuthorWatcherAdapter(Context context, AuthorWatcherAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        authors = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Author author = authors.get(position);
        Picasso.with(context).load(author.getThumbRef()).error(R.mipmap.img_avatar).into(holder.imgThumb);
        holder.txtName.setText(author.getName());
        holder.linearLayout.setOnClickListener(v -> listener.authorClicker(author));
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_thumb)
        CircularImageView imgThumb;
        @Bind(R.id.txt_name)
        TextView txtName;
        @Bind(R.id.linear_layout)
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
