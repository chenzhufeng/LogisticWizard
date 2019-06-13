package com.example.jeremy.logisticwizard.Profile;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.jeremy.logisticwizard.R;
import com.example.jeremy.logisticwizard.Custom_object.user_info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class profile_edit extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter_userInfo mAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<user_info> user_infoList;
    String Uid;
    public Button save;

    protected DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        user_infoList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter_userInfo(getApplicationContext(), user_infoList));


        mDatabase = FirebaseDatabase.getInstance().getReference("users");

    }

    public void onStart() {
        super.onStart();
        mDatabase.keepSynced(true);

        //Get all users' info from database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (user_infoList != null){
                    user_infoList.clear();
                }
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    user_info user = userSnapshot.getValue(user_info.class);
                    user_infoList.add(user);
                }
                // Specify the adapter
                mAdapter = new RecyclerViewAdapter_userInfo(getApplicationContext(), user_infoList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
