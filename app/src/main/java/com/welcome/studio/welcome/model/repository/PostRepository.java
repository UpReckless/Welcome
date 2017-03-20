package com.welcome.studio.welcome.model.repository;

import com.welcome.studio.welcome.model.data.Post;

import java.util.List;

/**
 * Created by @mistreckless on 19.03.2017. !
 */

public interface PostRepository {
    void savePosts(List<Post> posts);

    List<Post> getAllPosts();

    void removePost(String key);

    void update(Post post);

    void savePost(Post post);
}
