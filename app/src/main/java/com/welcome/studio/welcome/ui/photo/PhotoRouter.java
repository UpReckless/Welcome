package com.welcome.studio.welcome.ui.photo;

import com.welcome.studio.welcome.model.data.Post;

/**
 * Created by Royal on 11.02.2017. !
 */

public interface PhotoRouter {
    void navigateToPreviewScreen();

    void navigateToCustomPhotoSettingsScreen(Post post);

    void navigateFilterScreen(String picturePath);

    void navigateToWall();

}
