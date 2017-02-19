package com.welcome.studio.welcome.ui.wall;

import com.welcome.studio.welcome.model.data.Post;

import java.util.List;

/**
 * Created by @mistreckless on 12.01.2017. !
 */

public interface WallView {

    void setFabEnabled(Boolean enabled);

    void updateUi(List<Post> post);
}
