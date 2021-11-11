package com.example.lifecycleawarecomponent26082021;

import static android.content.Context.LOCATION_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class MyLocationListener implements DefaultLifecycleObserver {
    private OnCallBackLocation onCallBackLocation;
    private LocationManager locationManager;
    private Lifecycle lifecycle;
    private Context context;

    public MyLocationListener(Context context, Lifecycle lifecycle, OnCallBackLocation onCallBackLocation) {
        this.onCallBackLocation = onCallBackLocation;
        this.lifecycle = lifecycle;
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        Log.d("BBB","onPause");
        locationManager.removeUpdates(locationListener);
    }

    @SuppressLint("MissingPermission")
    public void enable() {
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.INITIALIZED)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0F, locationListener);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0F, locationListener);
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (onCallBackLocation != null){
                onCallBackLocation.onChangeLocation(location);
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

}
