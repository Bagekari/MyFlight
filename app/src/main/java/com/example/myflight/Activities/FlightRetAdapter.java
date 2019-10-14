package com.example.myflight.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myflight.R;

import java.util.ArrayList;

public class FlightRetAdapter extends RecyclerView.Adapter<FlightRetAdapter.FlightRetViewHolder> {
    private ArrayList<FlightItem> mFlightList;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class FlightRetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView priceTV, depDisplayTV, retDisplayTV, airlineTV;
        OnItemClickListener onItemClickListener;

        public FlightRetViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            priceTV = itemView.findViewById(R.id.tvPrice_circle2);
            depDisplayTV = itemView.findViewById(R.id.tvRetDisplay_circle2);
            retDisplayTV = itemView.findViewById(R.id.tvDepDisplay_circle2);
            airlineTV = itemView.findViewById(R.id.tvAirline_circle2);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public FlightRetAdapter(ArrayList<FlightItem> flightList, OnItemClickListener onItemClickListener) {
        mFlightList = flightList;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FlightRetAdapter.FlightRetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item_ret2, parent, false);
        return new FlightRetAdapter.FlightRetViewHolder(v, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightRetAdapter.FlightRetViewHolder holder, int position) {
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
