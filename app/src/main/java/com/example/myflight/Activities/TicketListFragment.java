package com.example.myflight.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myflight.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TicketListFragment extends Fragment implements TicketListAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private TicketListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    private ArrayList<DataSnapshot> list = new ArrayList<>();
    private ArrayList<TicketListItem> ticketList;
    private int p;
    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ticket_list, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Fetching your booked tickets");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Booked Flights").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ticketList = new ArrayList<>();
                try {
                    if (!dataSnapshot.hasChildren())
                        Toast.makeText(getActivity(), "You have not booked any tickets yet", Toast.LENGTH_LONG).show();
                    else {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if (dataSnapshot1.child("retListPrice").getValue() == null && dataSnapshot.child("retListDep").getValue() == null && dataSnapshot1.child("retListRet").getValue() == null && dataSnapshot1.child("retListAirline").getValue() == null)
                                ticketList.add(new TicketListItem(Integer.parseInt(dataSnapshot1.child("imageResource").getValue().toString()),
                                        dataSnapshot1.child("depListPrice").getValue().toString(),
                                        dataSnapshot1.child("depListDep").getValue().toString(),
                                        dataSnapshot1.child("depListRet").getValue().toString(),
                                        dataSnapshot1.child("depListAirline").getValue().toString(),
                                        null,
                                        null,
                                        null,
                                        null));
                            else {
                                ticketList.add(new TicketListItem(Integer.parseInt(dataSnapshot1.child("imageResource").getValue().toString()),
                                        dataSnapshot1.child("depListPrice").getValue().toString(),
                                        dataSnapshot1.child("depListDep").getValue().toString(),
                                        dataSnapshot1.child("depListRet").getValue().toString(),
                                        dataSnapshot1.child("depListAirline").getValue().toString(),
                                        dataSnapshot1.child("retListPrice").getValue().toString(),
                                        dataSnapshot1.child("retListDep").getValue().toString(),
                                        dataSnapshot1.child("retListRet").getValue().toString(),
                                        dataSnapshot1.child("retListAirline").getValue().toString()));
                            }
                        }
                    }
                } catch (NullPointerException e) {
//                    Toast.makeText(getActivity(), "You deleted the last ticket", Toast.LENGTH_LONG).show();
                }

                mRecyclerView = view.findViewById(R.id.rvTicketList);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mAdapter = new TicketListAdapter(ticketList, TicketListFragment.this);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.hide();
                Toast.makeText(getActivity(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onDeleteClick(int position) {
        p = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm delete");
        builder.setMessage("Are you sure you want to delete this ticket?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ticketList.remove(p);
                mAdapter.notifyItemRemoved(p);
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Booked Flights").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference = databaseReference1;
                list.clear();
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            list.add(snapshot);
                        }
                        reference.child(list.get(p).getKey()).removeValue();
                        if (list.size() == 1)
                            Toast.makeText(getActivity(), "You cancelled your last ticket", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Ticket cancelled successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
