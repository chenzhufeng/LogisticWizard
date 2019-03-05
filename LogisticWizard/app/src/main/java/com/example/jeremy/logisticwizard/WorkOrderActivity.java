package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private Button newOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order);

        newOrder = findViewById(R.id.new_order);
        newOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == newOrder) {
            Intent intent = new Intent(view.getContext(), add_a_order.class);
            startActivity(intent);
        }
    }
}
