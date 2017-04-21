package com.welcome.studio.welcome.ui.adapter;

import com.welcome.studio.welcome.model.data.Post;

/**
 * Created by @mistreckless on 27.02.2017. !
 */

public interface PostAdapterListener {
    void likeClicked(Post post);

    void willcomeClicked(Post post);

    void reportClicked(Post post);

    void commentClicked(Post post);

    void userProfileClicked(Post post);

    void tryAgainClicked(Post post);

    void likeCountClicked(Post post);

    void willcomeCountClicked(Post post);

    void reportCountClicked(Post post);

    void addressLineClicked(Post post);
}
