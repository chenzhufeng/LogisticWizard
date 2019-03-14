package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


public class workorder_main extends AppCompatActivity implements View.OnClickListener {
    protected DatabaseReference mDatabase;
    Button newOrder;
    RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<workorder_info> workorder_infoList;
    RecyclerView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_main);


        workorder_infoList = new ArrayList<>();
        // Code for initializing RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // Calls LinearLayoutManager for use on the recycler
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        //lv = (RecyclerView) findViewById(R.id.recycler_view); //will need this later

        mDatabase = FirebaseDatabase.getInstance().getReference("orders");

        newOrder = findViewById(R.id.new_order);
        newOrder.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workorder_infoList.clear();
                for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                    workorder_info workorder = machineSnapshot.getValue(workorder_info.class);
                    workorder_infoList.add(workorder);
                }
                //Toast.makeText(Machine.this, machine_infoList.get(0).machine_name+machine_infoList.get(1).machine_name, Toast.LENGTH_SHORT).show();
                //Toast.makeText(workorder_main.this, workorder_infoList.get(0).order_creator, Toast.LENGTH_SHORT).show();
                // Specify the adapter
                mAdapter = new RecyclerViewAdapter(getApplicationContext(), workorder_infoList);
                recyclerView.setAdapter(mAdapter);
                //lv.setAdapter(machineinfoAdapter);
                //});
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 29 && resultCode == RESULT_OK) {

            String order_title = data.getStringExtra("orderTitle");
            String order_description = data.getStringExtra("orderDescription");
            String order_note = data.getStringExtra("orderNote");
            String order_DueDate = data.getStringExtra("orderDueDate");
            String order_cost = data.getStringExtra("orderCate");
            String order_priority = data.getStringExtra("orderPriority");
            String order_plan = data.getStringExtra("maintainencePlan");
            String order_status = data.getStringExtra("orderStatus");
            String order_image = data.getStringExtra("orderImage");
            String order_creator = data.getStringExtra("orderCreator");

            Toast.makeText(this, "order name"+order_title+"lalal", Toast.LENGTH_SHORT).show();
            saveorderToDB(order_title, order_description, order_note, order_DueDate,
                    order_cost, order_priority, order_plan, order_status, order_image, order_creator);
        }
    }


    private void saveorderToDB(String order_title, String order_description, String order_note,
                               String order_DueDate, String order_cost, String order_priority,
                               String order_plan, String order_status, String order_image, String order_creator ) {
        workorder_info order = new workorder_info(order_title, order_description, order_note, order_DueDate,
                order_cost, order_priority, order_plan, order_status, order_image, order_creator);
        mDatabase.child(order_title).setValue(order);

    }



    @Override
    public void onClick(View view) {
        if (view == newOrder) {
            Intent intent = new Intent(view.getContext(), workorder_add.class);
            startActivityForResult(intent, 29);
        }
    }
}
