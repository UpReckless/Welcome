package com.welcome.studio.welcome.ui.module.comment;

import com.welcome.studio.welcome.model.data.CommentModel;

/**
 * Created by @mistreckless on 28.02.2017. !
 */

public interface CommentAdapterListener {
    void likeClicked(CommentModel comment, int position);

    void likeCountClicked(CommentModel comment);

    void userProfileClicked(CommentModel comment);
}
