package com.welcome.studio.welcome.model.entity;

/**
 * Created by Royal on 08.01.2017.
 */

public class AuthResponse {
    private String token;
    public AuthResponse(){}

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
