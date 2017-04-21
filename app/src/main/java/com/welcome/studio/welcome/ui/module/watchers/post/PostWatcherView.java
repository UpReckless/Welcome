package com.welcome.studio.welcome.ui.module.watchers.post;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;

/**
 * Created by @mistreckless on 31.03.2017. !
 */
public interface PostWatcherView {
    void initUi(Post post, User user);

    void showToast(String message);

    void updatePost(Post post, User user);
}
