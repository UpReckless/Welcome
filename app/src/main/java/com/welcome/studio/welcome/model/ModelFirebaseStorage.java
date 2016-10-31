package com.welcome.studio.welcome.model;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.welcome.studio.welcome.util.Constance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Royal on 28.10.2016.
 */
public class ModelFirebaseStorage {

    private static ModelFirebaseStorage instance;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public static ModelFirebaseStorage getInstance() {
        if (instance==null)instance=new ModelFirebaseStorage();
        return instance;
    }

    private ModelFirebaseStorage() {
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReferenceFromUrl(Constance.URL.FIREBASE_STORAGE);
    }

    public UploadTask uploadImage(String path, String id) throws FileNotFoundException {
        InputStream is=new FileInputStream(new File(path));
        Uri uri=Uri.parse(path);
        StorageReference myStorage=storageReference.child(id+"/"+uri.getLastPathSegment());
        UploadTask uploadTask=myStorage.putStream(is);
        uploadTask.addOnCompleteListener(complete -> {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return uploadTask;
    }
}
