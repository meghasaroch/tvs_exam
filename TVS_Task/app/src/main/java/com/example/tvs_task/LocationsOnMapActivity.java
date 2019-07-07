package com.example.tvs_task;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationsOnMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<User> all_users_org;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
       all_users_org =(ArrayList<User>) bundle.getSerializable("values");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<all_users_org.size();i++)
                {
                    if(Geocoder.isPresent()){
                        try {
                            String location = all_users_org.get(i).location;
                            Geocoder gc = new Geocoder(LocationsOnMapActivity.this);
                            List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects

                            List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                            for(Address a : addresses){
                                if(a.hasLatitude() && a.hasLongitude()){
                                   final LatLng loc = new LatLng(a.getLatitude(), a.getLongitude());

                                    final int finalI = i;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mMap.addMarker(new MarkerOptions().position(loc).title(all_users_org.get(finalI).location));

                                        }
                                    });

                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }).start();




    }
}
