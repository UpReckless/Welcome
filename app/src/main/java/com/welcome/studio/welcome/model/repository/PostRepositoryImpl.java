package com.welcome.studio.welcome.model.repository;

import android.content.Context;

import com.esotericsoftware.kryo.Kryo;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.util.Constance;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by @mistreckless on 19.03.2017. !
 */

public class PostRepositoryImpl implements PostRepository {
    private Context context;

    @Inject
    public PostRepositoryImpl(Context context) {
        this.context = context;
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
            for (String[] batch : snappyDb.allKeysIterator().byBatch(Constance.ConstHolder.MAX_POST_LIMIT)) {
                for (String key : batch)
                    posts.add(snappyDb.get(key, Post.class));
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
                snappyDb.put(post.getId(),post);
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
                snappyDb.put(post.getId(),post);
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
}
