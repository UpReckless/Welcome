package com.welcome.studio.welcome.model.repository;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseStorage;
import com.welcome.studio.welcome.model.data.Post;

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
    private StorageReference storageReference;

    @Inject
    public FirebaseRepositoryImpl(FirebaseStorage firebaseStorage, StorageReference storageReference) {
        this.firebaseStorage = firebaseStorage;
        this.storageReference = storageReference;
    }

    @Override
    public Observable<AuthResult> auth(String token) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authStateListener = firebaseAuth1 -> {
        };
        firebaseAuth.addAuthStateListener(authStateListener);
        return RxFirebaseAuth.signInWithCustomToken(firebaseAuth,token);
    }

    @Override
    public Observable<Uri> uploadImage(String path, long id) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File(path));
        Uri uri=Uri.parse(path);
        return RxFirebaseStorage.putStream(storageReference.child(id+"/"+uri.getLastPathSegment()),is)
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
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference postRef=database.getReference("posts").child(post.getCountry()).child(post.getCity()).push();
        Log.e(postRef.getKey(),postRef.getParent().toString());
        return Observable.just(postRef.setValue(post)).map(res->postRef);
    }

    @Override
    public Observable<Boolean> setPostTags(DatabaseReference postRef, List<String> tags) {
        return Observable.just(setTags(postRef.getParent(),postRef.getKey(),tags));
    }
    private boolean setTags(DatabaseReference parent, String key, List<String> tags){
        for (String tag :
                tags)
            parent.child(tag.substring(1)).setValue(key);
        return true;
    }
}
