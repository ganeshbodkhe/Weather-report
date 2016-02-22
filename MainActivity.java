package com.example.ganesh.weatherreport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener {

    public String Current_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public String API_Key= "your key";

    Button btn_weather, forecast_btn;
    TextView temp_tv, min_max_tv, desc_tv, place_tv;
    EditText cityname;
    NetworkImageView iv;

    ProgressDialog PD;

    String name, id, country, description, icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btn_weather = (Button) findViewById(R.id.button1);
        forecast_btn = (Button) findViewById(R.id.forecast);

        forecast_btn.setOnClickListener(this);

        cityname = (EditText) findViewById(R.id.editText1);

        place_tv = (TextView) findViewById(R.id.place);
        temp_tv = (TextView) findViewById(R.id.temp);
        min_max_tv = (TextView) findViewById(R.id.min_max);
        desc_tv = (TextView) findViewById(R.id.desc);

        iv = (NetworkImageView) findViewById(R.id.icon);

        btn_weather.setOnClickListener(this);

        System.out.println("Correct");
    }

    public void makejsonreq(String full_url) {

        PD.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                full_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                System.out.println("Response "+response.toString());

                try {

                    name = response.getString("name");
                    id = response.getString("id");

                    JSONObject main = response.getJSONObject("main");

                    int temp, temp_max, temp_min;
                    temp = (int) (main.getDouble("temp") - 273.15);
                    temp_max = (int) (main.getDouble("temp_max") - 273.15);
                    temp_min = (int) (main.getDouble("temp_min") - 273.15);

                    JSONObject sys = response.getJSONObject("sys");

                    country = sys.getString("country");


                    JSONArray weather = response.getJSONArray("weather");

                    JSONObject jsonObject = weather.getJSONObject(0);

                    description = jsonObject.getString("description");
                    icon = jsonObject.getString("icon");
                    System.out.println("Image-Icon"+icon);

                    
                    // getting icon from  url
                    String icon_url = "http://openweathermap.org/img/w/" + icon + ".png";

                    System.out.println("Image response "+icon_url);
                    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();



                    place_tv.setText(name + "," + country);

                    temp_tv.setText(temp + "\u2103");
                    min_max_tv.setText(temp_min + "\u2103 /" + temp_max + "\u2103");
                    desc_tv.setText(description);

                    iv.setImageUrl(icon_url, imageLoader);

                    forecast_btn.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    PD.dismiss();
                }

                PD.dismiss();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjReq, "jreq");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button1:

                PD = new ProgressDialog(MainActivity.this);
                PD.setMessage("Loading.....");
                PD.setCancelable(false);

                String city = cityname.getText().toString();

                String full_url = Current_URL +city+API_Key;

                makejsonreq(full_url);
                break;

            case R.id.forecast:

                Intent forecast_intent = new Intent(getApplicationContext(),Forecast.class);

                forecast_intent.putExtra("id", id);
                forecast_intent.putExtra("name", name);

                startActivity(forecast_intent);

                break;
        }

    }

}