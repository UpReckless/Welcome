package com.welcome.studio.welcome.model.interactor.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.UserResponse;
import com.welcome.studio.welcome.model.interactor.SearchInteractor;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.ui.adapter.SearchUserAdapter;
import com.welcome.studio.welcome.ui.module.search.PaginListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import static com.welcome.studio.welcome.util.Constance.ConstHolder.EMPTY_LIST_COUNT;
import static com.welcome.studio.welcome.util.Constance.ConstHolder.MAX_USER_LIMIT;
import static com.welcome.studio.welcome.util.Constance.ConstHolder.RETRY_COUNT;

/**
 * Created by @mistreckless on 19.04.2017. !
 */

public class SearchInteractorImpl implements SearchInteractor {
    private UserRepository userRepository;

    @Inject
    public SearchInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Observable<List<User>> controlUsers(RecyclerView recyclerView) {
        PaginListener paginListener = getPaginListener(recyclerView);
        return getScrollObservable(recyclerView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMap(offset -> getPagingObservable(paginListener, paginListener.nextPage(offset), 0, offset, RETRY_COUNT))
                .flatMap(this::generateUsersFromResponse)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<User>> searchQueryUsers(String searchLine) {
        return userRepository.getUsers(searchLine)
                .switchMap(this::generateUsersFromResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private PaginListener getPaginListener(RecyclerView recyclerView) {
        return offset -> {
            User latestUser = ((SearchUserAdapter) recyclerView.getAdapter()).getUserAtPosition(offset - 1);
            return userRepository.getUsers(latestUser != null ? offset - 1 : 0)
                    .doOnNext(val-> Log.e("userSize",val.size()+""))
                    .subscribeOn(Schedulers.io());
        };
    }

    private Observable<Integer> getScrollObservable(RecyclerView recyclerView) {
        return Observable.create(subscriber -> {
            final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (!subscriber.isUnsubscribed()) {
                        int position = getLastVisiblePosition(recyclerView);
                        int updatePosition = recyclerView.getAdapter().getItemCount() - 1 - (MAX_USER_LIMIT / 2);
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

    private Observable<List<UserResponse>> getPagingObservable(PaginListener listener, Observable<List<UserResponse>> observable, int numberOfAttemptToRetry, int offset, int retryCount) {
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

    private Observable<List<User>> generateUsersFromResponse(List<UserResponse> userResponses) {
        if (userResponses.size() > 0)
            return Observable.from(userResponses)
                    .map(this::generateUserFromResponse)
                    .toList()
                    .subscribeOn(Schedulers.computation());
        else return Observable.just(new ArrayList<User>());
    }

    private User generateUserFromResponse(UserResponse userResponse) {
        User user = new User();
        user.setId(userResponse.getId());
        user.setNickname(userResponse.getNickname());
        user.setRating(userResponse.getRating());
        user.setCountry(userResponse.getCountry());
        user.setCity(userResponse.getCity());
        return user;
    }
}
