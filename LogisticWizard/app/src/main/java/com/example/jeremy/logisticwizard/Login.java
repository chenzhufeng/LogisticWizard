package com.example.jeremy.logisticwizard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button SignupButton;
    private Button LoginButton;
    private EditText user_name;
    private EditText password;
    private TextView forgetPwd;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog2;
    private FirebaseAuth mAuth;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(8);
        setContentView(R.layout.login);

        //invoking Button
        SignupButton=(Button)findViewById(R.id.SignUpBut);
        SignupButton.setOnClickListener(this);
        LoginButton = (Button)findViewById(R.id.LoginBut);
        LoginButton.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        user_name = (EditText)findViewById(R.id.UserName);
        password = (EditText) findViewById(R.id.Password);

        forgetPwd = findViewById(R.id.ForgetPwdText);
        forgetPwd.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void UserLogin(){
        String infoEmail = user_name.getText().toString().trim();
        String infoPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(infoEmail)) {
            Toast.makeText(this, "Please enter an email !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(infoPassword)) {
            Toast.makeText(this, "Please enter password !", Toast.LENGTH_SHORT).show();
            return;
        }
        // let user to see the process of login
        //function to receive user email and password with firebase

        mAuth.signInWithEmailAndPassword(infoEmail, infoPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog2.dismiss();
                        if (task.isSuccessful()) {
                            //*
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String Uid = user.getUid();
                                getRole(Uid);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            String grab_error = task.getException().getMessage();
                            Toast.makeText(Login.this,
                                    "Error occur:" + grab_error, Toast.LENGTH_SHORT).show();
                        }
                        //progressDialog2.dismiss();

                    }
                });
    }

    private void getRole(String Uid) {
        mDatabase.child(Uid).child("Role").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String role = dataSnapshot.getValue(String.class);
                if (role.equals("None")) {
                    Toast.makeText(Login.this, "Your role has not yet been set", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Login.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), home_page.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Override
    public void onClick(View view) {
        if (view == SignupButton) {
            Intent intent = new Intent(view.getContext(), Register.class);
            startActivity(intent);

        }
        if (view == LoginButton) {
            UserLogin();
        }
        if (view == forgetPwd){
            finish();
            startActivity(new Intent(this, forget_password.class));
        }
    }
}
