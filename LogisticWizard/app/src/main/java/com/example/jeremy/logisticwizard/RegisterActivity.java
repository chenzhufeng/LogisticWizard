package com.example.jeremy.logisticwizard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    private EditText getEmail;
    private Button SubmitBtn;
    private DatabaseReference mDatabase;
    //private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //mAuth = FirebaseAuth.getInstance();
        getEmail = (EditText) findViewById(R.id.enterEmail);
        SubmitBtn = (Button) findViewById(R.id.button2);
        SubmitBtn.setOnClickListener((View.OnClickListener) this);
    }

    private void UserRegister(){
        String UserEmail = getEmail.getText().toString().trim();
        mDatabase.child("users").child("email").setValue(UserEmail);
    }


    public void onClick(View view) {
        if(view == SubmitBtn){
           UserRegister();
        }
    }

}

