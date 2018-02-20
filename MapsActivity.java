package com.example.usamanaseer.googlemap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback { //

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

      // ** this LatLngBounds is covering whole europe
//    private LatLngBounds france = new LatLngBounds(new LatLng(41.2654, -4.04074), new LatLng(52.394, 7.07037));

    // ** this LatLngBounds is covering france and spain
    private LatLngBounds france = new LatLngBounds(new LatLng(41.2654, -4.7900), new LatLng(46.4136, 6.1084));

    //public LatLngBounds (LatLng southwest, LatLng northeast)
    //LatLngBounds FRANCE = "N:52.394; E:7.07037; S:41.2654; W:-4.04074"
    //The bounds conceptually includes all points where:
    // 1)the latitude is in the range [northeast.latitude, southwest.latitude];
    // 2)the longitude is in the range [southwest.longtitude, northeast.longitude] if southwest.longtitude ≤ northeast.longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); //comment

        //FragmentActivity myContext=(FragmentActivity) getApplicationContext();//faltu line for removing error

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//        Ask.on(this)
//                .forPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .go();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
        }


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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2000, mLocationListener);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
//            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(loc).title("Your Location"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 20.0f));
//            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//
//            //** code to restrict google map to only france **
//            mMap.setLatLngBoundsForCameraTarget(france);


//
//            // ** restrict google map to one country but shows other country map in blur **
//            final LatLng royan_france = new LatLng(45.62846,-1.0281);
//            final LatLng megeve_france = new LatLng(45.85233, 6.61286);
//
//            // Create a LatLngBounds that includes France
//            LatLngBounds france = new LatLngBounds(royan_france, megeve_france);
//
//            // Set the camera to the greatest possible zoom level that includes the bounds
//            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(france, 0));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(france.getCenter(), 10));



              // ** zoom in and zoom out camera setting code **
//            // Move the camera instantly to Sydney with a zoom of 15.
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(royan_france, 15));
//
//            // Zoom in, animating the camera.
//            mMap.animateCamera(CameraUpdateFactory.zoomIn());
//
//            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//
//            // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(megeve_france)      // Sets the center of the map to Mountain View
//                    .zoom(17)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
//                    .build();                   // Creates a CameraPosition from the builder
//            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not inst  LatLng sydney = new LatLng(-34, 151);alled on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       // LatLng sydney = new LatLng(24.863663, 67.075118);
        // Add a marker in Sydney and move the camera

        mMap.setMyLocationEnabled(true);

        //** code to restrict google map only for france **
        mMap.setLatLngBoundsForCameraTarget(france);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
        }

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

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
//                            LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
//                            mMap.addMarker(new MarkerOptions().position(loc).title("Your Location"));
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 16.0f));


                            // ** restrict google map to one country but shows other country map in blur **
//                            final LatLng royan_france = new LatLng(45.62846,-1.0281);
//                            final LatLng megeve_france = new LatLng(45.85233, 6.61286);
//
//                            // Create a LatLngBounds that includes France
//                            LatLngBounds france = new LatLngBounds(royan_france, megeve_france);
//
//                            // Set the camera to the greatest possible zoom level that includes the bounds
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(france, 0));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(france.getCenter(), 17));
//                       //   LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
//                       //   mMap.addMarker(new MarkerOptions().position(loc).title("Your Location"));


//
//                            //** code to restrict google map only for france **
//                            mMap.setLatLngBoundsForCameraTarget(france);


                        }
                    }
                });

    }

    // *Code for on press of mobile back button go to previous class*

    @Override
    public void onBackPressed() {


        Intent i = new Intent(MapsActivity.this,NearestPlacesActivity.class);
        startActivity(i);
        finish();

    }



}
