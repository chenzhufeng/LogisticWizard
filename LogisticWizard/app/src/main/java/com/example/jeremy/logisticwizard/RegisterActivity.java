package com.example.jeremy.logisticwizard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;


public class RegisterActivity extends AppCompatActivity {
    private EditText getEmail;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getEmail = (EditText) findViewById(R.id.enterEmail);
    }

}

