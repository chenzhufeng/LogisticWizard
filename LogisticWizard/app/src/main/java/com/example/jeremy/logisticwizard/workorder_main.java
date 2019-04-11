package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;
import java.util.Arrays;

import static com.example.jeremy.logisticwizard.R.*;

public class workorder_main extends AppCompatActivity implements View.OnClickListener {
    Button newOrder;
    SearchView sv;
    View v1;
    View top;
    ListView order_view;
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
        setContentView(layout.workorder_main);

        v1 = (View) findViewById(id.list_view);
        order_view = (ListView) findViewById(id.order_list);
        top = (View) findViewById(id.top_view);
        sv = (SearchView) findViewById(id.search_workorders);
        newOrder = (Button) findViewById(R.id.new_order);

        //newOrder.setOnClickListener(this);

        // Code for initializing RecyclerView
        recyclerView = findViewById(id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // Calls LinearLayoutManager for use on the recycler
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Specify the adapter
        mAdapter = new RecyclerViewAdapter(workOrders, Priorities, Dates, Creators, Progress, getApplicationContext());
        recyclerView.setAdapter(mAdapter);

        BottomNavigationView bottomNav  = findViewById(id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(0).setCheckable(false);
    }

    @Override
    public void onClick(View view) {
        if (view == newOrder) {
            Intent intent = new Intent(view.getContext(), workorder_add.class);
            startActivity(intent);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case id.nav_home:
                            //menuItem.setCheckable(true);
                            Intent intent = new Intent(workorder_main.this, home_page.class);
                            startActivity(intent);
                            //selectedFragment = new HomeFragment();
                            break;
                        case id.nav_orders:
                            v1.setVisibility(View.INVISIBLE);
                            order_view.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                            top.setVisibility(View.INVISIBLE);
                            newOrder.setVisibility(View.INVISIBLE);
                            sv.setVisibility(View.INVISIBLE);

                            //setContentView(R.layout.calendar_main_fragment);
                            //recyclerView.setVisibility(View.GONE);
                            //need other layouts

                            menuItem.setCheckable(true);
                            selectedFragment = new calendar_main_fragment();
                            getSupportFragmentManager().beginTransaction().replace(id.fragment_container,
                                    selectedFragment).commit();
                            break;
                        case id.nav_profile:


                            v1.setVisibility(View.INVISIBLE);
                            order_view.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                            top.setVisibility(View.INVISIBLE);
                            newOrder.setVisibility(View.INVISIBLE);
                            sv.setVisibility(View.INVISIBLE);
                            //need other layouts

                            menuItem.setCheckable(true);
                            selectedFragment = new profile_main_fragment();
                            getSupportFragmentManager().beginTransaction().replace(id.fragment_container,
                                    selectedFragment).commit();
                            break;
                    }
                    return true; //return clicked item
                }

            };


}
