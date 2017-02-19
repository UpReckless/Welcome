package com.welcome.studio.welcome.model.repository;

import android.net.Uri;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.welcome.studio.welcome.model.data.Post;

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

    Observable<Boolean> setPostTags(DatabaseReference postRef, List<String> tags);
}
