package com.welcome.studio.welcome.util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Royal on 30.10.2016.
 */

public class AuthService {
    public static Task<AuthResult> auth(String token){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authStateListener= firebaseAuth1 -> {

        };
        firebaseAuth.addAuthStateListener(authStateListener);
        return firebaseAuth.signInWithCustomToken(token);
    }
}
