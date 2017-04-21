package com.welcome.studio.welcome.ui.adapter;

import com.welcome.studio.welcome.model.data.Post;

/**
 * Created by @mistreckless on 05.04.2017. !
 */

public interface WillcomeAdapterListener {
    void addressClicked(Post post);

    void cancelClicked(Post post);

    void postClicked(Post post);
}
