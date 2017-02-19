package com.welcome.studio.welcome.ui.registry.singup.last_screen;

/**
 * Created by Royal on 09.02.2017.
 */
public interface NextStepView {
    void setHeaderText(String text);

    void setMainPhoto(String path);


    void sendIntentToGallery();

    void finish();

    void showToast(String s);
}
