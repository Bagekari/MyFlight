package com.example.myflight.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.myflight.R;

public class FlightDetailsActivity extends AppCompatActivity {

    private static final String TAG = "FlightDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onCreate: called");
        Intent intent = getIntent();
        FlightItem flightItem = intent.getParcelableExtra("Flight Item");
        String priceRes = flightItem.getTextPrice();
        String airlineRes = flightItem.getTextAirline();
        String depDisplayRes = flightItem.getTextDep();
        String retDisplayRes = flightItem.getTextRet();

        TextView priceText = findViewById(R.id.text_price);
        priceText.setText(priceRes);
        TextView airlineText = findViewById(R.id.text_airline);
        airlineText.setText(airlineRes);
        TextView depDisplayText = findViewById(R.id.text_dep_display);
        depDisplayText.setText(depDisplayRes);
        TextView retDisplayText = findViewById(R.id.text_ret_display);
        retDisplayText.setText(retDisplayRes);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
