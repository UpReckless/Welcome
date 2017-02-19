package com.welcome.studio.welcome.model.data;

/**
 * Created by @mistreckless on 08.01.2017. !
 */

public class AuthRequest {
    private String imei;
    private long id;
    private String city;
    private String country;

    public AuthRequest(){}

    public AuthRequest(String city,String country, String imei, long id) {
        this.imei = imei;
        this.id=id;
        this.city=city;
        this.country=country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
