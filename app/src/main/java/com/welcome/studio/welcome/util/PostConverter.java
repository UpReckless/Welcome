package com.welcome.studio.welcome.util;

import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.Willcome;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.welcome.studio.welcome.util.Constance.ConstHolder.MAX_POST_LIMIT;

/**
 * Created by @mistreckless on 31.03.2017. !
 */

public class PostConverter {
    @Inject
    PostConverter(){}

    public Observable<List<Post>> convertPostsToAdapter(List<Post> posts, long uId) {
        return Observable.from(posts)
                .flatMap(post -> convertPostToAdapter(post, uId))
                .buffer(MAX_POST_LIMIT)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<Post> convertPostToAdapter(Post post, long uId) {
        return Observable.zip(likeFilter(post.getLikes(), uId), willcomeFilter(post.getWillcomes(), uId), reportFilter(post.getReports(), uId),
                (isLiked, isWillcomed, isReported) -> {
                    post.setLiked(isLiked);
                    post.setWillcomed(isWillcomed);
                    post.setReported(isReported);
                    return post;
                });
    }

    private Observable<Boolean> likeFilter(Map<String, Like> likes, long uId) {
        return Observable.just(likes)
                .map(likes1 -> {
                    if (likes1 != null)
                        for (Like like :
                                likes1.values())
                            if (like.getAuthor().getuId() == uId)
                                return true;

                    return false;
                });
    }

    private Observable<Boolean> willcomeFilter(Map<String, Willcome> willcomeMap, long uId) {
        return Observable.just(willcomeMap)
                .map(willcomes -> {
                    if (willcomes != null)
                        for (Willcome willcome :
                                willcomes.values())
                            if (willcome.getAuthor().getuId() == uId)
                                return true;
                    return false;
                });
    }

    private Observable<Boolean> reportFilter(Map<String, Report> reportMap, long uId) {
        return Observable.just(reportMap)
                .map(reports -> {
                    if (reports != null)
                        for (Report report :
                                reports.values())
                            if (report.getAuthor().getuId() == uId)
                                return true;
                    return false;
                });
    }
}
