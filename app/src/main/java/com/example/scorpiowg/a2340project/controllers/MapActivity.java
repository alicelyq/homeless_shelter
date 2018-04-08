package com.example.scorpiowg.a2340project.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nancy on 4/2/18.
 */

@SuppressWarnings("ALL")
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MapActivity";
    private final Map<Marker, Shelter> linker = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        @SuppressWarnings("ChainedMethodCall") SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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

    @SuppressWarnings("FeatureEnvy")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.setOnMarkerClickListener(this);

        //noinspection ChainedMethodCall
        for (Shelter s: Model.getInstance().getCurrentShelterList()) {
            LatLng c = new LatLng(Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
            @SuppressWarnings("ChainedMethodCall") MarkerOptions m = new MarkerOptions().position(c)
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
        //noinspection ChainedMethodCall
        Model.getInstance().setCurrentShelter(clicked);
        final Intent shelterInfo = new Intent(this, ShelterInfoActivity.class);
        shelterInfo.putExtra("shelterId", clicked.getShelterId());
        startActivity(shelterInfo);
        return true;
    }
}
