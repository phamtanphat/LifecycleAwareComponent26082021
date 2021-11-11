package com.example.lifecycleawarecomponent26082021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MyLocationListener locationListener;
    int REQUEST_CODE_LOCATION = 123;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_LOCATION

            );
        }else{
            locationListener = new MyLocationListener(this, getLifecycle(), new OnCallBackLocation() {
                @Override
                public void onChangeLocation(Location location) {
                    Log.d("BBB","Lat : " + location.getLatitude() + " , Lon : " +  location.getLongitude());
                    textView.setText("Lat : " + location.getLatitude() + " , Lon : " +  location.getLongitude());
                }
            });
            getLifecycle().addObserver(locationListener);
            locationListener.enable();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_LOCATION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                locationListener = new MyLocationListener(this, getLifecycle(), new OnCallBackLocation() {
                    @Override
                    public void onChangeLocation(Location location) {
                        Log.d("BBB", location.getLatitude() + " , " + location.getLongitude());
                    }
                });
                locationListener.enable();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}