package com.example.zhaoxinwu.maraca;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LocationActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {
    private LocationManager mLocationManager;
    private TextView mWifiLatitudeTextView;
    private TextView mWifiLongitudeTextView;
    private TextView mWifiAccuracyTextView;
    private TextView mWifiAltitudeTextView;
    private TextView mGpsLatitudeTextView;
    private TextView mGpsLongitudeTextView;
    private TextView mGpsAccuracyTextView;
    private TextView mGpsAltitudeTextView;
    private static int WIFI = 0;
    private static int GPS = 1;
    private int mLocationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
        setContentView(R.layout.activity_location);
        mWifiLatitudeTextView = (TextView)findViewById(R.id.text_view_wifi_latitude_value);
        mWifiLongitudeTextView = (TextView)findViewById(R.id.text_view_wifi_longitude_value);
        mWifiAccuracyTextView = (TextView)findViewById(R.id.text_view_wifi_accuracy_value);
        mWifiAltitudeTextView = (TextView)findViewById(R.id.text_view_wifi_altitude_value);
        mGpsLatitudeTextView = (TextView)findViewById(R.id.text_view_gps_laltitude_value);
        mGpsLongitudeTextView = (TextView)findViewById(R.id.text_view_gps_longitude_value);
        mGpsAccuracyTextView = (TextView)findViewById(R.id.text_view_gps_accuracy_value);
        mGpsAltitudeTextView = (TextView)findViewById(R.id.text_view_gps_altitude_value);
        Button gpsButton = (Button)findViewById(R.id.button_gps);
        gpsButton.setOnClickListener(this);

        Button wifiButton = (Button)findViewById(R.id.button_wifi);
        wifiButton.setOnClickListener(this);

        mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    }
    @Override
    protected void onResume() {
        Log.d("Place Sample", "plog onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("PlaceSample", "plog onPause");
        if(mLocationManager != null) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }
            mLocationManager.removeUpdates(this);
        }
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("place sample", String.valueOf(location.getLatitude()));
        if(mLocationType == GPS) {
            mGpsLatitudeTextView.setText(String.valueOf(location.getLatitude()));
            mGpsLongitudeTextView.setText(String.valueOf(location.getLongitude()));
            mGpsAccuracyTextView.setText(String.valueOf(location.getAccuracy()));
            mGpsAltitudeTextView.setText(String.valueOf(location.getAltitude()));
        }
        else if(mLocationType == WIFI) {
            mWifiLatitudeTextView.setText(String.valueOf(location.getLatitude()));
            mWifiLongitudeTextView.setText(String.valueOf(location.getLongitude()));
            mWifiAccuracyTextView.setText(String.valueOf(location.getAccuracy()));
            mWifiAltitudeTextView.setText(String.valueOf(location.getAltitude()));
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch(status) {
            case LocationProvider.AVAILABLE:
                Log.v("Place Sample", "AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.v("Place Sample", "OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.v("Place Sample", "TEMPORARILY UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_gps) {
            mLocationType = GPS;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else if (v.getId() == R.id.button_wifi) {
            mLocationType = WIFI;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        }
    }
}