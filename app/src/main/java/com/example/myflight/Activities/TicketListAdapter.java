package com.example.myflight.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myflight.R;

import java.util.ArrayList;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketListViewHolder> {
    private ArrayList<TicketListItem> mticketListItems;

    public static class TicketListViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewDepPrice, mTextViewDepAirline, mTextViewDepSource, mTextViewDepDestination;
        public TextView mTextViewRetPrice, mTextViewRetAirline, mTextViewRetSource, mTextViewRetDestination, mTextViewRetInfo;

        public TicketListViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.tlImageView);
            mTextViewDepPrice = itemView.findViewById(R.id.tlDepPrice);
            mTextViewDepAirline = itemView.findViewById(R.id.tlDepAirline);
            mTextViewDepSource = itemView.findViewById(R.id.tlDepSource);
            mTextViewDepDestination = itemView.findViewById(R.id.tlDepDestination);
            mTextViewRetPrice = itemView.findViewById(R.id.tlRetPrice);
            mTextViewRetAirline = itemView.findViewById(R.id.tlRetAirline);
            mTextViewRetSource = itemView.findViewById(R.id.tlRetSource);
            mTextViewRetDestination = itemView.findViewById(R.id.tlRetDestination);
            mTextViewRetInfo = itemView.findViewById(R.id.infoRetDisplay);
        }
    }

    public TicketListAdapter(ArrayList<TicketListItem> ticketListItems) {
        mticketListItems = ticketListItems;
    }

    @NonNull
    @Override
    public TicketListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        return new TicketListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketListViewHolder holder, int position) {
        TicketListItem currentItem = mticketListItems.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextViewDepPrice.setText(currentItem.getDepListPrice());
        holder.mTextViewDepAirline.setText(currentItem.getDepListAirline());
        holder.mTextViewDepSource.setText(currentItem.getDepListDep());
        holder.mTextViewDepDestination.setText(currentItem.getDepListRet());
        if (currentItem.getRetListAirline() == null && currentItem.getRetListDep() == null && currentItem.getRetListPrice() == null && currentItem.getRetListRet() == null) {
            holder.mTextViewRetInfo.setVisibility(View.GONE);
            holder.mTextViewRetPrice.setVisibility(View.GONE);
            holder.mTextViewRetAirline.setVisibility(View.GONE);
            holder.mTextViewRetSource.setVisibility(View.GONE);
            holder.mTextViewRetDestination.setVisibility(View.GONE);
        }
        else {
            holder.mTextViewRetInfo.setVisibility(View.VISIBLE);
            holder.mTextViewRetPrice.setVisibility(View.VISIBLE);
            holder.mTextViewRetAirline.setVisibility(View.VISIBLE);
            holder.mTextViewRetSource.setVisibility(View.VISIBLE);
            holder.mTextViewRetDestination.setVisibility(View.VISIBLE);

            holder.mTextViewRetPrice.setText(currentItem.getRetListPrice());
            holder.mTextViewRetAirline.setText(currentItem.getRetListAirline());
            holder.mTextViewRetSource.setText(currentItem.getRetListDep());
            holder.mTextViewRetDestination.setText(currentItem.getRetListRet());
        }
    }

    @Override
    public int getItemCount() {
        return mticketListItems.size();
    }
}
