package com.example.myflight.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myflight.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity implements FlightAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private FlightAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private String s, d;
    ArrayList<FlightItem> flightList;
    private static final String TAG = "RecyclerViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog.setTitle("Searching");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        s = getIntent().getExtras().getString("Source");
        d = getIntent().getExtras().getString("Destination");
        databaseReference = FirebaseDatabase.getInstance().getReference("Flights");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flightList = new ArrayList<>();
                flightList.add(new FlightItem(dataSnapshot.child("Air Berlin").child("Price").getValue().toString(),
                        s + " : " + dataSnapshot.child("Air Berlin").child("Departure Time").getValue().toString(),
                        d + " : " + dataSnapshot.child("Air Berlin").child("Arrival Time").getValue().toString(),
                        "Air Berlin"));
                flightList.add(new FlightItem(dataSnapshot.child("Air Russia").child("Price").getValue().toString(),
                        s + " : " + dataSnapshot.child("Air Russia").child("Departure Time").getValue().toString(),
                        d + " : " + dataSnapshot.child("Air Russia").child("Arrival Time").getValue().toString(),
                        "Air Russia"));
                flightList.add(new FlightItem(dataSnapshot.child("Turkish Airlines").child("Price").getValue().toString(),
                        s + " : " + dataSnapshot.child("Turkish Airlines").child("Departure Time").getValue().toString(),
                        d + " : " + dataSnapshot.child("Turkish Airlines").child("Arrival Time").getValue().toString(),
                        "Turkish Airlines"));
                mRecyclerView = findViewById(R.id.recyclerView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(RecyclerViewActivity.this);
                mAdapter = new FlightAdapter(flightList, RecyclerViewActivity.this);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.hide();
                Toast.makeText(RecyclerViewActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: clicked");
        Intent intent = new Intent(RecyclerViewActivity.this, FlightDetailsActivity.class);
        intent.putExtra("Flight Item", flightList.get(position));
        startActivity(intent);
    }
}
