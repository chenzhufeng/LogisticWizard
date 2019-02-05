package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import com.example.jeremy.logisticwizard.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button SignupButton;
    private Button LoginButton;
    private EditText user_name;
    private EditText password;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(8);
        setContentView(R.layout.activity_login);

        //invoking Button
        SignupButton=(Button)findViewById(R.id.SignUpBut);
        SignupButton.setOnClickListener(this);
        LoginButton = (Button)findViewById(R.id.LoginBut);
        LoginButton.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        user_name = (EditText)findViewById(R.id.UserName);
        password = (EditText) findViewById(R.id.Password);


        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    //Toast.makeText(LoginActivity.this, "Press Enter", Toast.LENGTH_SHORT).show();
                    String user_name_s = user_name.getText().toString();
                    final String password_s = password.getText().toString();
                    mDatabase.child(user_name_s).child("password").addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                                if(password_s.equals(dataSnapshot.getValue())){
                                    Toast.makeText(LoginActivity.this, "Login succeed", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(LoginActivity.this, password_s, Toast.LENGTH_SHORT).show();
                                }

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "No permission", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return false;
            }
        });



        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }


    @Override
    protected void onStart() {
        super.onStart();
        //View decorView = getWindow().getDecorView();
        // Hide the status bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        //decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    @Override
    public void onClick(View view) {
        if (view == SignupButton) {
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            startActivity(intent);

        }
        if (view == LoginButton) {
            String user_name_s = user_name.getText().toString();
            final String password_s = password.getText().toString();
            mDatabase.child(user_name_s).child("password").addValueEventListener(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(password_s.equals(dataSnapshot.getValue())){
                        Toast.makeText(LoginActivity.this, "Login succeed", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "No permission", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
