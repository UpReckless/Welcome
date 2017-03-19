package com.welcome.studio.welcome.model.entity;

/**
 * Created by @mistreckless on 07.11.2016. !
 */

public class ExceptionJSONInfo {
    private String message;
    public ExceptionJSONInfo(){}

    public ExceptionJSONInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
