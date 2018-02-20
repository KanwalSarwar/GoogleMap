package com.example.usamanaseer.googlemap;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.Locale;

/* Main activity demonstrating how to pass extra parameters to an activity that
 * recognizes text.
 */


// *Main2Activity is Image-to-Text Conversion Work Activity*
public class Main2Activity extends BaseActivity implements View.OnClickListener { //FragmentActivity

    private static final String API_KEY = "AIzaSyDepY3gUVPnjFNnZHVHjS-c26yMZzqkvdI";
    // Use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView textValue;
    //private TextView texttranslate;
    private TextToSpeech tts;

    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.activity_main2, frameLayout);

        /**
         * Setting title and itemChecked
         */
        //mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        statusMessage = (TextView)findViewById(R.id.status_message);
        textValue = (TextView)findViewById(R.id.text_value);
        //texttranslate = (TextView)findViewById(R.id.text_translate);


        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        findViewById(R.id.read_text).setOnClickListener(this);

        // TODO: Set up the Text To Speech engine.
        TextToSpeech.OnInitListener listener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(final int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Log.d("TTS", "Text to speech engine started successfully.");
                    tts.setLanguage(Locale.US); //FRENCH
                } else {
                    Log.d("TTS", "Error starting the text to speech engine.");
                }
            }
        };
        tts = new TextToSpeech(this.getApplicationContext(), listener);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_text) {
            // launch Ocr capture activity.
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    final String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    statusMessage.setText(R.string.ocr_success);
                    textValue.setText(text);
                    //
                    final Handler textViewHandler = new Handler();

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            TranslateOptions options = TranslateOptions.newBuilder()
                                    .setApiKey(API_KEY)
                                    .build();
                            Translate translate = options.getService();
                            final Translation translation =
                                    translate.translate(text, Translate.TranslateOption.targetLanguage("en")); //de
                            textViewHandler.post(new Runnable() {
                                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void run() {
                                    if (textValue != null) {
                                        Log.e("TOAST : ",text);
                                        String trtext = translation.getTranslatedText();
                                        //textValue.setText(translation.getTranslatedText()); //coomented cz don't want to show translated text in textview
                                        // Speak the string.
                                        tts.speak(translation.getTranslatedText(), TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                                        //
                                        //showAlertDialog(text, trtext);
                                        AlertDialog alertDialog = new AlertDialog.Builder(Main2Activity.this).create(); //Read Update
                                        alertDialog.setTitle("Text Detected");
                                        alertDialog.setIcon(R.mipmap.search);
                                        alertDialog.setMessage(trtext);

                                        alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // here you can add functions
                                            }
                                        });

                                        alertDialog.show();
                                        //
                                    }
                                }
                            });
                            return null;
                        }
                    }.execute();
                    //
                    Log.d(TAG, "Text read: " + text);



                } else {
                    statusMessage.setText(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //custom alert dialog
    /*private void showAlertDialog(String t1, String t2) {
        FragmentManager fm = getSupportFragmentManager();
        TextDialog alertDialog = TextDialog.newInstance(t1,t2);
        alertDialog.show(fm, "fragment_alert");
    }*/


    // *Code for on press of mobile back button go to previous class*

    @Override
    public void onBackPressed() {


        Intent i = new Intent(Main2Activity.this,NearestPlacesActivity.class);
        startActivity(i);
        finish();

    }


}
