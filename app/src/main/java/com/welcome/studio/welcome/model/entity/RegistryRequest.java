package com.welcome.studio.welcome.model.entity;

/**
 * Created by @mistreckless on 06.03.2017. !
 */

public class RegistryRequest {
    private String name;
    private String imei;

    public RegistryRequest(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
