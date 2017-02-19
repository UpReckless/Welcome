package com.welcome.studio.welcome.model.repository;

import android.location.Address;
import android.location.Location;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;

/**
 * Created by Royal on 13.02.2017. !
 */

public class LocationRepositoryImpl implements LocationRepository {
    private ReactiveLocationProvider locationProvider;

    @Inject
    public LocationRepositoryImpl(ReactiveLocationProvider locationProvider) {
        this.locationProvider = locationProvider;
    }

    @Override
    public Observable<List<Address>> reverseGeocodeLocation(Location location) {
        Log.e("LocRepo","thread "+Thread.currentThread());
        return locationProvider.getReverseGeocodeObservable(Locale.ENGLISH,
                location.getLatitude(),location.getLongitude(),1);
    }

    @Override
    public Observable<Location> getLastKnownLocation() {
        return locationProvider.getLastKnownLocation();
    }

    @Override
    public Observable<List<Address>> getGeocode(String name) {
        Log.e("getGeocode",name);
        return locationProvider.getGeocodeObservable(name,1);
    }

}
