package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Button subButton;
    private Button backButton;
    private DatabaseReference mDatabase;
    private EditText getusername;
    private EditText getpassword;
    private EditText getName;
    private EditText getPhone;
    private EditText getAddress;
    private boolean gonextpage;
    private  FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //connect to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // evoking the button and edittext from signup.xml and make them clickable
        subButton = (Button)findViewById(R.id.subBtn);
        subButton.setOnClickListener(this);

        backButton = (Button)findViewById(R.id.toLoginFromReg);
        backButton.setOnClickListener(this);

        getusername = (EditText)findViewById(R.id.enterUser);
        getpassword = (EditText)findViewById(R.id.enterPassword);
        getName = (EditText)findViewById(R.id.enterName);
        getPhone = (EditText)findViewById(R.id.enterPhone);
        getAddress = (EditText)findViewById(R.id.enterAddress);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    private boolean UserRegister(){
        String infoUsername = getusername.getText().toString().trim();
        String infoPassword = getpassword.getText().toString().trim();
        String infoName =  getName.getText().toString().trim();
        String infoPhone =  getPhone.getText().toString().trim();
        String infoAddress =  getAddress.getText().toString().trim();
        //if the user doesnot enter username
        if (TextUtils.isEmpty(infoUsername)) {
            Toast.makeText(this, "Please enter username !", Toast.LENGTH_SHORT).show();
            return false;
        }
        // if the user does not enter password
        if (TextUtils.isEmpty(infoPassword)) {
            Toast.makeText(this, "Please enter password !", Toast.LENGTH_SHORT).show();
            return false;
        }
        // if the user does not enter email
        if (TextUtils.isEmpty(infoName)) {
            Toast.makeText(this, "Please enter name !", Toast.LENGTH_SHORT).show();
            return false;
        }
        // if the user does not enter phone number
        if (TextUtils.isEmpty(infoPhone)) {
            Toast.makeText(this, "Please enter phone number !", Toast.LENGTH_SHORT).show();
            return false;
        }
        // if the user does not enter address
        if (TextUtils.isEmpty(infoAddress)) {
            Toast.makeText(this, "Please enter Address !", Toast.LENGTH_SHORT).show();
            return false;
        }
        push_data_into_firebase();
        return true;
    }

    //push user input information into firebase
    private void push_data_into_firebase() {
        final String infoUsername = getusername.getText().toString().trim();
        final String infoPassword = getpassword.getText().toString().trim();
        final String infoName = getName.getText().toString().trim();
        final String infoPhone = getPhone.getText().toString().trim();
        final String infoAddress = getAddress.getText().toString().trim();

        user_info user = new user_info(infoUsername, infoPassword, infoName, infoPhone, infoAddress);


        mDatabase.child("user").child(infoUsername).setValue(user);
//        FirebaseUser usertemp = mAuth.getCurrentUser();
//
//        if (usertemp != null) {
//            usertemp.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                }
//            });
//
//        return false;
    }


    @Override
    public void onClick(View view) {
        if (view == subButton) {
            gonextpage = UserRegister();
            if(gonextpage) {
                Toast.makeText(RegisterActivity.this, "Successfully submit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        }
        if (view == backButton){
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

}