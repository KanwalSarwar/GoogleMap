package com.example.usamanaseer.googlemap;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherActivity extends BaseActivity{//AppCompatActivity {

    EditText editTextCityName;
    //   Button btnByCityName;
    ImageButton btnByCityName;
    TextView textViewResult, textViewInfo, city_name, weather_description, temperature, date, dayname, humidity;
    String city, description, temp, humidity_level;
    String formatted_date, formatted_date_day, formatted_date_time;

    Button testing_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_weather);

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.activity_weather, frameLayout);

        /**
         * Setting title and itemChecked
         */
        //mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        editTextCityName = (EditText) findViewById(R.id.cityname);
        btnByCityName = (ImageButton) findViewById(R.id.bycityname);
//        textViewResult = (TextView)findViewById(R.id.result);
        textViewInfo = (TextView) findViewById(R.id.info);

        city_name = (TextView) findViewById(R.id.my_city_text);
        weather_description = (TextView) findViewById(R.id.weather_text);
        temperature = (TextView) findViewById(R.id.temp_text);
        date = (TextView) findViewById(R.id.date_text);
        dayname = (TextView) findViewById(R.id.day_name);
        humidity = (TextView) findViewById(R.id.day_time);


//        getSupportActionBar().hide();

//        testing_button = (Button) findViewById(R.id.test_button);
//
//        testing_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(MainActivity.this,Main2Activity.class);
//                startActivity(i);
//            }
//        });

        btnByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OpenWeatherMapTask(
                        editTextCityName.getText().toString(),
                        textViewResult).execute();


                // ** Code to automatically close keyboard after clicking the button **
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextCityName.getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


    }

    private class OpenWeatherMapTask extends AsyncTask<Void, Void, String> {

        String cityName;
        TextView tvResult;

        String dummyAppid = "8da70a1b9a0b5c2a6690c1053b0415fe";
        String queryWeather = "http://api.openweathermap.org/data/2.5/weather?q=";
        String queryDummyKey = "&appid=" + dummyAppid;
        String units = "&units=metric";

        OpenWeatherMapTask(String cityName, TextView tvResult) {
            this.cityName = cityName;
            this.tvResult = tvResult;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            String queryReturn;

            String query = null;
            try {
                query = queryWeather + URLEncoder.encode(cityName, "UTF-8") + queryDummyKey + units;
                queryReturn = sendQuery(query);
                result += ParseJSON(queryReturn);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            }


            // ** Code for setting result in TextViews receiving from JSON **
            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    // Any UI task, example
                    city_name.setText(city);
                    weather_description.setText(description);
                    temperature.setText(temp);

                    date.setText(formatted_date);
                    dayname.setText(formatted_date_day);
                    humidity.setText(humidity_level);
                }
            };
            handler.sendEmptyMessage(1);

//            final String finalQueryReturn = query + "\n\n" + queryReturn;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    textViewInfo.setText(finalQueryReturn);
//                }
//            });


            return result;
        }

//        @Override
//        protected void onPostExecute(String s) {
//            tvResult.setText(s);
//
//        }

        private String sendQuery(String query) throws IOException {
            String result = "";

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader,
                        8192);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
            }

            return result;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private String ParseJSON(String json) {
            String jsonResult = "";


            try {
                JSONObject JsonObject = new JSONObject(json);
                String cod = jsonHelperGetString(JsonObject, "cod");

//                JSONObject main_object = response.getJSONObject("main");
//                JSONArray array = response.getJSONArray("weather");
//                JSONObject object = array.getJSONObject(0);
//                String temp = String.valueOf(main_object.getDouble("temp"));
//                String description = object.getString("description");
//                String city = response.getString("name");

                if (cod != null) {
                    if (cod.equals("200")) {

                        //     jsonResult += jsonHelperGetString(JsonObject, "name") + "\n";

                        // ** Setting JSON data in Strings to setText with TextViews **
                        city = jsonHelperGetString(JsonObject, "name");

                        JSONArray weather = jsonHelperGetJSONArray(JsonObject, "weather");
                        JSONObject thisWeather = weather.getJSONObject(0);
                        description = jsonHelperGetString(thisWeather, "description");

                        JSONObject main = jsonHelperGetJSONObject(JsonObject, "main");
                        temp = jsonHelperGetString(main, "temp");
                        humidity_level = jsonHelperGetString(main, "humidity") + "%";

                        // ** Code for Current Date, Day and Time **
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
                        formatted_date = sdf.format(calendar.getTime());

                        SimpleDateFormat sdf_day = new SimpleDateFormat("EEEE");
                        formatted_date_day = sdf_day.format(calendar.getTime());

//                        SimpleDateFormat sdf_time = new SimpleDateFormat("h:mm a");
//                        formatted_date_time = sdf_time.format(calendar.getTime());
//                        sdf_time.setTimeZone(TimeZone.getTimeZone("GMT"));


//                        JSONObject sys = jsonHelperGetJSONObject(JsonObject, "sys");
//                        if(sys != null){
//                            jsonResult += jsonHelperGetString(sys, "country") + "\n";
//                        }
//                        jsonResult += "\n";
//
//                        JSONObject coord = jsonHelperGetJSONObject(JsonObject, "coord");
//                        if(coord != null){
//                            String lon = jsonHelperGetString(coord, "lon");
//                            String lat = jsonHelperGetString(coord, "lat");
//                            jsonResult += "lon: " + lon + "\n";
//                            jsonResult += "lat: " + lat + "\n";
//                        }
//                        jsonResult += "\n";

//                        JSONArray weather = jsonHelperGetJSONArray(JsonObject, "weather");
//                        if(weather != null){
//                            for(int i=0; i<weather.length(); i++){
//                                JSONObject thisWeather = weather.getJSONObject(i);
//                                jsonResult += "weather " + i + ":\n";
//                                jsonResult += "id: " + jsonHelperGetString(thisWeather, "id") + "\n";
//                                jsonResult += jsonHelperGetString(thisWeather, "main") + "\n";
//                                jsonResult += jsonHelperGetString(thisWeather, "description") + "\n";
//                                jsonResult += "\n";
//                            }
//                        }

//                        JSONObject main = jsonHelperGetJSONObject(JsonObject, "main");
//                        if(main != null){
//                            jsonResult += "temp: " + jsonHelperGetString(main, "temp") + "\n";
//                            jsonResult += "pressure: " + jsonHelperGetString(main, "pressure") + "\n";
//                            jsonResult += "humidity: " + jsonHelperGetString(main, "humidity") + "\n";
//                            jsonResult += "temp_min: " + jsonHelperGetString(main, "temp_min") + "\n";
//                            jsonResult += "temp_max: " + jsonHelperGetString(main, "temp_max") + "\n";
//                            jsonResult += "sea_level: " + jsonHelperGetString(main, "sea_level") + "\n";
//                            jsonResult += "grnd_level: " + jsonHelperGetString(main, "grnd_level") + "\n";
//                            jsonResult += "\n";
//                        }

//                        jsonResult += "visibility: " + jsonHelperGetString(JsonObject, "visibility") + "\n";
//                        jsonResult += "\n";
//
//                        JSONObject wind = jsonHelperGetJSONObject(JsonObject, "wind");
//                        if(wind != null){
//                            jsonResult += "wind:\n";
//                            jsonResult += "speed: " + jsonHelperGetString(wind, "speed") + "\n";
//                            jsonResult += "deg: " + jsonHelperGetString(wind, "deg") + "\n";
//                            jsonResult += "\n";
//                        }

                        //...incompleted

                    } else if (cod.equals("404")) {
                        String message = jsonHelperGetString(JsonObject, "message");
                        jsonResult += "cod 404: " + message;
                    }
                } else {
                    jsonResult += "cod == null\n";
                }

            } catch (JSONException e) {
                e.printStackTrace();
                jsonResult += e.getMessage();
            }

            return jsonResult;
        }

        private String jsonHelperGetString(JSONObject obj, String k) {
            String v = null;
            try {
                v = obj.getString(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return v;
        }

        private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k) {
            JSONObject o = null;

            try {
                o = obj.getJSONObject(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return o;
        }

        private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k) {
            JSONArray a = null;

            try {
                a = obj.getJSONArray(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return a;
        }
    }

    public void onBackPressed() {


        Intent i = new Intent(WeatherActivity.this,EntryActivity.class);
        startActivity(i);
        finish();

    }

}