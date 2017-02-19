package com.welcome.studio.welcome.ui.registry.singup.first_screen;

import android.support.annotation.StringRes;

/**
 * Created by Royal on 06.01.2017.
 */

public interface SignUpView {

    void setEnableNextButton(Boolean enabled);

    void setHeaderName(CharSequence name);

    void finish();

    void prepareParams();

    void showToast(@StringRes int resId);

    void showToast(String message);

    void showProgressBarVisible();
}
