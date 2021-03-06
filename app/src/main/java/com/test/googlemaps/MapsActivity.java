package com.test.googlemaps;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.googlemaps.adapter.RecyclerViewAdapter;
import com.test.googlemaps.model.Results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Results> resultsList = new ArrayList<>();

    double lat;
    double lng;
    String name;
    String addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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


