package com.welcome.studio.welcome.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Royal on 08.01.2017.
 */

public class LocationService extends Service implements LocationListener {
    private Context context;
    private Location location;
    private boolean isNetworkEnabled;

    public LocationService(Context context){
        this.context=context;
    }

    public Location getLocation() throws IllegalAccessException {
        LocationManager locationManager=(LocationManager)context.getSystemService(LOCATION_SERVICE);
        isNetworkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isNetworkEnabled) throw new IllegalAccessException("Network is disabled!");
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
