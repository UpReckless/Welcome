package com.welcome.studio.welcome.model.repository;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.entity.UserRequest;

import java.util.List;

import rx.Observable;

/**
 * Created by @mistreckless on 19.03.2017. !
 */

public interface PostRepository {
    void savePosts(List<Post> posts);

    List<Post> getAllPosts();

    void removePost(String key);

    void update(Post post);

    void savePost(Post post);

    Observable<List<Post>> getHistoryPosts(UserRequest userRequest);

    Observable<List<Post>> getNowPosts(UserRequest userRequest);

    Observable<List<Post>> getWillcomePosts(UserRequest userRequest);
}
