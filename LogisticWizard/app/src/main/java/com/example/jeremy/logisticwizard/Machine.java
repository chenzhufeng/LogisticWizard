package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Machine extends AppCompatActivity implements View.OnClickListener{
    protected DatabaseReference mDatabase;
    private Button add_machine;
    private SearchView sv;
    private ListView lv;
    private DatabaseReference machineRef;

    //just for now
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final ArrayList<String> listData = new ArrayList<String>();
        listData.add("sample data 1");
        listData.add("sample data 2");
        listData.add("sample data 3");
        listData.add("sample data 4");
        listData.add("sample data 5");

        sv = (SearchView) findViewById(R.id.machine_search);
        lv = (ListView) findViewById(R.id.list_of_machines); //will need this later
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        lv.setAdapter(adapter);

        add_machine = (Button) findViewById(R.id.add_machine_button);
        add_machine.setOnClickListener(this);

        // https://www.youtube.com/watch?v=H3JAy94UFw0
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });

        // https://stackoverflow.com/questions/30455723/android-make-whole-search-bar-clickable
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.setIconified(false);
            }
        });

    }

    public void add_machine (View view){
        Intent add_machine_intent = new Intent(view.getContext() , add_a_machine.class);
        startActivityForResult(add_machine_intent, 21);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK) {
            String machineName = data.getStringExtra("machineName");
            Toast.makeText(this, "machine name"+machineName+"lalal", Toast.LENGTH_SHORT).show();
            saveMachineToDB(machineName);
        }
    }

    private void saveMachineToDB(String machineName) {
        final String machine_Name = machineName;
        //currentUserID = mAuthSetting.getCurrentUser().getUid();
//        machineRef = FirebaseDatabase.getInstance().getReference().child("machines");
//        //userRef2 = userRef.child("comments").child(Rest_ID);
//        machineRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Log.i("snapshot", "Inside onDataChange!!!");
            machine_info machine = new machine_info(machine_Name);
            mDatabase.child("machines").child(machine_Name).setValue(machine);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    public void onClick(View view){
        if (view == add_machine) {
            add_machine(view);
        }
    }
}
