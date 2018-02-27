package com.sportcitizen.sportcitizen.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.sportcitizen.sportcitizen.activities.CreateChallengeActivity;
import com.sportcitizen.sportcitizen.models.LocationModel;

import java.util.List;
import java.util.Locale;

/**
 * Created by Axel Drozdzynski on 27/02/2018.
 */

public class MyLocationListener implements LocationListener {
    private LocationModel _locationModel;
    private Context _context;

    public MyLocationListener(LocationModel locationModel, Context context) {
        _locationModel = locationModel;
        _context = context;
    }
    @Override
    public void onLocationChanged(Location location) {
        double latitude, longitude;
        Geocoder geocoder = new Geocoder(_context, Locale.getDefault());
        List<Address> addresses;

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String cityName = addresses.get(0).getAddressLine(1);
            _locationModel.city = cityName;
            Log.d("CITY", cityName);
        }
        catch (Exception e) {
            Log.d("Exception", e.toString());
        }
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
