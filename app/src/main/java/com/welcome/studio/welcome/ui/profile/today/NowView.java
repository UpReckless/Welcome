package com.welcome.studio.welcome.ui.profile.today;

import com.welcome.studio.welcome.model.data.Post;

import java.util.List;

/**
 * Created by @mistreckless on 16.01.2017. !
 */
public interface NowView {

    void setPostList(List<Post> posts);

    void changePost(Post post);

    void removePost(Post post);
}
