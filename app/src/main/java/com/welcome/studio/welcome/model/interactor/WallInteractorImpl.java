package com.welcome.studio.welcome.model.interactor;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.ui.wall.PagingListener;
import com.welcome.studio.welcome.ui.wall.PostAdapter;

import java.io.FileNotFoundException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
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

    @Inject
    public WallInteractorImpl(FirebaseRepository firebaseRepository, UserRepository userRepository) {
        this.firebaseRepository = firebaseRepository;
        this.userRepository = userRepository;
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
        User user = userRepository.getUserCache();
        PagingListener pagingListener = getPagingListener(recyclerView, user);
        return getScrollObservable(recyclerView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMap(offset -> getPagingObservable(pagingListener, pagingListener.nextPage(offset), 0, offset, RETRY_COUNT));
    }


    @Override
    public Observable<RxFirebaseChildEvent<Post>> listenPosts() {
        return Observable.just(userRepository.getUserCache())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(user -> firebaseRepository.listenPosts(user.getCountry(), user.getCity()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> sharePost(Post post) {
        try {
            return firebaseRepository.uploadImage(post.getContentPath(),post.getAuthor().getuId())
                    .doOnNext(uri -> post.setContentRef(String.valueOf(uri)))
                    .observeOn(Schedulers.io())
                    .flatMap(uri->firebaseRepository.sharePost(post))
                    .flatMap(postReference -> firebaseRepository.setPostTags(post.getCountry(),post.getCity()
                            ,postReference.getKey(),post.getTags()))
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
                .switchMap(connect -> connect ? post.isWillcomed() ? firebaseRepository.decReportCount(post, findUserReport(user, post)) :
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
