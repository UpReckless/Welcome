package com.welcome.studio.welcome.ui.module.comment;

import com.welcome.studio.welcome.model.interactor.CommentInteractor;
import com.welcome.studio.welcome.model.interactor.impl.CommentInteractorImpl;
import com.welcome.studio.welcome.model.repository.FirebaseRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 28.02.2017. !
 */
@CommentScope
@Module
public class CommentModule {

    @CommentScope
    @Provides
    public CommentInteractor provideCommentInteractor(UserRepository userRepository, FirebaseRepository firebaseRepository){
        return new CommentInteractorImpl(userRepository,firebaseRepository);
    }
}
