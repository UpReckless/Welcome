package com.welcome.studio.welcome.model.data;

import java.io.Serializable;

/**
 * Created by @mistreckless on 15.02.2017. !
 */

public class Report implements Serializable{
    private long id;
    private long uId;

    public Report(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }
}
