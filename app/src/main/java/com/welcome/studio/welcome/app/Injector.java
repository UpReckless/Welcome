package com.welcome.studio.welcome.app;

import com.welcome.studio.welcome.ui.comment.CommentComponent;
import com.welcome.studio.welcome.ui.comment.CommentModule;
import com.welcome.studio.welcome.ui.main.MainComponent;
import com.welcome.studio.welcome.ui.main.MainModule;
import com.welcome.studio.welcome.squarecamera_mock.PhotoComponent;
import com.welcome.studio.welcome.squarecamera_mock.PhotoModule;
import com.welcome.studio.welcome.ui.profile.ProfileComponent;
import com.welcome.studio.welcome.ui.profile.ProfileModule;
import com.welcome.studio.welcome.ui.registry.RegistryComponent;
import com.welcome.studio.welcome.ui.registry.RegistryModule;
import com.welcome.studio.welcome.ui.wall.WallComponent;
import com.welcome.studio.welcome.ui.wall.WallModule;


public class Injector {
    private static Injector ourInstance = new Injector();
    private MainComponent mainComponent;
    private RegistryComponent registryComponent;
    private ProfileComponent profileComponent;
    private WallComponent wallComponent;
    private PhotoComponent photoComponent;
    private CommentComponent commentComponent;

    public static Injector getInstance() {
        return ourInstance;
    }

    private Injector() {
    }

    public MainComponent plus(MainModule mainModule) {
        if (mainComponent == null) mainComponent = App.getComponent().plus(mainModule);
        return mainComponent;
    }

    public void clearMainComponent() {
        mainComponent = null;
    }

    public RegistryComponent plus(RegistryModule registryModule) {
        if (registryComponent == null) registryComponent = mainComponent.plus(registryModule);
        return registryComponent;
    }

    public void clearRegistryComponent() {
        registryComponent = null;
    }

    public ProfileComponent plus(ProfileModule profileModule) {
        if (profileComponent == null) profileComponent = mainComponent.plus(profileModule);
        return profileComponent;
    }

    public void clearProfileComponent() {
        profileComponent = null;
    }


    public WallComponent plus(WallModule wallModule) {
        if (wallComponent == null) wallComponent = mainComponent.plus(wallModule);
        return wallComponent;
    }

    public void clearWallComponent() {
        wallComponent = null;
    }

    public PhotoComponent plus(PhotoModule photoModule) {
        if (photoComponent == null) photoComponent = mainComponent.plus(photoModule);
        return photoComponent;
    }

    public CommentComponent plus(CommentModule commentModule) {
        if (commentComponent == null) commentComponent = mainComponent.plus(commentModule);
        return commentComponent;
    }

    public void clearCommentComponent() {
        commentComponent = null;
    }
}
