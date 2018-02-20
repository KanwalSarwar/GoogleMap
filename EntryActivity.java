package com.example.usamanaseer.googlemap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

public class EntryActivity extends BaseActivity{ //AppCompatActivity

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123 ;
    EditText e1, e2;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_E1 = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_E2 = 2;
    private FusedLocationProviderClient mFusedLocationClient;
    LatLng l1,l2;
  //  ImageButton currentlocation, route, nearestplaces, destination;
    ImageView currentlocation, route, nearestplaces, destination, weather_option, currency_option, text_translation_option, image_converter_option;
    Dialog dialog, exitdialog;
    private static final String DEFAULT = "";
    TextView mName, mAddress;
    int PLACE_PICKER_REQUEST = 3;

    Button start_button;
    Context mContext;
    String appPackageName;
    TextView share_app, more_apps, rate_us, exit, cancel;
    ImageView more_apps_icon, rate_us_icon;
    View v1;
    Locale locale;
    private AdView mAdView;

    int PLACE_PICKER_REQUEST_E1 = 1;
    int PLACE_PICKER_REQUEST_E2 = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyAppTheme);
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
        String pine = sharedPreferences.getString("language", DEFAULT);
        String languageToLoad = pine;
        Locale locale = new Locale(languageToLoad);//Set Selected Locale
        Locale.setDefault(locale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = locale;//set config locale as selected locale
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());


        //setContentView(R.layout.activity_entry);

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.activity_entry, frameLayout);

        /**
         * Setting title and itemChecked
         */
        //mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        // ** Code line to hide the title name of App **
//        getSupportActionBar().hide();

        // ** Banner ad code **
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

//        //**Code Line for button animation (fade)**
//
//        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

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

//        currentlocation = (ImageButton) findViewById(R.id.b_currentlocation);
//        route = (ImageButton) findViewById(R.id.b_route);
//        nearestplaces = (ImageButton) findViewById(R.id.btn_nearestplaces);
//        destination = (ImageButton) findViewById(R.id.btn_destination);


        currentlocation = (ImageView) findViewById(R.id.imageView1);
        route = (ImageView) findViewById(R.id.imageView4);
        nearestplaces = (ImageView) findViewById(R.id.imageView5);
        destination = (ImageView) findViewById(R.id.imageView2);
        weather_option = (ImageView)findViewById(R.id.imageView3);
        currency_option = (ImageView)findViewById(R.id.imageView8);
        text_translation_option = (ImageView)findViewById(R.id.imageView6);
        image_converter_option = (ImageView)findViewById(R.id.imageView7);

//        mName = (TextView) findViewById(R.id.dummy_text);
//        mAddress = (TextView) findViewById(R.id.dummy_text2);



        currentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryActivity.this, MapsActivity.class);
                startActivity(i);
                finish();

//                //**Code Line for button animation (fade)**
//                v.startAnimation(buttonClick);
            }
        });


        weather_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(EntryActivity.this, WeatherActivity.class);
                startActivity(i);
                finish();

            }
        });


        currency_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(EntryActivity.this, CurrencyConverterActivity.class); //Main
                startActivity(i);
                finish();

            }
        });


        text_translation_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(EntryActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });


        image_converter_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(EntryActivity.this, Main2Activity.class);
                startActivity(i);
                finish();

            }
        });


        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                //**Code Line for button animation (fade)**
//                v.startAnimation(buttonClick);

                final View v1 = getLayoutInflater().inflate(R.layout.dialog_custom, null);

                dialog = new Dialog(EntryActivity.this);
                dialog.setContentView(R.layout.dialog_custom);
                e1 = (EditText) dialog.findViewById(R.id.et_Startingpoint);
                e1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // ** PlaceAutocomplete Code for entering origin **
//                        try {
//
//                            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                                    .setCountry("FR")
//                                    .build();
//
//                            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                                            .setFilter(typeFilter)
//                                            .build(EntryActivity.this);
//
//                            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_E1);
//
//                        }
//                        catch (GooglePlayServicesRepairableException e) {
//                            // TODO: Handle the error.
//                        }
//                        catch (GooglePlayServicesNotAvailableException e) {
//                            // TODO: Handle the error.
//                        }



                        // ** PlacePicker Code for entering origin **
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                        try {
                            startActivityForResult(builder.build(EntryActivity.this), PLACE_PICKER_REQUEST_E1);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }

                    }
                });


                e2 = (EditText) dialog.findViewById(R.id.et_destinationpoint);
                e2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // ** PlaceAutocomplete Code for entering destination **
//                        try {
//
//                            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                                    .setCountry("FR")
//                                    .build();
//
//                            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                                            .setFilter(typeFilter)
//                                            .build(EntryActivity.this);
//
//                            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_E2);
//
//                        }
//                        catch (GooglePlayServicesRepairableException e) {
//                            // TODO: Handle the error.
//                        }
//                        catch (GooglePlayServicesNotAvailableException e) {
//                            // TODO: Handle the error.
//                        }



                        // ** PlacePicker Code for entering destination **
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                        try {
                            startActivityForResult(builder.build(EntryActivity.this), PLACE_PICKER_REQUEST_E2);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }



                    }
                });
              final Button b1 = (Button) dialog.findViewById(R.id.b_ok);
                Button b2 = (Button) dialog.findViewById(R.id.b_cancel);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(EntryActivity.this, RouteActivity.class);

//                        i.putExtra("Latlng1", e1.getText().toString());
//                        i.putExtra("Latlng2", e2.getText().toString());

                        if (l1 == null && l2 == null || l1 == null || l2 == null) {
                            b1.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                        }
                        else {
                            b1.getBackground().setColorFilter(null);

                            i.putExtra("Latlng1", l1.latitude + "," + l1.longitude);
                            i.putExtra("Latlng2", l2.latitude + "," + l2.longitude);

                            startActivity(i);
                            finish();
                            //String s = e1.getText().toString() + " " + e2.getText().toString();
                            //Toast.makeText(EntryActivity.this,s,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });


        nearestplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntryActivity.this, NearestPlacesActivity.class);
                startActivity(i);
                finish();

//
//                //**Code Line for button animation (fade)**
//                v.startAnimation(buttonClick);
            }
        });


        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                //**Code Line for button animation (fade)**
//                v.startAnimation(buttonClick);

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

                if (ActivityCompat.checkSelfPermission(EntryActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EntryActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnSuccessListener(EntryActivity.this, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(Location location) {
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
                                startActivityForResult(builder.build(EntryActivity.this), PLACE_PICKER_REQUEST);
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
        });

    }


    //*Code for deleting the app from mobile cache (to clear last store values in app)*
    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        // ** PlaceAutocomplete Code for receiving origin result **
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_E1) {
//            if (resultCode == RESULT_OK) {
//
//                Place place = PlaceAutocomplete.getPlace(this, data);
//
//                l1 = place.getLatLng();
//
//                e1.setText(place.getName());
//
////              e1.setText(l1.latitude + "," + l1.longitude);
//
//                Log.i("Kanwal", "Place: " + place.getLatLng());
//
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
//        // ** PlaceAutocomplete Code for receiving destination result **
//        else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_E2) {
//            if (resultCode == RESULT_OK) {
//
//                Place place = PlaceAutocomplete.getPlace(this, data);
//
//                l2 = place.getLatLng();
//
//                e2.setText(place.getName());
//
////              e2.setText(l1.latitude + "," + l1.longitude);
//
//                Log.i("Kanwal", "Place: " + place.getLatLng());
//
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(this, data);
//                // TODO: Handle the error.
//                Log.i("Kanwal", status.getStatusMessage());
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }





        // ** PlacePicker Code for receiving origin result **
        if (requestCode == PLACE_PICKER_REQUEST_E1) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(this, data);

                l1 = place.getLatLng();

                e1.setText(place.getName());

//              e1.setText(l1.latitude + "," + l1.longitude);

                Log.i("Kanwal", "Place: " + place.getLatLng());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("Kanwal", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        // ** PlacePicker Code for receiving destination result **
        else if (requestCode == PLACE_PICKER_REQUEST_E2) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(this, data);

                l2 = place.getLatLng();

                e2.setText(place.getName());

//              e2.setText(l1.latitude + "," + l1.longitude);

                Log.i("Kanwal", "Place: " + place.getLatLng());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("Kanwal", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }



        //*Code For Place Picker Result*

           else if (requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(this, data);
                CharSequence name = place.getName();
                CharSequence address = place.getAddress();
                CharSequence phonenumber = place.getPhoneNumber();


//                mName.setText(name);
//                mAddress.setText(address);


                Intent i = new Intent(EntryActivity.this, Destination_Result.class);
                i.putExtra("placename", name.toString());
                i.putExtra("placeaddress", address.toString());
                i.putExtra("phonenumber", phonenumber.toString());
                startActivity(i);
                finish();


                Log.i("Kanwal", "Place: " + place.getName() + place.getAddress());

            } else if (resultCode == PlacePicker.RESULT_ERROR){
                Status status = PlacePicker.getStatus(this, data);
                Log.i("Kanwal", status.getStatusMessage());
            }
            else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


        }

    public void onBackPressed() {

        //*Code for alert-dialog box generation with YES, NO and RATE Us buttons*

//// TODO Auto-generated method stub
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        // builder.setCancelable(false);
//        builder.setTitle("Rate Us if u like this");
//        builder.setMessage("Do you want to Exit?");
//        builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//                Toast.makeText(Main_Page_Activity.this, "Yes i wanna exit", Toast.LENGTH_LONG).show();
//
//                finish();
//            }
//        });
//        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//                Toast.makeText(Main_Page_Activity.this, "i wanna stay on this page", Toast.LENGTH_LONG).show();
//                dialog.cancel();
//
//            }
//        });
//        builder.setNeutralButton("Rate",new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//
//              appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
//                }
//
//            }
//        });
//        AlertDialog alert=builder.create();
//        alert.show();
//        //super.onBackPressed();



        v1 = getLayoutInflater().inflate(R.layout.exit_dialog_box, null);

        exitdialog = new Dialog(EntryActivity.this);
        exitdialog.setContentView(R.layout.exit_dialog_box);

//        share_app = (TextView)dialog.findViewById(R.id.exit_dialog_text1);
        more_apps = (TextView)exitdialog.findViewById(R.id.exit_dialog_text2);
        rate_us = (TextView) exitdialog.findViewById(R.id.exit_dialog_text3);
        exit = (TextView)exitdialog.findViewById(R.id.exit_dialog_text4);
        cancel = (TextView)exitdialog.findViewById(R.id.exit_dialog_text5);
        more_apps_icon = (ImageView)exitdialog.findViewById(R.id.more_apps_icon);
        rate_us_icon = (ImageView)exitdialog.findViewById(R.id.rate_us_icon);


//        share_app.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i=new Intent(android.content.Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"APP NAME......");
//                i.putExtra(android.content.Intent.EXTRA_TEXT, "Wonderful App. Install and give 5 stars \n \n https:/play.google.com/store/apps........(APP GOOGLE PLAY LINK)");
//                startActivity(Intent.createChooser(i,"Share via"));
//
//            }
//        });

        more_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=navigation&hl=en/"));
                startActivity(browserIntent);

            }
        });


        rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }


            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //deleteAppData();
                finish();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exitdialog.cancel();
            }
        });



        exitdialog.show();

    }

    @Override
    public void onPause() {
        super.onPause();

        if(exitdialog != null)
            exitdialog.dismiss();
    }


    // *Intent code for going to MapRouteActivity.class*

//    public void onClick(View v) {
//        startActivity(new Intent(getBaseContext(), MapsRouteActivity.class));
//    }



    // *Code for Refreshing Activity for selecting different languages at a time*
//
//        Intent refresh = new Intent(this, StartActivity.class);
//        startActivity(refresh);
//
//    }


}







