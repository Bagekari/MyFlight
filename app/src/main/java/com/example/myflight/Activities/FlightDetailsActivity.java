package com.example.myflight.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
        final FlightItem depFlightItem = intent.getParcelableExtra("Flight Item Departure");
        final FlightItem retFlightItem = intent.getParcelableExtra("Flight Item Return");;
        String priceRes = depFlightItem.getTextPrice();
        String airlineRes = depFlightItem.getTextAirline();
        final String source = getIntent().getExtras().getString("Source");
        final String destination = getIntent().getExtras().getString("Destination");
        String departureDate = getIntent().getExtras().getString("Departure Date");
        final boolean flag = getIntent().getExtras().getBoolean("Flag");

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
        Button pay_btn = findViewById(R.id.btnPayment);

        if (flag) {
            findViewById(R.id.tvRetFlightInfo).setVisibility(View.VISIBLE);
            findViewById(R.id.textView17).setVisibility(View.VISIBLE);
            findViewById(R.id.textView18).setVisibility(View.VISIBLE);
            findViewById(R.id.textView19).setVisibility(View.VISIBLE);
            findViewById(R.id.textView20).setVisibility(View.VISIBLE);
            findViewById(R.id.textView21).setVisibility(View.VISIBLE);
            findViewById(R.id.textView22).setVisibility(View.VISIBLE);
            findViewById(R.id.textView8).setVisibility(View.VISIBLE);
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
            TextView tvRetAirlinesNameRes = findViewById(R.id.tvRetAirlinesName);
            tvRetAirlinesNameRes.setVisibility(View.VISIBLE);
            tvRetAirlinesNameRes.setText(retFlightItem.getTextAirline());
        }

        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FlightDetailsActivity.this, CreditCardActivity.class);
                intent1.putExtra("Flight Item Departure", depFlightItem);
                intent1.putExtra("Flight Item Return", retFlightItem);
                intent1.putExtra("Source", source);
                intent1.putExtra("Destination", destination);
                intent1.putExtra("Flag", flag);
                startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
