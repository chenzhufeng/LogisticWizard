package com.example.jeremy.logisticwizard;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Button subButton;
    private Button backButton;
    private DatabaseReference mDatabase;
    private EditText getusername;
    private EditText getpassword;
    private EditText getEmail;
    private EditText getPhone;
    private EditText getAddress;
    private boolean gonextpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        subButton = (Button)findViewById(R.id.subBtn);
        subButton.setOnClickListener(this);

        backButton = (Button)findViewById(R.id.toLoginFromReg);
        backButton.setOnClickListener(this);

        getusername = (EditText)findViewById(R.id.enterEmail);
        getpassword = (EditText)findViewById(R.id.enterPassword);
        getEmail = (EditText)findViewById(R.id.enterEmail);
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
        String infoEmail =  getEmail.getText().toString().trim();
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
        if (TextUtils.isEmpty(infoEmail)) {
            Toast.makeText(this, "Please enter email !", Toast.LENGTH_SHORT).show();
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
    private boolean push_data_into_firebase(){

        return false;
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