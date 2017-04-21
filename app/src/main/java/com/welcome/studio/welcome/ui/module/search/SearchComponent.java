package com.welcome.studio.welcome.ui.module.search;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 17.04.2017. !
 */

@SearchScope
@Subcomponent(modules = SearchModule.class)
public interface SearchComponent {

    void inject(Search search);
}
