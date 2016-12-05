package com.welcome.studio.welcome.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Royal on 30.11.2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstStartScope {
}
