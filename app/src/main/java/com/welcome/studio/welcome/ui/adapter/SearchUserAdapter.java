package com.welcome.studio.welcome.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.subinkrishna.widget.CircularImageView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.util.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 19.04.2017. !
 */

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    private List<User> users;
    private Context context;
    private SearchUserAdapterListener listener;

    public SearchUserAdapter(Context context, SearchUserAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        users = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        if (user.getPhotoRef() != null)
            Picasso.with(context).load(Uri.parse(user.getPhotoRef())).into(holder.imgThumb);
        holder.txtName.setText(user.getNickname());
        holder.txtRating.setText(String.valueOf(Helper.countRating(user.getRating())));
        holder.txtCountry.setText(user.getCountry());
        holder.txtCity.setText(user.getCity());
        holder.userItem.setOnClickListener(v -> listener.userProfileClicked(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addUsers(List<User> users) {
        int count = users.size();
        this.users.addAll(users);
        notifyItemInserted(count);
    }

    public void clearUsers() {
        users.clear();
    }

    public User getUserAtPosition(int position) {
        return position >= 0 && position < users.size() ? users.get(position) : null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_thumb)
        CircularImageView imgThumb;
        @Bind(R.id.txt_name)
        TextView txtName;
        @Bind(R.id.txt_rating)
        TextView txtRating;
        @Bind(R.id.txt_country)
        TextView txtCountry;
        @Bind(R.id.txt_city)
        TextView txtCity;
        @Bind(R.id.search_item)
        LinearLayout userItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
