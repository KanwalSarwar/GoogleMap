package com.example.usamanaseer.googlemap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SpinnerActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123 ;
    ImageButton submitbutton;
    Spinner spinner;
    Locale locale;
    private static final String DEFAULT = "";

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        //*Code line For Hiding Action Bar (App Name and Icon)*
       // getSupportActionBar().hide();

        setUpMapIfNeeded();

        //**Code Line for button animation (fade)**

        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

//        //*Onboarding Start Screen  Code*
//
//        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
//
//        if(!preferences.getBoolean("onboarding_complete",false)){
//
//            Intent i = new Intent(this, OnboardingActivity.class);
//            startActivity(i);
//
//            finish();
//            return;
//        }


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

        submitbutton = (ImageButton) findViewById(R.id.submit_button);
        spinner = (Spinner) findViewById(R.id.spinner1);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //***Onboarding Start Screen Code***

                SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

                if(!preferences.getBoolean("onboarding_complete",false)){

                    Intent i = new Intent(SpinnerActivity.this, OnboardingActivity.class);
                    startActivity(i);
                    finish();

                    //**Code Line for button animation (fade)**
                    v.startAnimation(buttonClick);
                }

//                Intent i = new Intent(SpinnerActivity.this, StartActivity.class);
//                startActivity(i);
//                finish();

            }
        });

            // **Spinner set for 25 different languages according to their positions**
      //  String[] languages = {"Select Language", "Bulgarian", "Catalan", "Czech", "Danish", "German", "Greek", "English", "Spanish", "Basque", "Finnish", "French", "Galician", "Croatian", "Hungarian", "Italian", "Lithuanian", "Latvian", "Dutch", "Polish", "Portuguese", "Romanian", "Russian", "Slovak", "Slovenian", "Swedish"};




        // **Spinner set for only ENGLISH AND FRENCH languages according to their positions**
        String[] languages = {"Select Language", "English", "French"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages){

            //*Code for Select Language Title

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                // ** below (TextView) 2 lines code for displaying Spinner items in white colour and size 20 **
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) arg0.getChildAt(0)).setTextSize(20);

                Configuration config = new Configuration();
                Locale locale;
                String languageToLoad;

                switch (arg2) {

//                    case 1:
//
////                        config.locale = Locale.ENGLISH;
//
//
//                        //*Shared Preference code for storing selected value of language to set on next activities*
//
//                        SharedPreferences bgsharedPreferences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor bgeditor = bgsharedPreferences.edit();
//                        bgeditor.putString("language", "bg");
//                        bgeditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Bulgarian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//
//                    case 2:
//
//
//                        SharedPreferences casharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor caeditor = casharedPrefrences.edit();
//                        caeditor.putString("language", "ca");
//                        caeditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Catalan Selected", Toast.LENGTH_SHORT).show();
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
//                    //config.locale = Locale.ITALIAN;
//
//                    case 3:
//
//
//                        SharedPreferences cssharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor cseditor = cssharedPrefrences.edit();
//                        cseditor.putString("language", "cs");
//                        cseditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Czech Selected", Toast.LENGTH_SHORT).show();
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
//                    //config.locale = Locale.FRENCH;
//
//                    case 4:
//
//
//
//                        SharedPreferences dasharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor daeditor = dasharedPrefrences.edit();
//                        daeditor.putString("language", "da");
//                        daeditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Danish Selected", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(SpinnerActivity.this, "German Selected", Toast.LENGTH_SHORT).show();
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
//                        SharedPreferences elsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor eleditor = elsharedPrefrences.edit();
//                        eleditor.putString("language", "el");
//                        eleditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Greek Selected", Toast.LENGTH_SHORT).show();
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

                    case 1:

                        SharedPreferences ensharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor eneditor = ensharedPrefrences.edit();
                        eneditor.putString("language", "en");
                        eneditor.commit();
                        Toast.makeText(SpinnerActivity.this, "English Selected", Toast.LENGTH_SHORT).show();
                        break;

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
//                    case 8:
//
//                        SharedPreferences essharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor eseditor = essharedPrefrences.edit();
//                        eseditor.putString("language", "es");
//                        eseditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Spanish Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 9:
//
//                        SharedPreferences eusharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor eueditor = eusharedPrefrences.edit();
//                        eueditor.putString("language", "eu");
//                        eueditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Basque Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 10:
//
//                        SharedPreferences fisharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor fieditor = fisharedPrefrences.edit();
//                        fieditor.putString("language", "fi");
//                        fieditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Finnish Selected", Toast.LENGTH_SHORT).show();
//                        break;

                    case 2:

                        SharedPreferences frsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor freditor = frsharedPrefrences.edit();
                        freditor.putString("language", "fr");
                        freditor.commit();
                        Toast.makeText(SpinnerActivity.this, "French Selected", Toast.LENGTH_SHORT).show();
                        break;

//                    case 12:
//
//                        SharedPreferences glsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor gleditor = glsharedPrefrences.edit();
//                        gleditor.putString("language", "gl");
//                        gleditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Galician Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 13:
//
//                        SharedPreferences hrsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor hreditor = hrsharedPrefrences.edit();
//                        hreditor.putString("language", "hr");
//                        hreditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Croatian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 14:
//
//                        SharedPreferences husharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor hueditor = husharedPrefrences.edit();
//                        hueditor.putString("language", "hu");
//                        hueditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Hungarian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 15:
//
//                        SharedPreferences itsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor iteditor = itsharedPrefrences.edit();
//                        iteditor.putString("language", "it");
//                        iteditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Italian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 16:
//
//                        SharedPreferences ltsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor lteditor = ltsharedPrefrences.edit();
//                        lteditor.putString("language", "lt");
//                        lteditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Lithuanian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 17:
//
//                        SharedPreferences lvsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor lveditor = lvsharedPrefrences.edit();
//                        lveditor.putString("language", "lv");
//                        lveditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Latvian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 18:
//
//                        SharedPreferences nlsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor nleditor = nlsharedPrefrences.edit();
//                        nleditor.putString("language", "nl");
//                        nleditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Dutch Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 19:
//
//                        SharedPreferences plsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor pleditor = plsharedPrefrences.edit();
//                        pleditor.putString("language", "pl");
//                        pleditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Polish Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 20:
//
//                        SharedPreferences ptsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor pteditor = ptsharedPrefrences.edit();
//                        pteditor.putString("language", "pt");
//                        pteditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Portuguese Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 21:
//
//                        SharedPreferences rosharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor roeditor = rosharedPrefrences.edit();
//                        roeditor.putString("language", "ro");
//                        roeditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Romanian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 22:
//
//                        SharedPreferences rusharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor rueditor = rusharedPrefrences.edit();
//                        rueditor.putString("language", "ru");
//                        rueditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Russian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 23:
//
//                        SharedPreferences sksharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor skeditor = sksharedPrefrences.edit();
//                        skeditor.putString("language", "sk");
//                        skeditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Slovak Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 24:
//
//                        SharedPreferences slsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor sleditor = slsharedPrefrences.edit();
//                        sleditor.putString("language", "sl");
//                        sleditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Slovenian Selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 25:
//
//                        SharedPreferences svsharedPrefrences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor sveditor = svsharedPrefrences.edit();
//                        sveditor.putString("language", "sv");
//                        sveditor.commit();
//                        Toast.makeText(SpinnerActivity.this, "Swedish Selected", Toast.LENGTH_SHORT).show();
//                        break;




                    default:

                        SharedPreferences endefaultsharedPreferences = getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor endefaulteditor = endefaultsharedPreferences.edit();
                        endefaulteditor.putString("language", "en");
                        endefaulteditor.commit();
                        break;


//                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//                        config.locale = Locale.ENGLISH;

                }
//                getResources().updateConfiguration(config, null);
//                Resources res = getResources();
//                DisplayMetrics dm = res.getDisplayMetrics();
//                Configuration conf = res.getConfiguration();
//                conf.locale = locale;
//                res.updateConfiguration(conf, dm);
            }




            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }




        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //*Shared Preference code for storing and receiving last selected value of language to set on next activities*

        SharedPreferences sharedPreferences = this.getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
        String pine = sharedPreferences.getString("language", DEFAULT);
        String languageToLoad = pine;
        Locale locale = new Locale(languageToLoad);//Set Selected Locale
        Locale.setDefault(locale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = locale;//set config locale as selected locale
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
    }




    private void setUpMapIfNeeded() {
    }










}
