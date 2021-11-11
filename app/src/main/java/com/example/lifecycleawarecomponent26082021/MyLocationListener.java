package com.example.lifecycleawarecomponent26082021;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class MyLocationListener implements DefaultLifecycleObserver {
    private boolean enable;
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
        if (enable){
            enable = false;
            locationManager.removeUpdates(locationListener);
        }
    }

    public void enable() {
        enable = true;
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1f, locationListener);
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (onCallBackLocation != null){
                onCallBackLocation.onChangeLocation(location);
            }
        }
    };

}
