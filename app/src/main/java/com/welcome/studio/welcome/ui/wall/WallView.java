package com.welcome.studio.welcome.ui.wall;

import com.welcome.studio.welcome.model.data.Post;

import java.util.List;

/**
 * Created by @mistreckless on 12.01.2017. !
 */

interface WallView {

    void setFabEnabled(Boolean enabled);

    void addPosts(List<Post> posts);

    void refreshPosts(int position);

    void refreshPost(int position);

    void refresh();

    void removePost(Post post);

    void updatePostEvent(Post post);

    void updatePostView(Post post, int position);
}
