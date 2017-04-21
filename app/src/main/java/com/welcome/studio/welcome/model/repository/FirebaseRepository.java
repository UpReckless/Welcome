package com.welcome.studio.welcome.model.repository;

import android.net.Uri;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.welcome.studio.welcome.model.data.CommentModel;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.Willcome;

import java.io.FileNotFoundException;
import java.util.List;

import rx.Observable;

/**
 * Created by @mistreckless on 09.02.2017. !
 */
public interface FirebaseRepository {

    Observable<AuthResult> auth(String token);

    Observable<Uri> uploadImage(String path, long id) throws FileNotFoundException;

    Observable<Uri> getDownloadUrl(String photoRef);

    Observable<DatabaseReference> sharePost(Post post);

    Observable<Boolean> setPostTags(String country, String city, String key, List<String> tags);

    Observable<List<Post>> getPosts(String country, String city, long time, int limit);

    Observable<RxFirebaseChildEvent<Post>> listenPosts(String country, String city);

    Observable<Post> listenPost(String country, String city, String key);

    Observable<Boolean> sendComment(Post post, CommentModel comment);

    Observable<RxFirebaseChildEvent<CommentModel>> listenComments(Post post);

    Observable<Boolean> incLikeCount(Post post, Like like);

    Observable<Boolean> decLikeCount(Post post, Like like);

    Observable<Boolean> incWillcomeCount(Post post, Willcome willcome);

    Observable<Boolean> decWillcomeCount(Post post, Willcome willcome);

    Observable<Boolean> incReportCount(Post post, Report report);

    Observable<Boolean> decReportCount(Post post, Report report);

    Observable<Boolean> incLikeCount(Post post, CommentModel comment, Like like);

    Observable<Boolean> decLikeCount(Post post, CommentModel commentModel, Like like);
}
