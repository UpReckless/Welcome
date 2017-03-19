package com.welcome.studio.welcome.ui.comment;

import android.util.Log;

import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.model.data.CommentModel;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.interactor.CommentInteractor;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.main.MainRouter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

/**
 * Created by @mistreckless on 28.02.2017. !
 */

class CommentPresenter extends BasePresenter<CommentView, MainRouter> {
    private Post post;
    private String text = "";

    private CommentInteractor commentInteractor;
    private Subscription listenSubscription;

    @Inject
    CommentPresenter(CommentInteractor commentInteractor) {
        this.commentInteractor = commentInteractor;
    }

    @Override
    public void onStart() {
        commentInteractor.checkServerConnection()
                .subscribe(success->{
                    if (success)
                        listenSubscription = commentInteractor.listenComments(post)
                                .subscribe(this::realTimeProvider, throwable -> Log.e("CommentPres",throwable.getMessage()));
                    else getView().showToast("Network connection failed");
                });

        if (post.getComments() != null) {
            List<CommentModel> comments = new ArrayList<>(post.getComments().values());
            Collections.sort(comments, (o1, o2) -> o1.getTime() < o2.getTime() ? -1 : o2.getTime() < o1.getTime() ? 1 : 0);

            User user = commentInteractor.getUserCache();
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

    @Override
    public void onStop() {
        if (listenSubscription != null)
            listenSubscription.unsubscribe();
    }

    void setPost(Post post) {
        this.post = post;
    }

    void likeCLicked(CommentModel comment, int position) {
        if (comment.isLiked())
            commentInteractor.decLikeCount(comment, post)
                    .subscribe(success -> {
                        if (success){
                            comment.setLiked(false);
                            getView().updateCommentView(comment,position);
                        }
                        else getView().showToast("Internet connection failed");
                    });
        else commentInteractor.incLikeCount(comment, post)
                .subscribe(success -> {
                    if (success){
                        comment.setLiked(true);
                        getView().updateCommentView(comment,position);
                    }
                    else getView().showToast("Internet connection failed");
                });
    }

    void likeCountClicked(CommentModel comment) {

    }

    void userThumbClicked(CommentModel comment) {

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
                getView().updateCommentEvent(commentRxEvent.getValue());
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

    void controlTextChanges(Observable<CharSequence> charSequenceObservable) {
        charSequenceObservable.subscribe(charSequence -> text = charSequence.toString());
    }
}
