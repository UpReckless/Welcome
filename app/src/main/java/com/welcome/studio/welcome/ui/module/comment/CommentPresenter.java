package com.welcome.studio.welcome.ui.module.comment;

import android.util.Log;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.CommentModel;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.model.interactor.CommentInteractor;
import com.welcome.studio.welcome.model.interactor.MainInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.module.main.MainRouter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by @mistreckless on 28.02.2017. !
 */

class CommentPresenter extends BasePresenter<CommentView, MainRouter> {
    private Post post;
    private String text = "";
    private User user;

    private CommentInteractor commentInteractor;
    private MainInteractor mainInteractor;
    private Subscription listenSubscription;

    @Inject
    CommentPresenter(CommentInteractor commentInteractor, MainInteractor mainInteractor) {
        this.commentInteractor = commentInteractor;
        this.mainInteractor = mainInteractor;
        user = this.commentInteractor.getUserCache();
    }

    @Override
    public void onStart() {
        commentInteractor.checkServerConnection()
                .subscribe(success -> {
                    if (success)
                        listenSubscription = commentInteractor.listenComments(post)
                                .subscribe(this::realTimeProvider, throwable -> Log.e("CommentPres", throwable.getMessage()));
                    else getView().showToast("Network connection failed");
                });
    }

    @Override
    public void onStop() {
        if (listenSubscription != null)
            listenSubscription.unsubscribe();
    }

    void likeCLicked(CommentModel comment, int position) {
        commentInteractor.changeLikeCount(comment, post)
                .subscribe(success -> {
                    if (!success) getView().showToast("Internet connection failed");
                });
    }

    void likeCountClicked(CommentModel comment) {
        if (comment.getLikes() != null) {
            List<Author> authors = new ArrayList<>(comment.getLikes().size());
            for (Like like :
                    comment.getLikes().values())
                authors.add(like.getAuthor());
            getRouter().navigateToAuthorWatcher(authors);
        }
    }

    void userProfileClicked(CommentModel comment) {
        mainInteractor.getUser(comment.getAuthor())
                .subscribe(user -> getRouter().navigateToProfile(user, mainInteractor.getUserCache().getId() != user.getId()));
    }

    void sendComment() {
        commentInteractor.sendComment(post, text)
                .subscribe(success -> {
                    getView().hideKeyboard();
                });
    }

    void controlSendView(Observable<CharSequence> text) {
        commentInteractor.controlSendView(text)
                .subscribe(getView()::setSendView);
    }

    void controlTextChanges(Observable<CharSequence> charSequenceObservable) {
        charSequenceObservable.subscribe(charSequence -> text = charSequence.toString());
    }

    void loadComments(Post post) {
        this.post = post;
        if (post.getComments() != null) {
            List<CommentModel> comments = new ArrayList<>(post.getComments().values());
            Collections.sort(comments, (o1, o2) -> o1.getTime() < o2.getTime() ? -1 : o2.getTime() < o1.getTime() ? 1 : 0);

            for (int i = 0; i < comments.size(); i++) {
                CommentModel comment = comments.get(i);
                if (comment.getLikes() != null)
                    for (Like like :
                            comment.getLikes().values()) {
                        if (user.getId() == like.getAuthor().getuId()) {
                            comment.setLiked(true);
                            comments.set(i, comment);
                        }
                    }

            }
            getView().addComments(comments);
            getView().refresh();
        }
    }

    private void realTimeProvider(RxFirebaseChildEvent<CommentModel> commentRxEvent) {
        switch (commentRxEvent.getEventType()) {
            case ADDED:
                //add new comment
                if (post.getComments() != null) {
                    if (!post.getComments().containsKey(commentRxEvent.getKey())) {
                        post.getComments().put(commentRxEvent.getKey(), commentRxEvent.getValue());
                        getView().addComment(commentRxEvent.getValue());
                        getView().refresh();
                    }
                } else {
                    post.setComments(new HashMap<>());
                    post.getComments().put(commentRxEvent.getKey(), commentRxEvent.getValue());
                    getView().addComment(commentRxEvent.getValue());
                    getView().refresh();
                }
                Log.e("RxCommentAdded", commentRxEvent.getValue().toString());
                break;
            case CHANGED:
                //updateView
                Log.e("RxCommentChanged", commentRxEvent.getValue().toString());
                convertCommentToAdapter(commentRxEvent.getValue(), user.getId())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getView()::updateCommentEvent);
                break;
            case MOVED:
                //do nothing
                Log.e("RxCommentMoved", commentRxEvent.getValue().toString());
                break;
            case REMOVED:
                //do nothing
                Log.e("RxCommentRemoved", commentRxEvent.getValue().toString());
                break;
            default:
                throw new RuntimeException("Cannot define type " + commentRxEvent.getEventType().name());
        }
    }

    private Observable<CommentModel> convertCommentToAdapter(CommentModel commentModel, long uId) {
        return likeFilter(commentModel.getLikes(), uId)
                .map(isLiked -> {
                    commentModel.setLiked(isLiked);
                    return commentModel;
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

    void destroy() {
        Injector.getInstance().clearCommentComponent();
    }
}
