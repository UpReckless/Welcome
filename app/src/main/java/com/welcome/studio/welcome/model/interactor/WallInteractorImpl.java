package com.welcome.studio.welcome.model.interactor;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.app.RxBus;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.model.entity.PostEvent;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.PostRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.ui.wall.PagingListener;
import com.welcome.studio.welcome.ui.wall.PostAdapter;

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
    private RxBus bus;
    private RecyclerView recyclerView;

    @Inject
    public WallInteractorImpl(FirebaseRepository firebaseRepository, UserRepository userRepository, PostRepository postRepository, RxBus bus) {
        this.firebaseRepository = firebaseRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.bus = bus;
    }

    @Override
    public Observable<Boolean> controlFab() {
        return userRepository.checkServerConnection()
                .onErrorReturn(throwable -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Subscription controlPosts(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        User user = userRepository.getUserCache();
        PagingListener pagingListener = getPagingListener(recyclerView, user);
        return getScrollObservable(recyclerView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMap(offset -> getPagingObservable(pagingListener, pagingListener.nextPage(offset), 0, offset, RETRY_COUNT))
                .subscribe(posts -> {
                    postRepository.savePosts(posts);
                    Collections.reverse(posts);
                    bus.sendPostList(posts);
                });
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
                .buffer(MAX_POST_LIMIT)

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
    public Observable<Boolean> sharePost(Post post) {
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
        User user = getUserCache();
        return userRepository.checkServerConnection()
                .switchMap(connect -> connect ? post.isLiked() ? firebaseRepository.decLikeCount(post, findUserLike(user, post)) :
                        firebaseRepository.incLikeCount(post, generateLike(user)) : Observable.just(false))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> false)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> changeWillcomeCount(Post post) {
        User user = getUserCache();
        return userRepository.checkServerConnection()
                .switchMap(connect -> connect ? post.isWillcomed() ? firebaseRepository.decWillcomeCount(post, findUserWillcome(user, post)) :
                        firebaseRepository.incWillcomeCount(post, generateWillcome(user)) : Observable.just(false))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> false)
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<Boolean> changeReportCount(Post post) {
        User user = getUserCache();
        return userRepository.checkServerConnection()
                .switchMap(connect -> connect ? post.isReported() ? firebaseRepository.decReportCount(post, findUserReport(user, post)) :
                        firebaseRepository.incReportCount(post, generateReport(user)) : Observable.just(false))
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> false)
                .observeOn(AndroidSchedulers.mainThread());
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
                    if (adapter.getItemAtPosition(i).getId() == null && adapter.getItemAtPosition(i).getAuthor().getuId() == getUserCache().getId()) {
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

    private Like findUserLike(User user, Post post) {
        if (post.getLikes() != null)
            for (Like like :
                    post.getLikes().values()) {
                if (user.getId() == like.getAuthor().getuId())
                    return like;
            }
        return null;
    }

    private Like generateLike(User user) {
        Like like = new Like();
        like.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return like;
    }

    private Willcome findUserWillcome(User user, Post post) {
        if (post.getWillcomes() != null)
            for (Willcome willcome :
                    post.getWillcomes().values()) {
                if (user.getId() == willcome.getAuthor().getuId())
                    return willcome;
            }
        return null;
    }

    private Willcome generateWillcome(User user) {
        Willcome willcome = new Willcome();
        willcome.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return willcome;
    }

    private Report findUserReport(User user, Post post) {
        if (post.getReports() != null)
            for (Report report :
                    post.getReports().values()) {
                if (user.getId() == report.getAuthor().getuId())
                    return report;
            }
        return null;
    }

    private Report generateReport(User user) {
        Report report = new Report();
        report.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return report;
    }

}
