package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home_page extends AppCompatActivity implements View.OnClickListener{
    private Button assetsButton;
    private Button workOrdersButton;
    private Button tools;
    private Button profile;
    private Button calendar;
    public static String role;
    public static String name;

    protected DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        // *** !!!IMPORTANT!!! ***  --> must cite the calendar interface symbol
        // Place the attribution on the credits/description page of the application.
        // <div>Icons made by <a href="https://www.freepik.com/" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" 			    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 			    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

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
        tools.setOnClickListener(this);
        profile.setOnClickListener(this);
        calendar.setOnClickListener(this);
        workOrdersButton = findViewById(R.id.workOrdersButton);
        workOrdersButton.setOnClickListener(this);
        getUserRole();
        getUserName();
    }




    @Override
    public void onClick(View v) {

        // *** could implement better this way ***
        //switch (v.getId()){
        //    case R.id.workOrdersButton:
        //        break;
        //}
        if (v == workOrdersButton) {
            Intent intent = new Intent(v.getContext(), workorder_main.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            //overridePendingTransition(0, 0);
        }
        if (v == assetsButton) {
            Intent intent = new Intent(v.getContext(), machine_main.class);
            startActivity(intent);
            //overridePendingTransition(0, 0);
        }
        if(v == tools) {
            Intent intent = new Intent(v.getContext(), tool_main.class);
            startActivity(intent);
        }
        if (v == profile) {
            Intent intent = new Intent(v.getContext(), profile_main.class);
            startActivity(intent);
        }
        if (v == calendar) {
            Intent intent = new Intent(v.getContext(), calendar_main.class);
            startActivity(intent);
        }
    }

    /* Retrieves the user ID from Firebase as a string. Is used to determine the
     * permissions of the current user and bring up the appropriate dialogs and
     * activities. */
    private void getUserRole() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String Uid = mAuth.getCurrentUser().getUid();
            mDatabase.child(Uid).child("Role").addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            role = (String) dataSnapshot.getValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        }
    }

    private void getUserName() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String Uid = mAuth.getCurrentUser().getUid();
            mDatabase.child(Uid).child("Name").addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            name = (String) dataSnapshot.getValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
        }
    }
}
