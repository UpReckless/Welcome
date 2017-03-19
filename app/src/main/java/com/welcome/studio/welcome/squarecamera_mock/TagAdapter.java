package com.welcome.studio.welcome.squarecamera_mock;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.util.tagview.OnTagClickListener;
import com.welcome.studio.welcome.util.tagview.Tag;
import com.welcome.studio.welcome.util.tagview.TagView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 17.02.2017. !
 */

class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private List<Tag> tags;
    private OnTagClickListener listener;

    TagAdapter(List<Tag> tags, OnTagClickListener listener) {
        this.tags = tags;
        this.listener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.tag_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tag tag=tags.get(position);
        holder.tagView.setTag(tag);
        holder.tagView.setOnClickListener((view)->{
            removeTag(position);
            listener.onTagClick(position,tag);
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    void addTag(Tag tag){
        tags.add(tag);
        notifyDataSetChanged();
    }
    private void removeTag(int position){
        tags.remove(position);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tag)
        TagView tagView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
