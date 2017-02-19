package com.welcome.studio.welcome.model.repository;

import android.location.Address;
import android.location.Location;

import java.util.List;

import rx.Observable;

/**
 * Created by Royal on 13.02.2017. !
 */
public interface LocationRepository {
    Observable<List<Address>> reverseGeocodeLocation(Location location);

    Observable<Location> getLastKnownLocation();

    Observable<List<Address>> getGeocode(String name);
}
