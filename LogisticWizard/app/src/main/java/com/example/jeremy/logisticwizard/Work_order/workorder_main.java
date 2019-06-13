package com.example.jeremy.logisticwizard.Work_order;

import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.example.jeremy.logisticwizard.Calendar.calendar_main_fragment;
import com.example.jeremy.logisticwizard.Custom_object.workorder_info;
import com.example.jeremy.logisticwizard.R;
import com.example.jeremy.logisticwizard.home_page;
import com.example.jeremy.logisticwizard.Profile.profile_main_fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.jeremy.logisticwizard.R.*;

public class workorder_main extends AppCompatActivity implements View.OnClickListener {

    Button newOrder;
    View v1;
    View top;
    ListView order_view;
    RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<workorder_info> workorder_infoList;
    TextView new_order_button_text;
    private String role;

    protected DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialization of the activity
        super.onCreate(savedInstanceState);
        setContentView(layout.workorder_main);
        v1 = (View) findViewById(id.list_view);
        order_view = (ListView) findViewById(id.order_list);
        top = (View) findViewById(id.top_view);
        newOrder = (Button) findViewById(R.id.new_order);
        newOrder.setOnClickListener(this);
        new_order_button_text = findViewById(id.button_text);
        new_order_button_text.setOnClickListener(this);

        workorder_infoList = new ArrayList<>();

        // Code for initializing RecyclerView
        recyclerView = findViewById(id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), workorder_infoList));

        // Get role of current user from Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String Uid = mAuth.getCurrentUser().getUid();
            mDatabase.child(Uid).child("Role").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    role = (String) dataSnapshot.getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        mDatabase = FirebaseDatabase.getInstance().getReference("orders");
        BottomNavigationView bottomNav  = findViewById(id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(0).setCheckable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if (workorder_infoList != null){
                    workorder_infoList.clear();
                //}
                for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                    workorder_info workorder = machineSnapshot.getValue(workorder_info.class);
                    workorder_infoList.add(workorder);
                }
                // Specify the adapter
                mAdapter = new RecyclerViewAdapter(getApplicationContext(), workorder_infoList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Get data from workorder_add
        if (requestCode == 29 && resultCode == RESULT_OK) {
            if (data != null) {
                String order_title = data.getStringExtra("orderTitle");
                String order_description = data.getStringExtra("orderDescription");
                String order_note = data.getStringExtra("orderNote");
                String order_DueDate = data.getStringExtra("orderDueDate");
                String order_cost = data.getStringExtra("orderCost");
                String order_priority = data.getStringExtra("orderPriority");
                String order_plan = data.getStringExtra("maintainencePlan");
                String order_status = data.getStringExtra("orderStatus");
                String order_image = data.getStringExtra("orderImage");
                String order_creator = data.getStringExtra("orderCreator");
                String order_machine = data.getStringExtra("orderMachine");
                String maintenance_worker = data.getStringExtra("maintenanceWorker");

                saveorderToDB(order_title, order_description, order_note, order_DueDate,
                        order_cost, order_priority, order_plan, order_status, order_image,
                        order_creator, order_machine, maintenance_worker);
            }
        }
    }


    private void saveorderToDB(String order_title, String order_description, String order_note,
                               String order_DueDate, String order_cost, String order_priority,
                               String order_plan, String order_status, String order_image, String order_creator,
                               String order_machine, String maintenance_worker) {
        workorder_info order = new workorder_info(order_title, order_description, order_note, order_DueDate,
                order_cost, order_priority, order_plan, order_status, order_image, order_creator, order_machine, maintenance_worker);
        mDatabase.child(order_title).setValue(order);

    }

    @Override
    public void onClick(View view) {
        if (view == newOrder) {

            if (!role.equals("Facility Worker")) {

                Intent intent = new Intent(view.getContext(), workorder_add.class);
                startActivityForResult(intent, 29);

            } else {
                Intent intent = new Intent(view.getContext(), workorder_add_standard.class);
                startActivityForResult(intent, 29);
            }

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case id.nav_home:
                            Intent intent = new Intent(workorder_main.this, home_page.class);
                            startActivity(intent);
                            break;
                        case id.nav_orders:
                            v1.setVisibility(View.INVISIBLE);
                            order_view.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                            top.setVisibility(View.INVISIBLE);
                            newOrder.setVisibility(View.INVISIBLE);
                            new_order_button_text.setVisibility(View.INVISIBLE);


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
                            new_order_button_text.setVisibility(View.INVISIBLE);

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