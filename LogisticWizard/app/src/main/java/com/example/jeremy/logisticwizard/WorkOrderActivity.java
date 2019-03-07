package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.graphics.Outline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;

public class WorkOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private Button newOrder;
    private View bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order);

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0,0, view.getWidth(), view.getHeight());
            }
        };

        newOrder = findViewById(R.id.new_order);
        newOrder.setOnClickListener(this);
        bar = findViewById(R.id.order_bar);
        bar.setOutlineProvider(viewOutlineProvider);
        bar.setClipToOutline(true);
    }

    @Override
    public void onClick(View view) {
        if (view == newOrder) {
            Intent intent = new Intent(view.getContext(), add_a_order.class);
            startActivity(intent);
        }
    }
}
