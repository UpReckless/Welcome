package com.welcome.studio.welcome.ui.module.profile.now;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;

import java.util.List;

/**
 * Created by @mistreckless on 16.01.2017. !
 */
public interface NowView {

    void initAdapters(User user);

    void setPostList(List<Post> posts);

    void changePost(Post post);

    void removePost(Post post);

    void setWillcomeList(List<Post> posts);

    void removeWillcome(Post post);

    void showToast(String message);
}
