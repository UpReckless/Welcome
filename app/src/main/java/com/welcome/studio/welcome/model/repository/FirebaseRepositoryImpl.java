package com.welcome.studio.welcome.model.repository;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kelvinapps.rxfirebase.DataSnapshotMapper;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseStorage;
import com.welcome.studio.welcome.model.data.CommentModel;
import com.welcome.studio.welcome.model.data.Like;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.Report;
import com.welcome.studio.welcome.model.data.Willcome;
import com.welcome.studio.welcome.util.Constance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by @mistreckless on 09.02.2017. !
 */

public class FirebaseRepositoryImpl implements FirebaseRepository {
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;

    @Inject
    public FirebaseRepositoryImpl(FirebaseStorage firebaseStorage, FirebaseDatabase firebaseDatabase) {
        this.firebaseStorage = firebaseStorage;
        this.firebaseDatabase = firebaseDatabase;
        this.firebaseDatabase.setPersistenceEnabled(true);
    }

    @Override
    public Observable<AuthResult> auth(String token) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authStateListener = firebaseAuth1 -> {
        };
        firebaseAuth.addAuthStateListener(authStateListener);
        return RxFirebaseAuth.signInWithCustomToken(firebaseAuth, token);
    }

    @Override
    public Observable<Uri> uploadImage(String path, long id) throws FileNotFoundException {
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(Constance.URL.FIREBASE_STORAGE);
        Uri uri = Uri.parse(path);
        InputStream is = new FileInputStream(new File(path));
        Log.e("upload path",uri.toString());
        return RxFirebaseStorage.putStream(storageReference.child(id + "/" + uri.getLastPathSegment()), is)
                .map(UploadTask.TaskSnapshot::getDownloadUrl)
                .doOnNext(uri1 -> {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    @Override
    public Observable<Uri> getDownloadUrl(String photoRef) {
        return RxFirebaseStorage.getDownloadUrl(firebaseStorage.getReferenceFromUrl(photoRef));
    }

    @Override
    public Observable<DatabaseReference> sharePost(Post post) {
        Log.e("sharepost",post.toString());
        DatabaseReference postRef = firebaseDatabase.getReference("posts").child(post.getCountry()).child(post.getCity()).push();
        post.setId(postRef.getKey());
        return Observable.just(postRef.setValue(post)).map(res -> postRef);
    }

    @Override
    public Observable<Boolean> setPostTags(String country, String city, String key, List<String> tags) {
        return Observable.just(setTags(firebaseDatabase.getReference(), country, city, key, tags));
    }

    @Override
    public Observable<List<Post>> getPosts(String country, String city, long time, int limit) {
        DatabaseReference postRef = firebaseDatabase.getReference("posts").child(country).child(city);
        return RxFirebaseDatabase.observeSingleValueEvent(time == 0 ? postRef.limitToLast(limit) :
                        postRef.orderByChild("time").endAt(time - 1).limitToLast(limit)
                , DataSnapshotMapper.listOf(Post.class));
    }

    @Override
    public Observable<RxFirebaseChildEvent<Post>> listenPosts(String country, String city) {
        return Observable.just(firebaseDatabase.getReference("posts").child(country).child(city))
                .switchMap(postsRef -> RxFirebaseDatabase.observeChildEvent(postsRef, DataSnapshotMapper.ofChildEvent(Post.class)));
    }

    @Override
    public Observable<Boolean> sendComment(Post post, CommentModel comment) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("comments").push();
        comment.setId(ref.getKey());
        return Observable.just(ref.setValue(comment)).map(Task::isSuccessful);
    }

    @Override
    public Observable<RxFirebaseChildEvent<CommentModel>> listenComments(Post post) {
        return Observable.just(firebaseDatabase.getReference("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("comments"))
                .switchMap(ref -> RxFirebaseDatabase.observeChildEvent(ref, DataSnapshotMapper.ofChildEvent(CommentModel.class)));
    }

    @Override
    public Observable<Boolean> incLikeCount(Post post, Like like) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("likes").push();
        like.setKey(ref.getKey());
        return Observable.just(ref.setValue(like)).map(task->true);
    }

    @Override
    public Observable<Boolean> decLikeCount(Post post, Like like) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("likes")
                .child(like.getKey());
        return Observable.just(ref.removeValue()).map(task->true);
    }

    @Override
    public Observable<Boolean> incWillcomeCount(Post post, Willcome willcome) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("willcomes").push();
        willcome.setKey(ref.getKey());
        return Observable.just(ref.setValue(willcome)).map(Task::isSuccessful);
    }

    @Override
    public Observable<Boolean> decWillcomeCount(Post post, Willcome willcome) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("willcomes")
                .child(willcome.getKey());
        return Observable.just(ref.removeValue()).map(Task::isSuccessful);
    }

    @Override
    public Observable<Boolean> incReportCount(Post post, Report report) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("reports").push();
        report.setKey(ref.getKey());
        return Observable.just(ref.setValue(report)).map(Task::isSuccessful);
    }

    @Override
    public Observable<Boolean> decReportCount(Post post, Report report) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("reports")
                .child(report.getKey());
        return Observable.just(ref.removeValue()).map(Task::isSuccessful);
    }

    @Override
    public Observable<Boolean> incLikeCount(Post post, CommentModel comment, Like like) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("comments")
                .child(comment.getId())
                .child("likes").push();
        like.setKey(ref.getKey());
        return Observable.just(ref.setValue(like)).map(Task::isSuccessful);
    }

    @Override
    public Observable<Boolean> decLikeCount(Post post, CommentModel comment, Like like) {
        DatabaseReference ref = firebaseDatabase.getReference()
                .child("posts")
                .child(post.getCountry())
                .child(post.getCity())
                .child(post.getId())
                .child("comments")
                .child(comment.getId())
                .child("likes")
                .child(like.getKey());
        return Observable.just(ref.removeValue()).map(Task::isSuccessful);
    }


    private boolean setTags(DatabaseReference databaseReference, String country, String city, String key, List<String> tags) {
        for (String tag :
                tags)
            databaseReference.child("hashtags").child(country).child(city).child(tag.substring(1)).push().setValue(key);
        return true;
    }
}
