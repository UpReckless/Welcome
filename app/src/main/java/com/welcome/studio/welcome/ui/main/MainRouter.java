package com.welcome.studio.welcome.ui.main;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;

public interface MainRouter {
    void navigateFromRegistry();

    void navigateToWall();

    void navigateToRegistry();

    void navigateToProfile(User user);

    void navigateToPhoto();

    void navigateToComment(Post post);

    void navigateToPostWatcher(Post post);
}
