package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class workorder_main extends AppCompatActivity implements View.OnClickListener {
    protected DatabaseReference mDatabase;
    private Button newOrder;
    ArrayList<machine_info> workorder_infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_main);

        mDatabase = FirebaseDatabase.getInstance().getReference("orders");

        newOrder = findViewById(R.id.new_order);
        newOrder.setOnClickListener(this);
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

            Toast.makeText(this, "order name"+order_title+"lalal", Toast.LENGTH_SHORT).show();
            saveorderToDB(order_title, order_description, order_note, order_DueDate,
                    order_cost, order_priority, order_plan, order_status, order_image);
        }
    }


    private void saveorderToDB(String order_title, String order_description, String order_note, String order_DueDate,
                                 String order_cost, String order_priority, String order_plan, String order_status, String order_image) {
        workorder_info order = new workorder_info(order_title, order_description, order_note, order_DueDate,
                order_cost, order_priority, order_plan, order_status, order_image);
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
