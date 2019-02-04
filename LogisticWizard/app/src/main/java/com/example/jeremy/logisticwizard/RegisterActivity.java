package com.example.jeremy.logisticwizard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
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

