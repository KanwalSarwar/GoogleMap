package com.example.usamanaseer.googlemap;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Path;
import android.icu.text.RelativeDateTimeFormatter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.renderscript.Double2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;



public class RouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123 ;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    String s,s1;
    LatLng l1,l2;
    TextView text_distance,text_time,showDistance,showTime;
    ArrayList<LatLng> markerPoints;
    Route route;
    Leg leg;
    ArrayList<LatLng> sectionPositionList;
    String distance,duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

//        ShowDistanceDuration = (TextView) findViewById(R.id.show_distance_time);

        text_distance = (TextView) findViewById(R.id.distance_text);
        text_time = (TextView) findViewById(R.id.time_text);
        showDistance = (TextView) findViewById(R.id.show_distance);
        showTime = (TextView) findViewById(R.id.show_time);
        markerPoints = new ArrayList<LatLng>();

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



//        Ask.on(this)
//                .forPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .go();



        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                2000, mLocationListener);






        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title("Your Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 20.0f));
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

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



    //*getDirectionsUrl() and String downloadUrl() are extra codes*

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;


        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }



    //** Actual Implemented Code for finding route starting from here **

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }


        s = getIntent().getStringExtra("Latlng1");
        String[] words={};
        String[] words1 ={};
        if(s.contains(","))
        {
            words =  s.split(",");
            l1 = new LatLng(Double.parseDouble(words[0]), Double.parseDouble(words[1]));
        }
        else
        {
            s ="";
        }

        s1 = getIntent().getStringExtra("Latlng2");
        if(s1.contains(",")) {
            words1 = s1.split(",");
            l2 = new LatLng(Double.parseDouble(words1[0]), Double.parseDouble(words1[1]));
        }
        else
        {
            s1 = "";
        }

        if (l1!=null && l2!=null) {
            GoogleDirection.withServerKey("AIzaSyDSzhyMIiCSOABINtycTXG5eUzgotInDeo")
                    .from(l1)
                    .to(l2)
                    .avoid(AvoidType.FERRIES)
                    .avoid(AvoidType.HIGHWAYS)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {

                            if (direction.isOK()) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l1, 15.0f));

//
                                route = direction.getRouteList().get(0);
                                leg = route.getLegList().get(0);
                                sectionPositionList = leg.getSectionPoint();
                                Info distanceInfo = leg.getDistance();
                                Info durationInfo = leg.getDuration();
                                distance = distanceInfo.getText();
                                duration = durationInfo.getText();

//                              ShowDistanceDuration.setText("Distance:"+distance + ", Duration:"+duration);
                                showDistance.setText(distance);
                                showTime.setText(duration);

//                                // Already two locations
//                                if(markerPoints.size()>2){
//                                    markerPoints.clear();
//                                    mMap.clear();
//                                }
//
//                                // Adding new item to the ArrayList
//                                markerPoints.add(l1);
//                                markerPoints.add(l2);

                                // Creating MarkerOptions

                                // Setting the position of the marker



                                 //   mMap.addMarker(new MarkerOptions().position(l1).title("Origin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                                    MarkerOptions optionsorigin = new MarkerOptions();

                                    optionsorigin.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                    optionsorigin.position(l1).title("Origin");
                                    mMap.addMarker(optionsorigin);


                                 //   mMap.addMarker(new MarkerOptions().position(l2).title("Destination").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                    MarkerOptions optionsdesti = new MarkerOptions();
                                    optionsdesti.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                    optionsdesti.position(l2).title("Destination");
                                    mMap.addMarker(optionsdesti);


                                // *All BELOW CODE AND DirectionsJSONParser.java Class REGARDING DURATION AND TIME CALCULATION IS USELESS*


                                // Checks, whether start and end locations are captured
                                if(markerPoints.size() >= 2){
                                    LatLng origin = markerPoints.get(0);
                                    LatLng dest = markerPoints.get(1);

                                    // Getting URL to the Google Directions API
                                    String url = getDirectionsUrl(origin, dest);

                                    DownloadTask downloadTask = new DownloadTask();

                                    // Start downloading json data from Google Directions API
                                    downloadTask.execute(url);
                                }






                                List<LatLng> ll2 = PolyUtil.decode(direction.getRouteList().get(0).getOverviewPolyline().getRawPointList());
                                mMap.addPolyline(new PolylineOptions().addAll(ll2).color(Color.BLACK).width(12));





                            } else {
                                Toast.makeText(getApplicationContext(),"No Direction Found",Toast.LENGTH_SHORT).show();
                                // Do something
                            }
                        }
                        @Override
                        public void onDirectionFailure(Throwable t) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l1, 17.0f));
                            Toast.makeText(getApplicationContext(),"Direction Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else
        {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(loc).title("Your Location"));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 15.0f));
                            }
                        }
                    });
        }


    }

    //** Actual Implemented Code for finding route ending from here **



    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }


    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
//            PolylineOptions lineOptions = null;
//            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";



            if(result.size()<1){
                Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }


            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
//                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){	// Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

//                // Adding all the points in the route to LineOptions
//                lineOptions.addAll(points);
//                lineOptions.width(15);
//                lineOptions.color(Color.RED);

            }

//            ShowDistanceDuration.setText("Distance:"+distance + ", Duration:"+duration);
            // Drawing polyline in the Google Map for the i-th route
//            mMap.addPolyline(lineOptions);
        }
    }



    public void onBackPressed() {


        Intent i = new Intent(RouteActivity.this,EntryActivity.class);
        startActivity(i);
        finish();

    }



}
