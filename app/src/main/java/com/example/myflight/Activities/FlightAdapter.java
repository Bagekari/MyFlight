package com.example.myflight.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myflight.R;

import java.util.ArrayList;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
    private ArrayList<FlightItem> mFlightList;

    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        public TextView priceTV, depDisplayTV, retDisplayTV, airlineTV;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            priceTV = itemView.findViewById(R.id.tvPrice);
            depDisplayTV = itemView.findViewById(R.id.tvDepDisplay);
            retDisplayTV = itemView.findViewById(R.id.tvRetDisplay);
            airlineTV = itemView.findViewById(R.id.tvAirline);
        }
    }

    public FlightAdapter(ArrayList<FlightItem> flightList) {
        mFlightList = flightList;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
        FlightViewHolder fvh = new FlightViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        FlightItem currentItem = mFlightList.get(position);

        holder.airlineTV.setText(currentItem.getTextAirline());
        holder.priceTV.setText(currentItem.getTextPrice());
        holder.depDisplayTV.setText(currentItem.getTextDep());
        holder.retDisplayTV.setText(currentItem.getTextRet());
    }

    @Override
    public int getItemCount() {
        return mFlightList.size();
    }
}
