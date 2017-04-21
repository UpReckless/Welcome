package com.welcome.studio.welcome.ui.module.search;

import com.welcome.studio.welcome.model.interactor.SearchInteractor;
import com.welcome.studio.welcome.model.interactor.impl.SearchInteractorImpl;
import com.welcome.studio.welcome.model.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by @mistreckless on 17.04.2017. !
 */
@SearchScope
@Module
public class SearchModule {

    @SearchScope
    @Provides
    public SearchInteractor provideSearchInteractor(UserRepository userRepository){
        return new SearchInteractorImpl(userRepository);
    }
}
