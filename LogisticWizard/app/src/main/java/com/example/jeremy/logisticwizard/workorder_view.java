package com.example.jeremy.logisticwizard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class workorder_view extends AppCompatActivity implements View.OnClickListener {

    private TextView order_title;
    private TextView order_status;
    private TextView order_priority;
    private TextView order_Creator;
    private TextView order_description;
    private TextView order_cost;
    private TextView order_Duedate;
    private TextView order_note;

    private Button edit_button;
    private Button back_button;
    private Button delete_button;



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

        edit_button = findViewById(R.id.editButton);
        edit_button.setOnClickListener(this);

        back_button=findViewById(R.id.backButton);
        back_button.setOnClickListener(this);

        delete_button=findViewById(R.id.deleteButton);
        delete_button.setOnClickListener(this);


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

    private void delete_order(){
        mDatabase.child(orderTitle).removeValue();
    }

    private void show_dialogue(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(workorder_view.this);
        normalDialog.setTitle("Delete the work order?");
        //normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete_order();
                    }
                });
        normalDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        return;
                    }
                });
        // 显示
        normalDialog.show();
    }



    @Override
    public void onClick(View v) {
        if(v == edit_button){
            //Intent intent = new Intent (v.getContext(), workorder_main.class);
            //startActivity(intent);
        }else if( v== back_button){
            Intent intent = new Intent (v.getContext(), workorder_main.class);
            startActivity(intent);
        }else if ( v==delete_button) {
            show_dialogue();
            Intent intent = new Intent (v.getContext(), workorder_main.class);
            startActivity(intent);
        }
    }
}
