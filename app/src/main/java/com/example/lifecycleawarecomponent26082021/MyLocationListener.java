package com.example.lifecycleawarecomponent26082021;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;



public class MyLocationListener implements DefaultLifecycleObserver {
    private boolean enable;
    private OnCallBackLocation onCallBackLocation;
    private LocationManager locationManager;

    public MyLocationListener(Context context, OnCallBackLocation location){
        onCallBackLocation = location;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }


    @Override
    public void onResume(LifecycleOwner owner) {

    }

    @Override
    public void onPause(LifecycleOwner owner) {

    }

}
