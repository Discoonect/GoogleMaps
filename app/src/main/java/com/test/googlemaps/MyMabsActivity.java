package com.test.googlemaps;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.googlemaps.model.Results;

import java.util.ArrayList;

public class MyMabsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double lat;
    double lng;

    ArrayList<Results> resultsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mabs);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        lat=  i.getDoubleExtra("lat",37.554794);
        lng = i.getDoubleExtra("lng",126.854810);

        resultsList = (ArrayList<Results>) i.getSerializableExtra("resultsList");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng me = new LatLng(lat, lng);

        for(Results results : resultsList){
            MarkerOptions options = new MarkerOptions().position(new LatLng(results.getStoreLat(),results.getStoreLng()))
                    .title(results.getName()).snippet(results.getVicinity());
            mMap.addMarker(options);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me,17));
    }
}