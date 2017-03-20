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

    void removePost(Post post);

    void updatePost(Post post);

    void showToast(String message);

    void setUserPost(Post post);

    void updateUserPost(Post post);
}
