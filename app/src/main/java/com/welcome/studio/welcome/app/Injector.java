package com.welcome.studio.welcome.app;

import com.welcome.studio.welcome.squarecamera_mock.PhotoComponent;
import com.welcome.studio.welcome.squarecamera_mock.PhotoModule;
import com.welcome.studio.welcome.ui.module.comment.CommentComponent;
import com.welcome.studio.welcome.ui.module.comment.CommentModule;
import com.welcome.studio.welcome.ui.module.main.MainComponent;
import com.welcome.studio.welcome.ui.module.main.MainModule;
import com.welcome.studio.welcome.ui.module.profile.ProfileComponent;
import com.welcome.studio.welcome.ui.module.profile.ProfileModule;
import com.welcome.studio.welcome.ui.module.registry.RegistryComponent;
import com.welcome.studio.welcome.ui.module.registry.RegistryModule;
import com.welcome.studio.welcome.ui.module.search.SearchComponent;
import com.welcome.studio.welcome.ui.module.search.SearchModule;
import com.welcome.studio.welcome.ui.module.wall.WallComponent;
import com.welcome.studio.welcome.ui.module.wall.WallModule;
import com.welcome.studio.welcome.ui.module.watchers.author.AuthorWatcherComponent;
import com.welcome.studio.welcome.ui.module.watchers.author.AuthorWatcherModule;
import com.welcome.studio.welcome.ui.module.watchers.post.PostWatcherComponent;
import com.welcome.studio.welcome.ui.module.watchers.post.PostWatcherModule;


public class Injector {
    private static Injector ourInstance = new Injector();
    private MainComponent mainComponent;
    private RegistryComponent registryComponent;
    private ProfileComponent profileComponent;
    private WallComponent wallComponent;
    private PhotoComponent photoComponent;
    private CommentComponent commentComponent;
    private PostWatcherComponent postWatcherComponent;
    private AuthorWatcherComponent authorWatcherComponent;
    private SearchComponent searchComponent;

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

    public PostWatcherComponent plus(PostWatcherModule postWatcherModule) {
        if (postWatcherComponent == null)
            postWatcherComponent = mainComponent.plus(postWatcherModule);
        return postWatcherComponent;
    }

    public void clearWatcherComponent() {
        postWatcherComponent = null;
    }

    public AuthorWatcherComponent plus(AuthorWatcherModule authorWatcherModule) {
        if (authorWatcherComponent == null)
            authorWatcherComponent = mainComponent.plus(authorWatcherModule);
        return authorWatcherComponent;
    }

    public void clearAuthorWatcherComponent() {
        authorWatcherComponent = null;
    }

    public SearchComponent plus(SearchModule searchModule){
        if (searchComponent==null)
            searchComponent=mainComponent.plus(searchModule);
        return searchComponent;
    }

    public void clearSearchComponent(){
        searchComponent=null;
    }
}
