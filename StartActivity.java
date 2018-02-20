package com.example.usamanaseer.googlemap;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


import static android.app.PendingIntent.getActivity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;


public class StartActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    Button start_button;
    Context mContext;
    String appPackageName;
    TextView share_app, more_apps, rate_us, exit, cancel;
    ImageView more_apps_icon, rate_us_icon;
    View v1;
    Dialog dialog;
    Spinner spinner;
    Locale locale;
    private static final String DEFAULT = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
        String pine = sharedPreferences.getString("language", DEFAULT);
        String languageToLoad = pine;
        Locale locale = new Locale(languageToLoad);//Set Selected Locale
        Locale.setDefault(locale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = locale;//set config locale as selected locale
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());


        setContentView(R.layout.activity_start);

        setUpMapIfNeeded();

       //**Code Line for button animation (fade)**

       final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        start_button = (Button) findViewById(R.id.start_button);
//        spinner = (Spinner) findViewById(R.id.spinner1);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StartActivity.this, EntryActivity.class);
                startActivity(i);
                finish();

                //**Code Line for button animation (fade)**
                v.startAnimation(buttonClick);
            }
        });



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


//        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);



//        Ask.on(this)
//                .forPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .go();

//        String[] languages = { "Select Language", "English", "Italiano", "Francese", "Arabic","German","Hindi","Japanese" };
//        String[] languages = {"Select Language", "English", "Italiano", "Francese", "Bangla", "German", "Hindi", "Japanese"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages){
//
//            //*Code for Select Language Title
//
//            @Override
//            public boolean isEnabled(int position){
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
//                    return true;
//                }
//            }
//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position == 0){
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.GRAY);
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//
//                Configuration config = new Configuration();
//                Locale locale;
//                String languageToLoad;
//
//                switch (arg2) {
//                    case 1:
//
////                        config.locale = Locale.ENGLISH;
//
//                        //*Shared Preference code for storing selected value of language to set on next activities*
//
//                        SharedPreferences ensharedPreferences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor eneditor = ensharedPreferences.edit();
//                        eneditor.putString("language", "en");
//                        eneditor.commit();
//                        Toast.makeText(StartActivity.this, "English Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//
//                    case 2:
//
//
//                        SharedPreferences itsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor iteditor = itsharedPrefrences.edit();
//                        iteditor.putString("language", "it");
//                        iteditor.commit();
//                        Toast.makeText(StartActivity.this, "Italian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
////                        languageToLoad = "it_IT";
////                        locale = new Locale(languageToLoad);
////                        Locale.setDefault(locale);
////                        config = new Configuration();
////                        config.locale = locale;
////                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//
////                        locale = new Locale("it");
////                        config.locale =locale;
//                        //config.locale = Locale.ITALIAN;
//
//                    case 3:
//
//
//                        SharedPreferences frsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor freditor = frsharedPrefrences.edit();
//                        freditor.putString("language", "fr");
//                        freditor.commit();
//                        Toast.makeText(StartActivity.this, "French Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
////                        languageToLoad = "fr_FR";
////                        locale = new Locale(languageToLoad);
////                        Locale.setDefault(locale);
////                        config = new Configuration();
////                        config.locale = locale;
////                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//
//
////                        locale = new Locale("fr");
////                        config.locale =locale;
//                        //config.locale = Locale.FRENCH;
//
//                    case 4:
//
//
//
//                        SharedPreferences bnsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor bneditor = bnsharedPrefrences.edit();
//                        bneditor.putString("language", "bn");
//                        bneditor.commit();
//                        Toast.makeText(StartActivity.this, "Bangla Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
////                        languageToLoad = "ar_SA";
////                        locale = new Locale(languageToLoad);
////                        Locale.setDefault(locale);
////                        config = new Configuration();
////                        config.locale = locale;
////                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
////
////                        locale = new Locale("bn");
////                        config.locale =locale;
//
//                    case 5:
//
//
//                        SharedPreferences desharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor deeditor = desharedPrefrences.edit();
//                        deeditor.putString("language", "de");
//                        deeditor.commit();
//                        Toast.makeText(StartActivity.this, "German Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
////                        languageToLoad = "de_DE";
////                        locale = new Locale(languageToLoad);
////                        Locale.setDefault(locale);
////                        config = new Configuration();
////                        config.locale = locale;
////                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//
////
////                        locale = new Locale("de");
////                        config.locale =locale;
//
//                    case 6:
//
//
//                        SharedPreferences hisharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor hieditor = hisharedPrefrences.edit();
//                        hieditor.putString("language", "hi");
//                        hieditor.commit();
//                        Toast.makeText(StartActivity.this, "Hindi Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
////                        languageToLoad = "hi_IN";  // hindi is not supporting by google map
////                        locale = new Locale(languageToLoad);
////                        Locale.setDefault(locale);
////                        config = new Configuration();
////                        config.locale = locale;
////                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//
//
//
////                        locale = new Locale("hi");
////                        config.locale =locale;
//
//                    case 7:
//
//                        SharedPreferences npsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor npeditor = npsharedPrefrences.edit();
//                        npeditor.putString("language", "ja");
//                        npeditor.commit();
//                        Toast.makeText(StartActivity.this, "Japnese Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//
////                        languageToLoad = "ja_JP";
////                        locale = new Locale(languageToLoad);
////                        Locale.setDefault(locale);
////                        config = new Configuration();
////                        config.locale = locale;
////                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
////
////                        locale = new Locale("ja");
////                        config.locale =locale;
//
//                    default:
//
//                        SharedPreferences endefaultsharedPreferences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor endefaulteditor = endefaultsharedPreferences.edit();
//                        endefaulteditor.putString("language", "en");
//                        endefaulteditor.commit();
//                        break;
//
//
////                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
////                        config.locale = Locale.ENGLISH;
//
//                }
////                getResources().updateConfiguration(config, null);
////                Resources res = getResources();
////                DisplayMetrics dm = res.getDisplayMetrics();
////                Configuration conf = res.getConfiguration();
////                conf.locale = locale;
////                res.updateConfiguration(conf, dm);
//            }
//
//
//
//
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//
//
//
//        });




    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //*Shared Preference code for storing and receiving last selected value of language to set on next activities*
//
//        SharedPreferences sharedPreferences = this.getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//        String pine = sharedPreferences.getString("language", DEFAULT);
//        String languageToLoad = pine;
//        Locale locale = new Locale(languageToLoad);//Set Selected Locale
//        Locale.setDefault(locale);//set new locale as default
//        Configuration config = new Configuration();//get Configuration
//        config.locale = locale;//set config locale as selected locale
//        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
//    }





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


    private void setUpMapIfNeeded() {
    }




    @Override
    public void onBackPressed() {

        //***Code for alert-dialog box generation with YES, NO and RATE Us buttons***

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

        dialog = new Dialog(StartActivity.this);
        dialog.setContentView(R.layout.exit_dialog_box);

//        share_app = (TextView)dialog.findViewById(R.id.exit_dialog_text1);
        more_apps = (TextView)dialog.findViewById(R.id.exit_dialog_text2);
        rate_us = (TextView) dialog.findViewById(R.id.exit_dialog_text3);
        exit = (TextView)dialog.findViewById(R.id.exit_dialog_text4);
        cancel = (TextView)dialog.findViewById(R.id.exit_dialog_text5);
        more_apps_icon = (ImageView)dialog.findViewById(R.id.more_apps_icon);
        rate_us_icon = (ImageView)dialog.findViewById(R.id.rate_us_icon);


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

                deleteAppData();
                finish();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });



        dialog.show();

    }

    @Override
    public void onPause() {
        super.onPause();

        if(dialog != null)
            dialog.dismiss();
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

