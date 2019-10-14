package com.example.myflight.Activities;

import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myflight.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.map, fragment);
        transaction.commit();
        fragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getActivity(), "Map Ready!", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        databaseReference = FirebaseDatabase.getInstance().getReference("Countries");
        final LatLng[] ll = new LatLng [6];
        final String[] titles = new String[]{"Long Island", "Washington DC", "New York", "San Antonio", "Philadelphia", "Toronto"};

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ll[0] = new LatLng(Double.parseDouble(dataSnapshot.child("Long Island").child("Latitude").getValue().toString()), Double.parseDouble(dataSnapshot.child("Long Island").child("Longitude").getValue().toString()));
                ll[1] = new LatLng(Double.parseDouble(dataSnapshot.child("Washington DC").child("Latitude").getValue().toString()), Double.parseDouble(dataSnapshot.child("Washington DC").child("Longitude").getValue().toString()));
                ll[2] = new LatLng(Double.parseDouble(dataSnapshot.child("New York").child("Latitude").getValue().toString()), Double.parseDouble(dataSnapshot.child("New York").child("Longitude").getValue().toString()));
                ll[3] = new LatLng(Double.parseDouble(dataSnapshot.child("San Antonio").child("Latitude").getValue().toString()), Double.parseDouble(dataSnapshot.child("San Antonio").child("Longitude").getValue().toString()));
                ll[4] = new LatLng(Double.parseDouble(dataSnapshot.child("Philadelphia").child("Latitude").getValue().toString()), Double.parseDouble(dataSnapshot.child("Philadelphia").child("Longitude").getValue().toString()));
                ll[5] = new LatLng(Double.parseDouble(dataSnapshot.child("Toronto").child("Latitude").getValue().toString()), Double.parseDouble(dataSnapshot.child("Toronto").child("Longitude").getValue().toString()));
                for(int i = 0; i < 6; i++){
                    mMap.addMarker(new MarkerOptions().position(ll[i]).title(titles[i]));
                    //new MarkerOptions().position(sydney).title("Marker in Sydney")
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(ll[2]));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(7.0f));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
