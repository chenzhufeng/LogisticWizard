package com.example.jeremy.logisticwizard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class machine_history extends Activity implements View.OnClickListener {

    Button new_order_button;
    ListView order_view;
    View v1;
    View top;
    SearchView sv;
    RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<workorder_info> machine_info_list;
    private String machine_name;

    protected DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_main);

        order_view = findViewById(R.id.order_list);
        top = findViewById(R.id.top_view);
        sv = findViewById(R.id.search_workorders);

        machine_info_list = new ArrayList<>();

        // Code for initializing RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // Calls LinearLayoutManager for use on the recycler
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), machine_info_list));

        //get database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("orders");

        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();
        machine_name = (String)data.get("machineName");

    }

    @Override
    protected void onStart() {
        super.onStart();

        new_order_button = (Button) findViewById(R.id.new_order);
        new_order_button.setVisibility(View.INVISIBLE);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if (workorder_infoList != null){
                machine_info_list.clear();
                //}
                for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                    workorder_info workorder = machineSnapshot.getValue(workorder_info.class);
                    if(machine_name.equals(workorder.order_machine)&&(!workorder.order_status.equals("Open"))) {
                        machine_info_list.add(workorder);
                    }
                }
                // Specify the adapter
                mAdapter = new RecyclerViewAdapter(getApplicationContext(), machine_info_list);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {

    }

}