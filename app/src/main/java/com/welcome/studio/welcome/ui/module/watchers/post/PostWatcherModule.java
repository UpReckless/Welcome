package com.welcome.studio.welcome.ui.module.watchers.post;

import com.welcome.studio.welcome.model.interactor.PostInteractor;
import com.welcome.studio.welcome.model.interactor.PostWatcherInteractor;
import com.welcome.studio.welcome.model.interactor.impl.PostWatcherInteractorImpl;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.PostRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 06.04.2017. !
 */

@PostWatcherScope
@Module
public class PostWatcherModule {

    @PostWatcherScope
    @Provides
    public PostWatcherInteractor providePostWatcherInteractor(UserRepository userRepository, FirebaseRepository firebaseRepository,
                                                              PostRepository postRepository, PostInteractor postInteractor) {
        return new PostWatcherInteractorImpl(userRepository, firebaseRepository, postRepository, postInteractor);
    }
}
