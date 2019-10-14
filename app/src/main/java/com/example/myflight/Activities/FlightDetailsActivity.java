package com.example.myflight.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
        String source = getIntent().getExtras().getString("Source");
        String destination = getIntent().getExtras().getString("Destination");
        String departureDate = getIntent().getExtras().getString("Departure Date");
        boolean flag = getIntent().getExtras().getBoolean("Flag");

        TextView tvPriceRes = findViewById(R.id.tvPriceDetails);
        tvPriceRes.setText(priceRes);
        TextView tvAirlineRes = findViewById(R.id.tvAirlinesName);
        tvAirlineRes.setText(airlineRes);
        TextView tvSource = findViewById(R.id.tvFromDetails);
        tvSource.setText(source);
        TextView tvDestination = findViewById(R.id.tvToDetails);
        tvDestination.setText(destination);
        TextView tvDepartureDate = findViewById(R.id.tvDepDateDetails);
        tvDepartureDate.setText(departureDate);

        if (flag) {
            findViewById(R.id.tvRetFlightInfo).setVisibility(View.VISIBLE);
            findViewById(R.id.textView17).setVisibility(View.VISIBLE);
            findViewById(R.id.textView18).setVisibility(View.VISIBLE);
            findViewById(R.id.textView19).setVisibility(View.VISIBLE);
            findViewById(R.id.textView20).setVisibility(View.VISIBLE);
            findViewById(R.id.textView21).setVisibility(View.VISIBLE);
            findViewById(R.id.textView22).setVisibility(View.VISIBLE);
            findViewById(R.id.tvRetFlightDuration).setVisibility(View.VISIBLE);
            findViewById(R.id.tvRetFlightTime).setVisibility(View.VISIBLE);
            String returnDate = getIntent().getExtras().getString("Return Date");
            TextView tvRetFromRes = findViewById(R.id.tvRetFromDetails);
            tvRetFromRes.setVisibility(View.VISIBLE);
            tvRetFromRes.setText(destination);
            TextView tvRetToRes = findViewById(R.id.tvRetToDetails);
            tvRetToRes.setVisibility(View.VISIBLE);
            tvRetToRes.setText(source);
            TextView tvRetPriceRes = findViewById(R.id.tvRetPriceDetails);
            TextView tvRetDateRes = findViewById(R.id.tvRetDateDetails);
            tvRetPriceRes.setVisibility(View.VISIBLE);
            tvRetDateRes.setVisibility(View.VISIBLE);
            tvRetDateRes.setText(returnDate);
            tvRetPriceRes.setText(priceRes);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
