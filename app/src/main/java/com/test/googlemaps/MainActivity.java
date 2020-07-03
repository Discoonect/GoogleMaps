package com.test.googlemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.test.googlemaps.adapter.RecyclerViewAdapter;
import com.test.googlemaps.model.Results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?language=ko&radius=20000&type=restaurant&key=AIzaSyB6MGXsA05A43EkIjfgv4RRh2zawJRjFPY";

    private LocationManager locationManager;
    private LocationListener locationListener;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    RequestQueue requestQueue;
    ArrayList<Results> resultsList = new ArrayList<>();

    String nextPageToken="";

    double lat;
    double lng;

    boolean isFirst = true;

    String pageToken= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("AAA",location.toString());
                lat = location.getLatitude();
                lng = location.getLongitude();

                if(isFirst) {
                    isFirst = false;
                    getNetworkData(lat, lng);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition())+1;
                int totalCount = recyclerView.getAdapter().getItemCount();

                if(lastPosition == totalCount){
                    Log.i("AAA","마지막");

                    if (nextPageToken.compareTo(pageToken)!=0) {
                        pageToken = nextPageToken;


                        addNetworkData(lat,lng);
                    }
                }
            }
        });



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                60000,   // 밀리세컨드,  1000 : 1초
                0,   // 미터   10m
                locationListener);
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 0){
            if(ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000,   // 밀리세컨드,  1000 : 1초
                    0,   // 미터   10m
                    locationListener);
        }
    }



    public void getNetworkData(double lat, double lng){

        String url = baseUrl+"&location="+lat+","+lng;

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("AAA",response.toString());
                                try {
                                    String nextpagetoken = response.getString("next_page_token");
                                    JSONArray jsonArray = response.getJSONArray("results");
                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String name = jsonObject.getString("name");
                                        String vicinity = jsonObject.getString("vicinity");
                                        JSONObject geometry = jsonObject.getJSONObject("geometry");
                                        JSONObject location = geometry.getJSONObject("location");

                                        double storeLat = location.getDouble("lat");
                                        double storeLng = location.getDouble("lng");

                                        Results results = new Results(name,vicinity,storeLat,storeLng);
                                        resultsList.add(results);
                                    }
                                    recyclerViewAdapter = new RecyclerViewAdapter(
                                            MainActivity.this,resultsList);
                                    recyclerView.setAdapter(recyclerViewAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
        requestQueue.add(jsonObjectRequest);
    }


    public void addNetworkData(double lat, double lng){

        String url = baseUrl+"&location="+lat+","+lng;

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("AAA",response.toString());
                                try {
                                    String nextpagetoken = response.getString("next_page_token");
                                    JSONArray jsonArray = response.getJSONArray("results");
                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String name = jsonObject.getString("name");
                                        String vicinity = jsonObject.getString("vicinity");
                                        JSONObject geometry = jsonObject.getJSONObject("geometry");
                                        JSONObject location = geometry.getJSONObject("location");

                                        double storeLat = location.getDouble("lat");
                                        double storeLng = location.getDouble("lng");

                                        Results results = new Results(name,vicinity,storeLat,storeLng);
                                        resultsList.add(results);
                                    }
                                    recyclerViewAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
        requestQueue.add(jsonObjectRequest);
    }


}