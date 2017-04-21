package com.welcome.studio.welcome.ui.module.main;

import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.Author;

import java.util.List;

public interface MainRouter {
    void navigateFromRegistry();

    void navigateToWall();

    void navigateToRegistry();

    void navigateToProfile(User user, boolean isAddedToBackStack);

    void navigateToPhoto();

    void navigateToComment(Post post);

    void navigateToPostWatcher(Post post, boolean isRealTime);

    void navigateToAuthorWatcher(List<Author> authors);

    void navigateToSearch();
}
