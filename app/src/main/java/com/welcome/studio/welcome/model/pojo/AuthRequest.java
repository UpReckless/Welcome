package com.welcome.studio.welcome.model.pojo;

/**
 * Created by Royal on 08.01.2017.
 */

public class AuthRequest {
    private double latitude;
    private double longitude;
    private String imei;
    public AuthRequest(){}

    public AuthRequest(double latitude, double longitude, String imei) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imei = imei;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
