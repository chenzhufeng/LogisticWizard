package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainPage extends AppCompatActivity implements View.OnClickListener{
    private Button assetsButton;
    private Button workOrdersButton;
    private Button tools;
    private Button profile;
    private Button calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        workOrdersButton = (Button) findViewById(R.id.workOrdersButton);
        assetsButton = (Button) findViewById(R.id.assetsButton);
        tools = (Button) findViewById(R.id.toolsButton);
        profile = (Button) findViewById(R.id.profileButton);
        calendar = (Button) findViewById(R.id.calenderButton);

        assetsButton.setOnClickListener(this);
        workOrdersButton.setOnClickListener(this);
        tools.setOnClickListener(this);
        profile.setOnClickListener(this);
        calendar.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {

        // *** could implement better this way ***
        //switch (v.getId()){
        //    case R.id.workOrdersButton:
        //        break;
        //}
        if (v == assetsButton) {
            Intent intent = new Intent(v.getContext(), Machine.class);
            startActivity(intent);
        }

        if (v == workOrdersButton) {
            Intent intent = new Intent(v.getContext(), WorkOrderActivity.class);
        }
        if(v == tools) {
            Intent intent = new Intent(v.getContext(), tools.class);
            startActivity(intent);
        }
        if (v == profile) {
            Intent intent = new Intent(v.getContext(), profile.class);
            startActivity(intent);
        }
        if (v == calendar) {
            Intent intent = new Intent(v.getContext(), Calendar.class);
            startActivity(intent);
        }
    }
}
