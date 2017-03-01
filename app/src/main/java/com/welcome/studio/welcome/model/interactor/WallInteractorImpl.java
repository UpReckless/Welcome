package com.welcome.studio.welcome.model.interactor;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.model.data.Author;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.ui.wall.PagingListener;
import com.welcome.studio.welcome.ui.wall.PostAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Royal on 11.02.2017. !
 */

public class WallInteractorImpl implements WallInteractor {
    private static final int LIMIT = 20;
    private static final int EMPTY_LIST_COUNT = 0;
    private FirebaseRepository firebaseRepository;
    private UserRepository userRepository;

    @Inject
    public WallInteractorImpl(FirebaseRepository firebaseRepository, UserRepository userRepository) {
        this.firebaseRepository = firebaseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Observable<Boolean> controlFab() {
        return Observable.just(true);
    }

    @Override
    public Observable<List<Post>> controlPosts(RecyclerView recyclerView) {
        User user = userRepository.getUserCache();
        PagingListener pagingListener = getPagingListener(recyclerView, user);
        return getScrollObservable(recyclerView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMap(offset -> getPagingObservable(pagingListener, pagingListener.nextPage(offset), 0, offset, 3));
    }


    @Override
    public Observable<RxFirebaseChildEvent<Post>> listenPosts(int limit) {
        return Observable.just(userRepository.getUserCache())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(user -> firebaseRepository.listenPosts(user.getCountry(), user.getCity(), limit))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> incLikeCount(Post post) {
        User user = getUserCache();
        Like like = new Like();
        like.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        return firebaseRepository.incLikeCount(post, like);
    }

    @Override
    public Observable<Boolean> decLikeCount(Post post) {
        return Observable.just(getUserCache())
                .map(user -> findUserLike(user, post))
                .switchMap(like -> firebaseRepository.decLikeCount(post, like));
    }

    @Override
    public Observable<Boolean> incWillcomeCount(Post post) {
        User user=getUserCache();
        Willcome willcome=new Willcome();
        willcome.setAuthor(new Author(user.getId(),user.getNickname(),user.getRating(),user.getPhotoRef()));
        return firebaseRepository.incWillcomeCount(post,willcome);
    }

    @Override
    public Observable<Boolean> decWillcomeCount(Post post) {
        return Observable.just(getUserCache())
                .map(user -> findUserWillcome(user,post))
                .switchMap(willcome->firebaseRepository.decWillcomeCount(post,willcome));
    }

    @Override
    public Observable<Boolean> incReportCount(Post post) {
        User user=getUserCache();
        Report report=new Report();
        report.setAuthor(new Author(user.getId(),user.getNickname(),user.getRating(),user.getPhotoRef()));
        return firebaseRepository.incReportCount(post,report);
    }

    @Override
    public Observable<Boolean> decReportCount(Post post) {
        return Observable.just(getUserCache())
                .map(user->findUserReport(user,post))
                .switchMap(report -> firebaseRepository.decReportCount(post,report));
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
                        int updatePosition = recyclerView.getAdapter().getItemCount() - 1 - (LIMIT / 2);
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
            return firebaseRepository.getPosts(user.getCountry(), user.getCity(), latestPost == null ? 0 : latestPost.getTime(), LIMIT);
        };
    }

    private Like findUserLike(User user, Post post) {
        for (Like like :
                post.getLikes().values()) {
            if (user.getId() == like.getAuthor().getuId())
                return like;
        }
        return null;
    }

    private Willcome findUserWillcome(User user, Post post){
        for (Willcome willcome:
                post.getWillcomes().values()){
            if (user.getId()==willcome.getAuthor().getuId())
                return willcome;
        }
        return null;
    }

    private Report findUserReport(User user, Post post){
        for (Report report :
                post.getReports().values()) {
            if (user.getId()==report.getAuthor().getuId())
                return report;
        }
        return null;
    }

}
