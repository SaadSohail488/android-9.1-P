package com.example.task91p;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.task91p.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize Places API with your API key
        Places.initialize(getApplicationContext(), "AIzaSyDjiHt-Vhd2JHoBobFFu6FXC2MFSdZNZGQ");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up the AutocompleteSupportFragment
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // Get the selected place's LatLng
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    // Clear previous marker (if any)
                    if (marker != null) {
                        marker.remove();
                    }
                    // Adding new marker at the selected location
                    marker = mMap.addMarker(new MarkerOptions().position(latLng));
                    // Move camera to the selected location
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                }
            }

            @Override
            public void onError(@NonNull Status status) {
                // Handle error
            }
        });

// Restrict autocomplete results to a specific country
        autocompleteFragment.setCountries(Arrays.asList("US"));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Remove previous marker
                if (marker != null) {
                    marker.remove();
                }

                // Add new marker
                marker = mMap.addMarker(new MarkerOptions().position(latLng));

                // Move camera to the selected location
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Pass the selected location back to the calling activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("latitude", latLng.latitude);
                resultIntent.putExtra("longitude", latLng.longitude);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
