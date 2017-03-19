package com.welcome.studio.welcome.ui.wall;

import com.welcome.studio.welcome.model.data.Post;

/**
 * Created by @mistreckless on 27.02.2017. !
 */

interface PostAdapterListener {
    void likeClicked(Post post);

    void willcomeClicked(Post post);

    void reportClicked(Post post);

    void commentClicked(Post post);

    void userThumbClicked(Post post);
}
