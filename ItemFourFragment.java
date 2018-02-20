package com.example.usamanaseer.googlemap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;



public class ItemFourFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123 ;
    EditText e1, e2;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_E1 = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_E2 = 2;
    private FusedLocationProviderClient mFusedLocationClient;
    LatLng l1;
    Button currentlocation, route, nearestplaces, destination;
    Dialog dialog;
    private static final String DEFAULT = "";
    TextView mName, mAddress;
    int PLACE_PICKER_REQUEST = 3;

    public static ItemThreeFragment newInstance() {
        ItemThreeFragment fragment = new ItemThreeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) getContext(), new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                                /*    String format = "geo:24.8618842,67.0750093?q=" + lat + "," + lng + "( Location title)";

                                    Uri uri = Uri.parse(format);


                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);*/



                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    try {
                        startActivityForResult(builder.build((Activity) getContext()), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }

//                            Intent i = new Intent(EntryActivity.this, Destination_Result.class);
//                            i.putExtra("Name", mName.getText().toString());
//                            i.putExtra("Address", mAddress.getText().toString());
//                            startActivity(i);
//                            finish();

                                 /*  AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                                            .build();

                                    try {
                                        Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                                        .setFilter(typeFilter).build(EntryActivity.this);
                                        startActivity(intent);
                                    } catch (GooglePlayServicesRepairableException e) {
                                        e.printStackTrace();
                                    } catch (GooglePlayServicesNotAvailableException e) {
                                        e.printStackTrace();
                                    }*/
                }
            }


        });


    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final android.location.Location location) {
            //your code here
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

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

    //*Code For Place Autocomplete Result*

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_E1) {
//            if (resultCode == RESULT_OK) {
//
//                Place place = PlaceAutocomplete.getPlace(this, data);
//                l1 = place.getLatLng();
//                e1.setText(l1.latitude + "," + l1.longitude);
//
//                Log.i("Kanwal", "Place: " + place.getLatLng());
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(this, data);
//                // TODO: Handle the error.
//                Log.i("Kanwal", status.getStatusMessage());
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//
//        else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_E2) {
//            if (resultCode == RESULT_OK) {
//
//                Place place = PlaceAutocomplete.getPlace(this, data);
//                l1 = place.getLatLng();
//                e2.setText(l1.latitude + "," + l1.longitude);
//
//                Log.i("Kanwal", "Place: " + place.getLatLng());
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(this, data);
//                // TODO: Handle the error.
//                Log.i("Kanwal", status.getStatusMessage());
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }

        //*Code For Place Picker Result*

        if (requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(getContext(), data);
                CharSequence name = place.getName();
                CharSequence address = place.getAddress();
                CharSequence phonenumber = place.getPhoneNumber();


//                mName.setText(name);
//                mAddress.setText(address);


                Intent i = new Intent(getContext(), Destination_Result.class);
                i.putExtra("placename", name.toString());
                i.putExtra("placeaddress", address.toString());
                i.putExtra("phonenumber", phonenumber.toString());
                startActivity(i);



                Log.i("Kanwal", "Place: " + place.getName() + place.getAddress());

            } else if (resultCode == PlacePicker.RESULT_ERROR){
                Status status = PlacePicker.getStatus(getContext(), data);
                Log.i("Kanwal", status.getStatusMessage());
            }
            else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_destination__result, container, false);
    }
}
