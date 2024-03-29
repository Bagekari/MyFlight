package com.example.myflight.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myflight.R;

import java.util.Calendar;
import java.util.Objects;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    private TextView mDepDate, mRetDate, roundTrip, returnTextView, oneWayTextView;
    private DatePickerDialog.OnDateSetListener mDateDepListener, mDateRetListener;
    private Spinner spinnerFrom, spinnerTo;
    private boolean flag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mDepDate = view.findViewById(R.id.tvDepDate);
        mRetDate = view.findViewById(R.id.tvRetDate);
        roundTrip = view.findViewById(R.id.tvRoundTrip);
        returnTextView = view.findViewById(R.id.tvReturn);
        oneWayTextView = view.findViewById(R.id.tvOneWay);
        spinnerFrom = view.findViewById(R.id.spinnerFrom);
        spinnerTo = view.findViewById(R.id.spinnerTo);
        Button btnSearch = view.findViewById(R.id.searchBtn);

        ArrayAdapter<CharSequence> adapterFrom = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.Countries, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterTo = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.Countries, android.R.layout.simple_spinner_item);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(adapterTo);
        spinnerFrom.setAdapter(adapterFrom);
        spinnerTo.setOnItemSelectedListener(this);
        spinnerFrom.setOnItemSelectedListener(this);


        final ColorStateList colors = oneWayTextView.getTextColors();
        oneWayTextView.setTextColor(Color.BLUE);

        mDepDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateDepListener, year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mRetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateRetListener, year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateDepListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDepDate.setText(date);
            }
        };

        mDateRetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: " + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mRetDate.setText(date);
            }
        };

        oneWayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRetDate.setVisibility(View.GONE);
                returnTextView.setVisibility(View.GONE);
                oneWayTextView.setTextColor(Color.BLUE);
                roundTrip.setTextColor(colors);
                flag = false;
            }
        });

        roundTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRetDate.setVisibility(View.VISIBLE);
                returnTextView.setVisibility(View.VISIBLE);
                roundTrip.setTextColor(Color.BLUE);
                oneWayTextView.setTextColor(colors);
                flag = true;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String source_country = spinnerFrom.getSelectedItem().toString();
                String destination_country = spinnerTo.getSelectedItem().toString();
                String text = mDepDate.getText().toString().trim();
                if (source_country.equals(destination_country))
                    Toast.makeText(getActivity(), "Please enter a valid source and destination", Toast.LENGTH_SHORT).show();
                else {
                    if (text.isEmpty())
                        Toast.makeText(getActivity(), "Please enter the departure date", Toast.LENGTH_SHORT).show();
                    else {
                        if (flag) {
                            String string = mRetDate.getText().toString().trim();
                            if (string.isEmpty())
                                Toast.makeText(getActivity(), "Please enter the return date", Toast.LENGTH_SHORT).show();
                            else {
                                Intent intent = new Intent(Objects.requireNonNull(getActivity()), DepartureRecyclerActivity.class);
                                intent.putExtra("Source", source_country);
                                intent.putExtra("Destination", destination_country);
                                intent.putExtra("Departure Date", mDepDate.getText().toString());
                                intent.putExtra("Return Date", mRetDate.getText().toString());
                                startActivity(intent);
                            }
                        } else {
                            Intent intent = new Intent(Objects.requireNonNull(getActivity()), RecyclerViewActivity.class);
                            intent.putExtra("Source", source_country);
                            intent.putExtra("Destination", destination_country);
                            intent.putExtra("Departure Date", mDepDate.getText().toString());
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
