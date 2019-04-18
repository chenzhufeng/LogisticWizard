package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class workorder_view extends AppCompatActivity {

    private TextView order_title;
    private TextView order_status;
    private TextView order_priority;
    private TextView order_Creator;
    private TextView order_description;
    private TextView order_cost;
    private TextView order_Duedate;
    private TextView order_note;

    String orderTitle;

    protected DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_disp);
        mDatabase  = FirebaseDatabase.getInstance().getReference("orders");
        order_title = findViewById(R.id.titleText);
        order_Creator = findViewById(R.id.creatorHolder);
        order_status =findViewById(R.id.currentStatusText);
        order_priority=findViewById(R.id.currentPriorityText);
        order_description=findViewById(R.id.descriptionInput);
        order_cost=findViewById(R.id.priceText);
        order_Duedate=findViewById(R.id.editText2);
        order_note=findViewById(R.id.note);

        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();

        orderTitle = data.get("orderTitle").toString();



    }

    protected void onStart(){
        super.onStart();

        order_title.setText(orderTitle);
        mDatabase.child(orderTitle).child("order_status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderStatus;
                orderStatus = (String) dataSnapshot.getValue();
                order_status.setText(orderStatus);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_priority").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderPriority;
                orderPriority = (String) dataSnapshot.getValue();
                order_priority.setText(orderPriority);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_creator").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderCreator;
                orderCreator = (String) dataSnapshot.getValue();
                order_Creator.setText(orderCreator);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_descrip").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderDescrip;
                orderDescrip = (String) dataSnapshot.getValue();
                order_description.setText(orderDescrip);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_cost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderCost;
                orderCost = (String) dataSnapshot.getValue();
                order_cost.setText(orderCost);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_dates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderDuedate;
                orderDuedate = (String) dataSnapshot.getValue();
                order_Duedate.setText(orderDuedate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_note").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderNote;
                orderNote = (String) dataSnapshot.getValue();
                order_note.setText(orderNote);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});




    }
}
