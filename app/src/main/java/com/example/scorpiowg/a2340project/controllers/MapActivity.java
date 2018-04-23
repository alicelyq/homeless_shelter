package com.example.scorpiowg.a2340project.controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.scorpiowg.a2340project.R;
import com.example.scorpiowg.a2340project.model.Model;
import com.example.scorpiowg.a2340project.model.Shelter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nancy on 4/2/18.
 */


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    //    private static final String TAG = "MapActivity";
    private final Map<Marker, Shelter> linker = new HashMap<>();
    final Model modelInstance = Model.getInstance();

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double curLongitude;
    private double curLatitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //get the location service
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //request the location update thru location manager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //get the latitude and longitude from the location
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                curLatitude = latitude;
                curLongitude = longitude;
                //get the location name from latitude and longitude
                Geocoder geocoder = new Geocoder(getApplicationContext());
                LatLng latLng = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.setMaxZoomPreference(50);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

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
        });
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        Button b = findViewById(R.id.back);
        final Intent listPage = new Intent(this, ShelterListActivity.class);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPage.putExtra("filter", "0");
                startActivity(listPage);

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //googleMap.setOnMarkerClickListener(this);

        LatLng currentLocation = new LatLng(33.7774, -84.3973);
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));


        for (Shelter s: modelInstance.getCurrentShelterList()) {
            LatLng c = new LatLng(Double.parseDouble(s.getLatitude()),
                    Double.parseDouble(s.getLongitude()));
            MarkerOptions m = new MarkerOptions().position(c)
                    .title(s.getName());
            Marker marker = googleMap.addMarker(m);
            linker.put(marker, s);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(c));
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Log.d("debug", "marker clicked");
        Shelter clicked = linker.get(marker);
        modelInstance.setCurrentShelter(clicked);
        final Intent shelterInfo = new Intent(this, ShelterInfoActivity.class);
        shelterInfo.putExtra("shelterId", clicked.getShelterId());
        startActivity(shelterInfo);
        return true;
    }
}