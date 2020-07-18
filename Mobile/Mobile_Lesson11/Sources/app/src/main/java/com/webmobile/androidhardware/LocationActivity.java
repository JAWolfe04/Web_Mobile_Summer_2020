package com.webmobile.androidhardware;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;
        Geocoder geocoder = new Geocoder(this);
        StringBuilder userAddress = new StringBuilder();

        final LocationManager userCurrentLocation = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener userCurrentLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
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
        LatLng userCurrentLocationCorodinates = null;
        double latitute = 0, longitude = 0;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //show message or ask permissions from the user.
            return;
        }

        //Getting the current location of the user.
        userCurrentLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                userCurrentLocationListener);
        Location location = userCurrentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitute = location.getLatitude();
        longitude = location.getLongitude();
        userCurrentLocationCorodinates = new LatLng(latitute, longitude);

        //Getting the address of the user based on latitude and longitude.
        try {
            List<Address> addresses = geocoder.getFromLocation(latitute, longitude, 1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            userAddress.append(knownName + ", " + city + ", " + state + ", " + country + " " + postalCode).append("\t");
            Toast.makeText(this, " " + userAddress, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Setting our image as the marker icon.
        map.addMarker(new MarkerOptions().position(userCurrentLocationCorodinates)
                .title("Your current address.").snippet(userAddress.toString())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_maps)));
        //Setting the zoom level of the map.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocationCorodinates, 7));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}