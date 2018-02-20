package com.example.usamanaseer.googlemap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Splash_Screen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String PREFERENCES_FILE_NAME = "LaunchingActivityPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MyAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        //getSupportActionBar().hide();

        sharedPreferences=getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        final boolean isfirstTime=sharedPreferences.getBoolean("first",true);
        /*if(isfirstTime) {
            editor.putBoolean("first", false);
            editor.commit();
        }*/
      /*  if(isfirstTime){
            editor.putBoolean("first",false);
            editor.commit();
            //setContentView(R.layout.activity_splash__screen);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    //Singleton.getInstance();
                    startActivity(new Intent(getApplicationContext(), SpinnerActivity.class));
                }
            }, 3000);
        }else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent=new Intent(getApplicationContext(),OnboardingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }*/

//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
//                {
//                    context.finish();
//                    context.startActivity(context.getIntent());
//                } else context.recreate();
//            }
//        }, 1);


        new Handler().postDelayed(new Runnable() {


             /* Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company*/


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(isfirstTime) {
                    editor.putBoolean("first", false);
                    editor.commit();
                    Log.e("FIRST: ",String.valueOf(isfirstTime));
                    Intent i = new Intent(Splash_Screen.this, SpinnerActivity.class);
                    startActivity(i);
                }else {
                    Log.e("FIRST: ",String.valueOf(isfirstTime));
                    Intent intent=new Intent(Splash_Screen.this,NearestPlacesActivity.class); //Entry Activity
                    startActivity(intent);
                }

                // close this activity
                finish();
            }
        }, 3000);
    }

}
