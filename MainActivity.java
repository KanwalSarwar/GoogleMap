package com.example.usamanaseer.googlemap;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.ArrayList;
import java.util.Arrays;


// *MainActivity is Text-to-Text (Language Converter) Activity*

public class MainActivity extends BaseActivity { //} implements AdapterView.OnItemSelectedListener { //AppCompatActivity

    //https://translation.googleapis.com/language/translate/v2/languages?key=AIzaSyDepY3gUVPnjFNnZHVHjS-c26yMZzqkvdI //url for list of lang code
    // todo API_KEY should not be stored in plain sight
    private static final String API_KEY = "AIzaSyDepY3gUVPnjFNnZHVHjS-c26yMZzqkvdI";
    String text="";
    Spinner dropdown;
    Spinner dropdown1;
    DatabaseHandler dbHandler;
    static String code_param="fr";
    static String code_param2="en";
    /*String[] l_name={"English","French"};
    String[] l_code={"en","fr"};*/
    //bottom sheet
    LinearLayout linearLayoutOne, linearLayoutTwo, mainLayout;
    public static int flag;
    public static BottomSheetBehavior behavior;
    RecyclerView recyclerView;
    int screenHeight = 0;
    public static TextView date, currencyTitle, TextTo, TextFrom;
    Typeface tfRegular, tfThin;
    public static ImageView arrowDown, arrowUp;
    LanguageAdapter mAdapter;
    ArrayList items = new ArrayList();
    public static EditText search;
    public static Context ctx;


    // ** FOR CURRENCY CONVERTER STRING **
    String[] dataArray = new String[]{"United Arab Emirates Dirham","Afghan Afghani","Albanian Lek","Armenian Dram","Angolan Kwanza","Argentine Peso","Australian Dollar (Australia)","Australian Dollar (Kiribati)","Australian Dollar (Nauru)","Australian Dollar (Tuvalu)","Aruban Guilder","New Azerbaijani Manat","Convertible Mark","Barbados Dollar","Bangladeshi Taka","Bulgarian Lev","Bahraini Dinar","Burundian Franc","Brunei Dollar","Boliviano","Brazilian Real","Bahamian Dollar"," ","Bhutanese Ngultrum","Botswana Pula"," ","Belarusian Ruble","Belize Dollar","Canadian Dollar","Congolese Franc","Swiss Franc (Liechtenstein)","Swiss Franc (Switzerland)"," ","Chilean Peso","Chinese Yuan","Colombian Peso","Costa Rican Colon"," ","Cuban convertible Peso"," ","Czech Koruna","Djiboutian Franc","Danish Krone","Dominican Peso","Algerian Dinar"," ","Egyptian Pound","Eritrean Nakfa","Ethipian Birr","Euro (Finland)","Euro (Luxembourg)","Euro (Portugal)","Euro (Slovenia)","Euro (Spain)","Euro (Estonia)","Euro (Andorra)","Euro (Belgium)","Euro (Malta)","Euro (Austria)","Euro (Cyprus)","Euro (France)","Euro (Germany)","Euro (Italy)","Euro (Montenegro)","Euro (San Marino)","Euro (Greece)","Euro (Ireland)","Euro (Monaco)","Euro (Netherlands)","Euro (Slovakia)","Lithuanian Litas","Fiji Dollar"," ","Pound Sterling","Georgian Lari"," ","Ghanaian Cedi","Gibraltar Pound","Gambian Dalasi","Guinean Franc","Guatemalan Quetzal","Guyanese Dollar","Hong Kong Dollar","Honduran Lempira","Croatian Kuna","Haitian Gourde","Hungarian Forint","Indonesian Rupiah","Israeli New Shekel"," ","Japanese Yen","Kenyan Shilling","Kyrgyzstani Som","Cambodian Riel","Comoro Franc","North Korean Won","South Korean Won","Kuwaiti Dinar","Cayman Islands Dollar","Kazakhstani Tenge","Lao Kip","Lebanese Pound","Sri Lankan Rupee","Lesotho Loti"," ","Euro (Latvia)","Libyan Dinar","Moroccan Dirham","Moldovan Leu","Malagasy Ariayry","Macedonian Denar","Myanma Kyat","Mongolian Tugrik","Macanese Pataca","Mauritanian Ouguiya","Mauritian Rupee","Maldivian Rufiyaa","Malawian Kwacha","Mexican Peso","Malaysian Ringgit","Mozambican Metical","Namibian Dollar","Nigerian Naira","Nicaraguan Córdoba","Norwegian Krone","Nepalese Rupee","New Zealand Dollar","Omani Rial","Panamanian Balboa","Peruvian Nuevo Sol","Papua New Guinean Kina","Philippine Peso","Pakistani Rupee","Polish Zloty","Paraguayan Guaraní","Qatari Riyal","Romanian New Leu","Serbian Dinar","Russian Ruble","Rwandan Franc","Saudi Riyal","Solomon Islands Dollar","Seychelles Rupee","Sudanese Pound","South Sudanese Pound","Swedish Krona","Singapore Dollar","Saint Helena Pound","Sierra Leonean Leone","Somali Shilling","Surinamese Dollar","São Tomé Dobra"," ","Syrian Pound","Swazi Lilangeni","Thai Baht","Tajikistani Somoni","Turkmenistani New Manat","Tunisian Dinar","Tongan Pa'anga","Turkish Lira","Trinidad Dollar","New Taiwan Dollar","Tanzanian Shilling","Ukrainian Hryvnia","Ugandan Shilling","US Dollar (Ecuador)","US Dollar (Micronesia)","US Dollar (Palau)","US Dollar (United States)","US Dollar (Puerto Rico)","US Dollar (El Salvador)","Urugayan Peso","Uzbekitan Som","Venezualan Bolivar Fuerte","Vietnamese Dồng","Vanuatu Vatu","Samoan Tala","CFA Franc BEAC (Chad)","CFA Franc BEAC (Congo)","CFA Franc BEAC (Gabon)","CFA Franc BEAC (Cameroon)","CFA Franc BEAC (Central African Republic)"," "," ","East Caribbean Dollar (Anguilla)","East Caribbean Dollar (Dominica)","East Caribbean Dollar (Grenada)","East Caribbean Dollar (Montserrat)","East Caribbean Dollar (St.Kitts Nevis Anguilla)","East Caribbean Dollar (Saint Lucia)","East Caribbean Dollar (Antigua & Barbuda)","East Caribbean Dollar (St.Vincent & Grenadines)"," ","CFA Franc BCEAO (Benin)","CFA Franc BCEAO (Mali)","CFA Franc BCEAO (Togo)","CFA Franc BCEAO (Burkina Faso)","Chilean Peso","CFA Franc BEAC (Guinea Bissau)","CFA Franc BCEAO (Niger)","CFA Franc BCEAO (Senegal)","French Pacific Franc (New Caledonia)","French Pacific Franc (French Polynesia)","French Pacific Franc (Wallis & Futuna Islands)","Yemeni Rial","South African Rand"," ","Zambian Kwacha"," "};






    String[] l_name = {"English	en", "Afrikaans	af", "Albanian	sq", "Amharic	am", "Arabic	ar", "Armenian	hy", "Azeerbaijani	az", "Basque	eu",
            "Belarusian	be", "Bengali	bn", "Bosnian	bs", "Bulgarian	bg", "Catalan	ca", "Cebuano	ceb (ISO-639-2)", "Chinese (Simplified)	zh-CN (BCP-47)",
            "Chinese (Traditional)	zh-TW (BCP-47)", "Corsican	co", "Croatian	hr", "Czech	cs", "Danish	da", "Dutch	nl", "Esperanto	eo", "Estonian	et", "Finnish	fi", "French	fr", "Frisian	fy",
            "Galician	gl", "Georgian	ka", "German	de", "Greek	el", "Gujarati	gu", "Haitian Creole	ht", "Hausa	ha",
            "Hawaiian	haw (ISO-639-2)", "Hebrew	iw", "Hindi	hi", "Hmong	hmn (ISO-639-2)", "Hungarian	hu", "Icelandic	is",
            "Igbo	ig", "Indonesian	id", "Irish	ga", "Italian	it", "Japanese	ja", "Javanese	jw", "Kannada	kn", "Kazakh	kk",
            "Khmer	km", "Korean	ko", "Kurdish	ku", "Kyrgyz	ky", "Lao	lo", "Latin	la", "Latvian	lv", "Lithuanian	lt", "Luxembourgish	lb",
            "Macedonian	mk", "Malagasy	mg", "Malay	ms", "Malayalam	ml", "Maltese	mt", "Maori	mi", "Marathi	mr", "Mongolian	mn", "Myanmar (Burmese)	my",
            "Nepali	ne", "Norwegian	no", "Nyanja (Chichewa)	ny", "Pashto	ps", "Persian	fa", "Polish	pl", "Portuguese (Portugal, Brazil)	pt",
            "Punjabi	pa", "Romanian	ro", "Russian	ru", "Samoan	sm", "Scots Gaelic	gd", "Serbian	sr", "Sesotho	st", "Shona	sn",
            "Sindhi	sd", "Sinhala (Sinhalese)	si", "Slovak	sk", "Slovenian	sl", "Somali	so", "Spanish	es", "Sundanese	su",
            "Swahili	sw", "Swedish	sv", "Tagalog (Filipino)	tl", "Tajik	tg", "Tamil	ta", "Telugu	te", "Thai	th", "Turkish	tr",
            "Ukrainian	uk", "Urdu	ur", "Uzbek	uz", "Vietnamese	vi", "Welsh	cy", "Xhosa	xh", "Yiddish	yi", "Yoruba	yo",
            "Zulu zu"}; //(BCP-47)

    String[] l_code = {"en", "af", "sq", "am", "ar", "hy", "az", "eu", "be", "bn", "bs", "bg", "ca", "ceb", "zh-CN",
            "zh-TW", "co", "hr", "cs", "da", "nl", "eo", "et", "fi", "fr", "fy", "gl", "ka", "de", "el", "gu", "ht", "ha",
            "haw", "iw", "hi", "hmn", "hu", "is", "ig", "id", "ga", "it", "ja", "jw", "kn", "kk", "km", "ko", "ku", "ky",
            "lo", "la", "lv", "lt", "lb", "mk", "mg", "ms", "ml", "mt", "mi", "mr", "mn", "my", "ne", "no", "ny", "ps", "fa", "pl", "pt", "pa",
            "ro", "ru", "sm", "gd", "sr", "st", "sn", "sd", "si", "sk", "sl", "so", "es", "su", "sw", "sv", "tl", "tg", "ta", "te", "th", "tr",
            "uk", "ur", "uz", "vi", "cy", "xh", "yi", "yo", "zu"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        /**
         * Setting title and itemChecked
         */
        //mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        // ** Code line to hide the title name of App **
       // getSupportActionBar().hide();

        initialize();

        final EditText textView = (EditText) findViewById(R.id.text_view);
        final TextView textView1 = (TextView) findViewById(R.id.text_view2);
        final ImageView btn=(ImageView)findViewById(R.id.enter);
        final ImageView switch_bt=(ImageView)findViewById(R.id.switch_btn);

        /* this 1
        dropdown = (Spinner)findViewById(R.id.spinner);
        dropdown.setOnItemSelectedListener(this);

        dropdown1 = (Spinner)findViewById(R.id.spinner1);
        dropdown1.setOnItemSelectedListener(this);*/

        dbHandler = new DatabaseHandler(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
/*        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,l_name);*/ //this 1
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);*/
// Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        /* this 1
        dropdown1.setAdapter(adapter);
        dropdown.setAdapter(adapter);*/

        //showInputMethodPicker(); //don't want to see available keyboards
        Log.e("ID: ",Settings.Secure.getString(getContentResolver(),Settings.Secure.DEFAULT_INPUT_METHOD)); //Settings.Secure.getString(getContentResolver(),Settings.Secure.DEFAULT_INPUT_METHOD)

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text= textView.getText().toString();
                final Handler textViewHandler = new Handler();

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        // TODO Auto-generated method stub
                        super.onPreExecute();
                        /*pDialog = new ProgressDialog(context);
                        pDialog.setMessage("Please wait...");
                        pDialog.setCancelable(true);
                        pDialog.show();*/
                        textView1.setText("translating...");
                    }
                    @Override
                    protected Void doInBackground(Void... params) {
                        TranslateOptions options = TranslateOptions.newBuilder()
                                .setApiKey(API_KEY)
                                .build();
                        Translate translate = options.getService();
                        final Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(code_param)); //de
                        textViewHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (textView1 != null) {
                                        Log.e("TOAST : ", text);
                                        textView1.setText(translation.getTranslatedText());
                                }
                            }
                        });
                        return null;
                    }
                }.execute();
            }
        });

        //textView.setText(text);
        switch_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //swapping values of selected lang_code
                String temp=code_param2;
                code_param2=code_param;
                code_param=temp;
                //swapping the spinner selection position
               /*this
               int spinner1Index = dropdown1.getSelectedItemPosition();

                dropdown1.setSelection(dropdown.getSelectedItemPosition());
                dropdown.setSelection(spinner1Index );*/
            }
        });

        //bottom sheet code
        screenHeight = getScreenHeight();
        //inflating bottom sheet
        final LinearLayout bottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheet.getLayoutParams().height = getScreenHeight() - 100;
        behavior = BottomSheetBehavior.from(bottomSheet);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //TODO fill the list
        mAdapter = new LanguageAdapter(MainActivity.this, Arrays.asList(l_name) , Arrays.asList(l_code));//Globals.NEWcountryCode, Globals.NEWcountriesCurrencies);
        recyclerView.setAdapter(mAdapter);

        behavior.setPeekHeight(0);
        //Setting click listeners on arrows
        linearLayoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayoutOne.getWindowToken(), 0);
//                behavior.setPeekHeight(screenHeight/2);
            }
        });
        linearLayoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayoutTwo.getWindowToken(), 0);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    mAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    /*this 1
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        //Toast.makeText(this,parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.spinner1)
        {
            code_param2=l_code[pos];
            ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(36,102,134)); //Color.WHITE
            ((TextView) parent.getChildAt(0)).setTextSize(20);
            //Toast.makeText(this, l_name[pos],Toast.LENGTH_SHORT).show();
        }
        if(spin2.getId() == R.id.spinner)
        {
            code_param=l_code[pos];
            ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(36,102,134));
            ((TextView) parent.getChildAt(0)).setTextSize(20);
            //Toast.makeText(this, l_name[pos],Toast.LENGTH_SHORT).show();
        }
    }*/

    /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
        public void onItemSelected (AdapterView < ? > arg0, View arg1,int arg2, long arg3){

        // ** below (TextView) 2 lines code for displaying Spinner items in white colour and size 20 **
        ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) arg0.getChildAt(0)).setTextSize(20);
        }
    }*/

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void showInputMethodPicker() {
        /*InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imeManager != null) {
            imeManager.showInputMethodPicker();
        } else {
            Toast.makeText(this, "not possible", Toast.LENGTH_LONG).show(); //R.string.not_possible_im_picker
        }*/
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);

        imeManager.showInputMethodPicker(); //This is to see available keyboards.
        //imeManager.setInputMethod(null,"com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME") ; //"jp.co.omronsoft.openwnn/.OpenWnnJAJP");
    }

    // *Code for on press of mobile back button go to previous class*

    @Override
    public void onBackPressed() {


        Intent i = new Intent(MainActivity.this,NearestPlacesActivity.class);
        startActivity(i);
        finish();

    }

    private void initialize() {

        arrowDown = (ImageView) findViewById(R.id.down_arrow);
        arrowUp = (ImageView) findViewById(R.id.up_arrow);
        TextTo = (TextView) findViewById(R.id.text_to);
        TextFrom = (TextView) findViewById(R.id.text_from);

        search = (EditText) findViewById(R.id.search);


        linearLayoutOne = (LinearLayout) findViewById(R.id.linearll_1);
        linearLayoutTwo = (LinearLayout) findViewById(R.id.linearll_2);
        //mainLayout = (LinearLayout) findViewById(R.id.maine_linear_layout);

        /*tfRegular = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        tfRegular = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Raleway-ExtraLight.ttf");
        date.setTypeface(tfRegular);
        currencyTitle.setTypeface(tfRegular);*/
        TextTo.setTypeface(tfThin);
        TextFrom.setTypeface(tfThin);


        ColorStateList csl = AppCompatResources.getColorStateList(this, R.color.blue);
        Drawable drawableone = getResources().getDrawable(R.drawable.ic_keyboard_arrow_down);
        DrawableCompat.setTintList(drawableone, csl);
        arrowDown.setImageDrawable(drawableone);
        arrowUp.setImageDrawable(drawableone);

        //hideKeyboard(findViewById(R.id.relative_1));
    }

    private int getScreenHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("SCREEN HEIGHT", "getScreenHeight: " + height);
        return height;
    }

    private void hideKeyboard(View root) {
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    View view = v.getRootView().findFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return false;
                }
                return false;
            }
        });
    }

}
