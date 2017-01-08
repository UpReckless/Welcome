package com.welcome.studio.welcome.model.pojo;

/**
 * Created by Royal on 08.01.2017.
 */

public class AuthResponse {
    private String token;
    private String city;
    public AuthResponse(){}

    public AuthResponse(String token, String city) {
        this.token = token;
        this.city = city;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
