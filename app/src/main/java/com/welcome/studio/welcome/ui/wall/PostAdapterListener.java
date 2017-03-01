package com.welcome.studio.welcome.ui.wall;

import com.welcome.studio.welcome.model.data.Post;

/**
 * Created by @mistreckless on 27.02.2017. !
 */

interface PostAdapterListener {
    void likeClicked(Post post, int position);

    void willcomeClicked(Post post, int position);

    void reportClicked(Post post, int position);

    void commentClicked(Post post);

    void userThumbClicked(Post post);
}
