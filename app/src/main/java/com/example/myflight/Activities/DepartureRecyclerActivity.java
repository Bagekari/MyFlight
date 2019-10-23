package com.example.myflight.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myflight.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DepartureRecyclerActivity extends AppCompatActivity implements FlightDepAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private FlightDepAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private String s, d;
    ArrayList<FlightItem> flightList;
    private static final String TAG = "DepartureRecyclerAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure_recycler);
        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog.setTitle("Searching Departure Flights");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        s = getIntent().getExtras().getString("Source");
        d = getIntent().getExtras().getString("Destination");
        databaseReference = FirebaseDatabase.getInstance().getReference("Flights");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    list.add(dataSnapshot1.getKey());
                flightList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++)
                    flightList.add(new FlightItem(dataSnapshot.child(list.get(i)).child("Price").getValue().toString(),
                            s + " : " + dataSnapshot.child(list.get(i)).child("Departure Time").getValue().toString(),
                            d + " : " + dataSnapshot.child(list.get(i)).child("Arrival Time").getValue().toString(),
                            list.get(i)));
                mRecyclerView = findViewById(R.id.recyclerViewDep);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(DepartureRecyclerActivity.this);
                mAdapter = new FlightDepAdapter(flightList, DepartureRecyclerActivity.this);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.hide();
                Toast.makeText(DepartureRecyclerActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(DepartureRecyclerActivity.this, ReturnRecyclerActivity.class);
        intent.putExtra("Flight Item", flightList.get(position));
        intent.putExtra("Source", s);
        intent.putExtra("Destination", d);
        intent.putExtra("Departure Date", getIntent().getExtras().getString("Departure Date"));
        intent.putExtra("Return Date", getIntent().getExtras().getString("Return Date"));
        startActivity(intent);
        finish();
    }
}
