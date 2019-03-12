package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;

public class workorder_main extends AppCompatActivity implements View.OnClickListener {
    Button newOrder;
    RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    String[] workOrders = new String[] { "Order 1", "Order 2", "Order 3" };
    String[] Priorities = new String[] { "High", "Medium", "Low" };
    String[] Dates = new String[] { "3/11/2019", "2/19/2019", "12/21/2019" };
    String[] Creators = new String[] { "Carl", "Bill", "Raymond" };
    String[] Progress = new String[] { "Open", "In Progress", "Hold" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_main);

        //newOrder = findViewById(R.id.new_order);
        //newOrder.setOnClickListener(this);

        // Code for initializing RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // Calls LinearLayoutManager for use on the recycler
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Specify the adapter
        mAdapter = new RecyclerViewAdapter(workOrders, Priorities, Dates, Creators, Progress, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == newOrder) {
            Intent intent = new Intent(view.getContext(), workorder_add.class);
            startActivity(intent);
        }
    }
}
