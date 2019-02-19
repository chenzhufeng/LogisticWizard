package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainPage extends AppCompatActivity implements View.OnClickListener{
    private Button assetsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        assetsButton = findViewById(R.id.assetsButton);
        assetsButton.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        if (v == assetsButton) {
            Intent intent = new Intent(v.getContext(), Machine.class);
            startActivity(intent);

        }
    }
}
