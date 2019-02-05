package com.example.jeremy.logisticwizard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        configureBackButton();
        configureSubButton();
    }

    private void configureBackButton() {
        /* Configures back button to go back to LoginActivity
         */
        Button nextButton = (Button)findViewById(R.id.toLoginFromReg);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void configureSubButton() {
        /* Configures the Submit button to parse information into the database and go back to
        LoginActivity
         */
        Button subButton = (Button)findViewById(R.id.subBtn);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}