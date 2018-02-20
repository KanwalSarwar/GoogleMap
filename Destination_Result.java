package com.example.usamanaseer.googlemap;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;



public class Destination_Result extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123 ;
    TextView mName, mAddress,mPhonenum;
    ImageButton sharebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination__result);

        getSupportActionBar().hide();


        //**Code Line for button animation (fade)**

        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

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
//                .forPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                .go();

        mName = (TextView) findViewById(R.id.place_name);
        mAddress = (TextView) findViewById(R.id.place_address);
        mPhonenum = (TextView) findViewById(R.id.place_phonenumber);
        sharebutton = (ImageButton) findViewById(R.id.share_app_button);


        mName.setText(getIntent().getExtras().getString("placename"));
        mAddress.setText(getIntent().getExtras().getString("placeaddress"));
        mPhonenum.setText(getIntent().getExtras().getString("phonenumber"));

        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Place Details \n");
                i.putExtra(android.content.Intent.EXTRA_TEXT, mName.getText().toString() + "\n \n" + mAddress.getText().toString() + "\n \n" + mPhonenum.getText().toString() );
                startActivity(Intent.createChooser(i,"Share via"));


                //**Code Line for button animation (fade)**
                v.startAnimation(buttonClick);

            }
        });



    }

    public void onBackPressed() {


        Intent i = new Intent(Destination_Result.this,EntryActivity.class);
        startActivity(i);
        finish();

    }


}
