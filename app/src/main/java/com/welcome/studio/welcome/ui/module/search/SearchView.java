package com.welcome.studio.welcome.ui.module.search;

import com.welcome.studio.welcome.model.data.User;

import java.util.List;

/**
 * Created by @mistreckless on 12.01.2017. !
 */

public interface SearchView {
    void addUsers(List<User> users);

    void setUsers(List<User> users);

}
