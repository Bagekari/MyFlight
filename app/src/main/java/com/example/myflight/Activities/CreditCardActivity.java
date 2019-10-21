package com.example.myflight.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.myflight.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreditCardActivity extends AppCompatActivity {
    CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true).mobileNumberExplanation("SMS is required on this number")
                .setup(CreditCardActivity.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(CreditCardActivity.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card Number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(CreditCardActivity.this, "Thank you for purchase, you can view the ticket in Ticket List", Toast.LENGTH_LONG).show();
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference myRefDep = firebaseDatabase.getReference("Booked Flights").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
                            FlightItem depFlightItem = getIntent().getParcelableExtra("Flight Item Departure");
                            FlightItem retFlightItem = getIntent().getParcelableExtra("Flight Item Return");
                            String source = getIntent().getExtras().getString("Source");
                            String destination = getIntent().getExtras().getString("Destination");
                            if(getIntent().getExtras().getBoolean("Flag")) {
                                TicketListItem ticketListItem = new TicketListItem(R.drawable.round_trip,
                                        depFlightItem.getTextPrice(),
                                        source,
                                        destination,
                                        depFlightItem.getTextAirline(),
                                        retFlightItem.getTextPrice(),
                                        destination,
                                        source,
                                        retFlightItem.getTextAirline());
                                myRefDep.setValue(ticketListItem);
                            }
                            else {
                                TicketListItem ticketListItem = new TicketListItem(R.drawable.ic_home,
                                        depFlightItem.getTextPrice(),
                                        source,
                                        destination,
                                        depFlightItem.getTextAirline(),
                                        null,
                                        null,
                                        null,
                                        null);
                                myRefDep.setValue(ticketListItem);
                            }
                            finish();
//                            startActivity(new Intent(CreditCardActivity.this, TicketListFragment.class));
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(CreditCardActivity.this, "Please complete the form", Toast.LENGTH_SHORT).show();
                }
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
