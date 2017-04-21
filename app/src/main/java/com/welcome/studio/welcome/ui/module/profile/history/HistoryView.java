package com.welcome.studio.welcome.ui.module.profile.history;

import com.welcome.studio.welcome.model.data.Post;

import java.util.List;

/**
 * Created by @mistreckless on 16.01.2017. !
 */
interface HistoryView {

    void setPostsToAdapter(List<Post> posts);
}
