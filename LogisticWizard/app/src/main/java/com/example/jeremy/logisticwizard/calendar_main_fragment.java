package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jeremy.logisticwizard.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class calendar_main_fragment extends Fragment {

    private CalendarView cv;
    private TextView t;
    ListView lv;
    ArrayAdapter<String> adapter;

    //new stuff
    ArrayList<String> machine_info_list;

    //added
    protected DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_main_fragment, container, false);
        cv = (CalendarView) v.findViewById(R.id.calendar);

        t = (TextView) v.findViewById(R.id.date_display);
        lv = (ListView) v.findViewById(R.id.events_for_day);
        machine_info_list = new ArrayList<String>();

        //get database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("orders");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy"); //may need mm/dd/yyyy
        final String formatDate = sdf.format(c.getTime());

        t.setText(formatDate);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if (workorder_infoList != null){
                machine_info_list.clear();
                //}
                for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                    workorder_info workorder = machineSnapshot.getValue(workorder_info.class);
                    if(formatDate.equals(workorder.order_dates)) {
                        machine_info_list.add(workorder.order_title);
                    }
                }
                adapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, machine_info_list);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // "work order" was clicked
                        machine_selected(i, machine_info_list, view);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //search database for today's date
        //display work orders for today's date

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                final String date = (i1+1) + "/" + i2 + "/" + i;
                t.setText(date);


                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //if (workorder_infoList != null){
                        machine_info_list.clear();
                        //}
                        for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                            workorder_info workorder = machineSnapshot.getValue(workorder_info.class);
                            if(date.equals(workorder.order_dates)) {
                                machine_info_list.add(workorder.order_title);
                            }
                        }
                        adapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, machine_info_list);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                // "work order" was clicked
                                machine_selected(i, machine_info_list, view);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return v;
    }


    public void machine_selected(int i, final ArrayList<String> machine_info_list, View view){
        Intent order_intent = new Intent(view.getContext(), workorder_view.class);
        order_intent.putExtra("orderTitle", machine_info_list.get(i));
        startActivity(order_intent);
    }
}
