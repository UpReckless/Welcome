package com.welcome.studio.welcome.ui.module.comment;

import dagger.Subcomponent;

/**
 * Created by @mistreckless on 28.02.2017. !
 */
@CommentScope
@Subcomponent(modules = CommentModule.class)
public interface CommentComponent {
    void inject(Comment comment);
}
