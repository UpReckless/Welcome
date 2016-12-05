package com.welcome.studio.welcome.model;

import com.welcome.studio.welcome.model.entity.User;

import rx.Observable;

/**
 * Created by Royal on 27.11.2016.
 */

public interface ModelDb {
    Observable<User> getCurrentUser();
}
