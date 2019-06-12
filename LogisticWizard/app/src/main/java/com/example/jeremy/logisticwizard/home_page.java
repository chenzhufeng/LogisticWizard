package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.jeremy.logisticwizard.Calendar.calendar_main;
import com.example.jeremy.logisticwizard.Custom_object.workorder_info;
import com.example.jeremy.logisticwizard.Machine.machine_main;
import com.example.jeremy.logisticwizard.Profile.profile_main;
import com.example.jeremy.logisticwizard.Tool.tool_main;
import com.example.jeremy.logisticwizard.Work_order.workorder_add;
import com.example.jeremy.logisticwizard.Work_order.workorder_add_standard;
import com.example.jeremy.logisticwizard.Work_order.workorder_main;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home_page extends AppCompatActivity implements View.OnClickListener {
    //*
    private Button assetsButton;
    private Button workOrdersButton;
    private Button tools;
    private Button profile;
    private Button calendar;
    //*/
    public static String role;
    public static String name;
    protected DatabaseReference mDatabase;
    private GridLayout gl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        getUserRole();
        getUserName();
        gl = (GridLayout) findViewById(R.id.gridlayout);
        setSingleEvent(gl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        DatabaseReference orderDatabase = FirebaseDatabase.getInstance().getReference("orders");
        workorder_info order = new workorder_info(order_title, order_description, order_note, order_DueDate,
                order_cost, order_priority, order_plan, order_status, order_image, order_creator, order_machine, maintenance_worker);
        Toast.makeText(this, "on save to db!"+order, Toast.LENGTH_LONG).show();
        orderDatabase.child(order_title).setValue(order);

    }

    /* Retrieves the user ID from Firebase as a string. Is used to determine the
     * permissions of the current user and bring up the appropriate dialogs and
     * activities. */
    private void getUserRole() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String Uid = mAuth.getCurrentUser().getUid();
            mDatabase.child(Uid).child("Role").addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            role = (String) dataSnapshot.getValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        }
    }

    private void getUserName() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String Uid = mAuth.getCurrentUser().getUid();
            mDatabase.child(Uid).child("Name").addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            name = (String) dataSnapshot.getValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        }
    }

    private void setSingleEvent(GridLayout mainGrid){
        for (int i = 0; i<mainGrid.getChildCount(); i++){
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 0){
                        Intent intent = new Intent(view.getContext(), workorder_main.class);
                        startActivity(intent);
                    }
                    if (finalI == 1){
                        if (!role.equals("Facility Worker")) {
                            //*/
                            Intent intent = new Intent(view.getContext(), workorder_add.class);
                            startActivityForResult(intent, 29);
                            //*
                        } else {
                            Intent intent = new Intent(view.getContext(), workorder_add_standard.class);
                            startActivityForResult(intent, 29);
                        }
                        //Intent intent = new Intent(view.getContext(), workorder_add.class);
                        //startActivity(intent);
                    }
                    if (finalI == 2){
                        Intent intent = new Intent(view.getContext(), machine_main.class);
                        startActivity(intent);
                    }
                    if (finalI == 3){
                        Intent intent = new Intent(view.getContext(), tool_main.class);
                        startActivity(intent);
                    }
                    if (finalI == 4){
                        Intent intent = new Intent(view.getContext(), profile_main.class);
                        startActivity(intent);
                    }
                    if (finalI == 5){
                        Intent intent = new Intent(view.getContext(), calendar_main.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {}
}
