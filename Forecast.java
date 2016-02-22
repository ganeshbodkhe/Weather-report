package com.example.ganesh.weatherreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Shalu on 2/6/2016.
 */
public class Forecast extends Activity {

    TextView Cityname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forcast);

        Cityname=(TextView)findViewById(R.id.cityname);

        Intent i = getIntent();
      Integer place_id = i.getExtras().getInt("id");
      String  place_name = i.getExtras().getString("name");

       Cityname.setText(place_name);

//        TextView id=new TextView(this);
//        id.setText(place_id);
//        setContentView(id);
//
//        TextView name=new TextView(this);
//        name.setText(place_name);
//        setContentView(name);

    }
}
