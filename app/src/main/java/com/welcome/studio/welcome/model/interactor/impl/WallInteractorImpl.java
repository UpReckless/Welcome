package com.welcome.studio.welcome.model.interactor.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.PostEvent;
import com.welcome.studio.welcome.model.interactor.PostInteractor;
import com.welcome.studio.welcome.model.interactor.WallInteractor;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.PostRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.ui.adapter.PostAdapter;
import com.welcome.studio.welcome.ui.module.wall.PagingListener;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import static com.welcome.studio.welcome.util.Constance.ConstHolder.EMPTY_LIST_COUNT;
import static com.welcome.studio.welcome.util.Constance.ConstHolder.MAX_POST_LIMIT;
import static com.welcome.studio.welcome.util.Constance.ConstHolder.RETRY_COUNT;

/**
 * Created by Royal on 11.02.2017. !
 */

public class WallInteractorImpl implements WallInteractor {
    private FirebaseRepository firebaseRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private PostInteractor postInteractor;
    private RxBus bus;
    private RecyclerView recyclerView;

    @Inject
    public WallInteractorImpl(FirebaseRepository firebaseRepository, UserRepository userRepository, PostRepository postRepository, RxBus bus, PostInteractor postInteractor) {
        this.firebaseRepository = firebaseRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.bus = bus;
        this.postInteractor = postInteractor;
    }

    @Override
    public Observable<Boolean> controlFab() {
        return userRepository.checkServerConnection()
                .onErrorReturn(throwable -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Post>> controlPosts(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        User user = userRepository.getUserCache();
        PagingListener pagingListener = getPagingListener(recyclerView, user);
        return getScrollObservable(recyclerView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMap(offset -> getPagingObservable(pagingListener, pagingListener.nextPage(offset), 0, offset, RETRY_COUNT))
                .map(posts -> {
                    postRepository.savePosts(posts);
                    Collections.reverse(posts);
                    return posts;
                }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Post>> getCachedPosts() {
        User user = getUserCache();
        long currentTime = System.currentTimeMillis();
        return Observable.just(postRepository.getAllPosts())
                .flatMap(postList -> postList.size() > 0 ? filterCachedPost(postList, user, currentTime)
                        : Observable.just(postList))
                .map(posts -> {
                    Collections.reverse(posts);
                    return posts;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Post>> filterCachedPost(List<Post> postList, User user, long currentTime) {
        return Observable.from(postList)
                .filter(post -> {
                    Log.e("filter cache posts", String.valueOf(new Date(currentTime)) + "  " + String.valueOf(new Date(post.getDeleteTime())));
                    return user.getId() != post.getAuthor().getuId() || currentTime < post.getDeleteTime();
                })
                .toList()
                .subscribeOn(Schedulers.computation());
    }


    @Override
    public Subscription listenPosts() {
        return Observable.just(userRepository.getUserCache())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(user -> firebaseRepository.listenPosts(user.getCountry(), user.getCity()))
                .observeOn(Schedulers.computation())
                .subscribe(this::realtimeProvider);
    }

    @Override
    public Observable<Boolean> sharePost(Post post, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        try {
            return firebaseRepository.uploadImage(post.getContentPath(), post.getAuthor().getuId())
                    .doOnNext(uri -> post.setContentRef(String.valueOf(uri)))
                    .observeOn(Schedulers.io())
                    .flatMap(uri -> firebaseRepository.sharePost(post))
                    .flatMap(postReference -> firebaseRepository.setPostTags(post.getCountry(), post.getCity()
                            , postReference.getKey(), post.getTags()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Observable.just(false);
        }
    }

    @Override
    public Observable<Boolean> changeLikeCount(Post post) {
        return postInteractor.changeLikeCount(post);
    }

    @Override
    public Observable<Boolean> changeWillcomeCount(Post post) {
        return postInteractor.changeWillcomeCount(post);
    }


    @Override
    public Observable<Boolean> changeReportCount(Post post) {
        return postInteractor.changeReportCount(post);
    }


    @Override
    public User getUserCache() {
        return userRepository.getUserCache();
    }

    private Observable<List<Post>> getPagingObservable(PagingListener listener, Observable<List<Post>> observable, int numberOfAttemptToRetry, int offset, int retryCount) {
        return observable.onErrorResumeNext(throwable -> {
            // retry to load new data portion if error occurred
            if (numberOfAttemptToRetry < retryCount) {
                int attemptToRetryInc = numberOfAttemptToRetry + 1;
                return getPagingObservable(listener, listener.nextPage(offset), attemptToRetryInc, offset, retryCount);
            } else {
                return Observable.empty();
            }
        });
    }

    private Observable<Integer> getScrollObservable(RecyclerView recyclerView) {
        return Observable.create(subscriber -> {
            final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (!subscriber.isUnsubscribed()) {
                        int position = getLastVisiblePosition(recyclerView);
                        int updatePosition = recyclerView.getAdapter().getItemCount() - 1 - (MAX_POST_LIMIT / 2);
                        if (position >= updatePosition)
                            subscriber.onNext(recyclerView.getAdapter().getItemCount());

                    }
                }
            };
            recyclerView.addOnScrollListener(scrollListener);
            subscriber.add(Subscriptions.create(() -> recyclerView.removeOnScrollListener(scrollListener)));
            if (recyclerView.getAdapter().getItemCount() == EMPTY_LIST_COUNT) {
                int offset = recyclerView.getAdapter().getItemCount();
                subscriber.onNext(offset);
            }
        });
    }

    private int getLastVisiblePosition(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        return layoutManager.findLastVisibleItemPosition();
    }

    private PagingListener getPagingListener(RecyclerView recyclerView, User user) {
        return offset -> {
            Post latestPost = ((PostAdapter) recyclerView.getAdapter()).getItemAtPosition(offset - 1);
            return firebaseRepository.getPosts(user.getCountry(), user.getCity(), latestPost == null ? 0 : latestPost.getTime(), MAX_POST_LIMIT);
        };
    }

    private void realtimeProvider(RxFirebaseChildEvent<Post> postChildEvent) {
        if (recyclerView.getAdapter() == null)
            return;
        switch (postChildEvent.getEventType()) {
            case ADDED:
                //handle user post

                PostAdapter adapter = ((PostAdapter) recyclerView.getAdapter());
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    if (adapter.getItemAtPosition(i).getId().equals(postChildEvent.getValue().getId()) && adapter.getItemAtPosition(i).getAuthor().getuId() == getUserCache().getId()) {
                        //need save to db
                        postRepository.savePost(postChildEvent.getValue());
                        bus.sendPostEvent(new PostEvent(postChildEvent.getValue(), RxFirebaseChildEvent.EventType.ADDED));
                    }
                }

                break;
            case MOVED:
                //do nothing
                break;
            case CHANGED:
                //change & send event to change
                //need to update changes in db
                postRepository.update(postChildEvent.getValue());
                bus.sendPostEvent(new PostEvent(postChildEvent.getValue(), RxFirebaseChildEvent.EventType.CHANGED));
                break;
            case REMOVED:
                //remove & send event to remove
                if (getUserCache().getId() != postChildEvent.getValue().getAuthor().getuId()) {
                    //need to remove in db
                    postRepository.removePost(postChildEvent.getValue().getId());
                }
                bus.sendPostEvent(new PostEvent(postChildEvent.getValue(), RxFirebaseChildEvent.EventType.REMOVED));
                break;
        }
    }


}
