package com.welcome.studio.welcome.ui.registry;

import javax.inject.Inject;

/**
 * Created by Royal on 06.01.2017.
 */

public class SignUpPresenterImpl implements SignUpPresenter {
    private SignUpView view;

    @Inject
    public SignUpPresenterImpl(SignUpView view){
        this.view=view;
        view.getComponent().inject(this);
    }
}
