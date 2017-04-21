package com.welcome.studio.welcome.model.repository.impl;

import android.content.Context;

import com.esotericsoftware.kryo.Kryo;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.KeyIterator;
import com.snappydb.SnappydbException;
import com.welcome.studio.welcome.model.RestApi;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.entity.UserRequest;
import com.welcome.studio.welcome.model.repository.PostRepository;
import com.welcome.studio.welcome.util.Constance;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by @mistreckless on 19.03.2017. !
 */

public class PostRepositoryImpl implements PostRepository {
    private Context context;
    private RestApi restApi;

    @Inject
    public PostRepositoryImpl(Context context, RestApi restApi) {
        this.context = context;
        this.restApi=restApi;
    }


    @Override
    public void savePosts(List<Post> posts) {
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "welcomedb", new Kryo());
            for (Post post :
                    posts) {
                if (!snappyDb.exists(post.getId()))
                    snappyDb.put(post.getId(), post);
            }
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "welcomedb", new Kryo());
            KeyIterator it = snappyDb.allKeysIterator();
            while (it.hasNext())
                for (String key : it.next(Constance.ConstHolder.MAX_POST_LIMIT))
                    posts.add(snappyDb.get(key, Post.class));


        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
        return posts;
    }

    @Override
    public void removePost(String key) {
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "welcomedb", new Kryo());
            if (snappyDb.exists(key))
                snappyDb.del(key);
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void update(Post post) {
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "welcomedb", new Kryo());
            if (snappyDb.exists(post.getId()))
                snappyDb.put(post.getId(), post);
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void savePost(Post post) {
        DB snappyDb = null;
        try {
            snappyDb = DBFactory.open(context, "welcomedb", new Kryo());
            if (!snappyDb.exists(post.getId()))
                snappyDb.put(post.getId(), post);
        } catch (SnappydbException e) {
            e.printStackTrace();
        } finally {
            if (snappyDb != null)
                try {
                    snappyDb.close();
                } catch (SnappydbException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public Observable<List<Post>> getHistoryPosts(UserRequest userRequest) {
        return restApi.getHistoryUserPosts(userRequest);
    }

    @Override
    public Observable<List<Post>> getNowPosts(UserRequest userRequest) {
        return restApi.getNowUserPosts(userRequest);
    }

    @Override
    public Observable<List<Post>> getWillcomePosts(UserRequest userRequest) {
        return restApi.getWillcomeUserPosts(userRequest);
    }

}
