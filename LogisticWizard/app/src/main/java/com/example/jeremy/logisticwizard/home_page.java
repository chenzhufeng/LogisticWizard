package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class home_page extends AppCompatActivity implements View.OnClickListener {
    //*
    private Button assetsButton;
    private Button workOrdersButton;
    private Button tools;
    private Button profile;
    private Button calendar;
    //*/
    public static String role;
    public static String name;
    protected DatabaseReference mDatabase;
    private GridLayout gl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        getUserRole();
        getUserName();
        gl = (GridLayout) findViewById(R.id.gridlayout);
        setSingleEvent(gl);
    }

    protected void onStart() {
        super.onStart();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();


                        Toast.makeText(home_page.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

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
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        }
    }

    private void setSingleEvent(GridLayout mainGrid){
        for (int i = 0; i<mainGrid.getChildCount(); i++){
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 0){
                        Intent intent = new Intent(view.getContext(), workorder_main.class);
                        startActivity(intent);
                    }
                    if (finalI == 1){
                        Intent intent = new Intent(view.getContext(), workorder_add.class);
                        startActivity(intent);
                    }
                    if (finalI == 2){
                        Intent intent = new Intent(view.getContext(), machine_main.class);
                        startActivity(intent);
                    }
                    if (finalI == 3){
                        Intent intent = new Intent(view.getContext(), tool_main.class);
                        startActivity(intent);
                    }
                    if (finalI == 4){
                        Intent intent = new Intent(view.getContext(), profile_main.class);
                        startActivity(intent);
                    }
                    if (finalI == 5){
                        Intent intent = new Intent(view.getContext(), calendar_main.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {}
}
