package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class machine_main_fragment extends Fragment {
    private Button addMachine;
    protected DatabaseReference mDatabase;
    //private Button add_machine;
    private SearchView sv;
    private ListView lv;
    //private View machineBar = (View) findViewById(R.id.machine_bar);
    ArrayList<machine_info> machine_infoList;
    //private View machineBar = (View) findViewById(R.id.machine_bar);

    //just for now
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.machine_main_fragment, container, false);

        // just added
        mDatabase = FirebaseDatabase.getInstance().getReference("machines");
        mDatabase = FirebaseDatabase.getInstance().getReference("machines");

        //no custom outline

        sv = (SearchView) v.findViewById(R.id.machine_search);
        lv = (ListView) v.findViewById(R.id.list_of_machines); //will need this later
        machine_infoList = new ArrayList<>();


        final FragmentManager fragmentManager = getFragmentManager();
        addMachine = (Button) v.findViewById(R.id.add_machine_button);
        addMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new machine_add_fragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

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
        return v;

    }

    @Override
    public void onStart() { //made public
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                machine_infoList.clear();
                for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                    machine_info machine = machineSnapshot.getValue(machine_info.class);
                    machine_infoList.add(machine);
                }
                //Toast.makeText(machine_main_fragment.this, machine_infoList.get(0).machine_name+machine_infoList.get(1).machine_name, Toast.LENGTH_SHORT).show();

                //************* NEED ADAPTER HERE **************
                //MachineinfoAdapter machineinfoAdapter = new MachineinfoAdapter(machine_main.this,
                //        machine_infoList);
                //lv.setAdapter(machineinfoAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // "machine" was clicked
                        machine_selected(i, machine_infoList, view);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //change to fragment
    public void machine_selected(int i, ArrayList<machine_info> machine_infoList, View view){
        String machineName = machine_infoList.get(i).machine_name;
        String machineDescp = machine_infoList.get(i).machine_descrip;
        String machinePrice = machine_infoList.get(i).machine_price;
        String machineLocat = machine_infoList.get(i).machine_location;
        String machineType = machine_infoList.get(i).machine_type;
        String machineParts = machine_infoList.get(i).machine_parts;
        String maintainPlan = machine_infoList.get(i).maintain_plan;
        String machineQuant = machine_infoList.get(i).machine_quant;

        final FragmentManager fragmentManager = getFragmentManager();
        Fragment selectedFragment = new profile_main_fragment(); //using profile as a test
        Bundle args = new Bundle();
        args.putString("key1", machineName);
        args.putString("key2", machineDescp);
        args.putString("key3", machinePrice);
        args.putString("key4", machineLocat);
        args.putString("key5", machineType);
        args.putString("key6", machineParts);
        args.putString("key7", maintainPlan);
        args.putString("key8", machineQuant);
        selectedFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit();

//        Intent machine_intent = new Intent(view.getContext(), machine_disp.class);
//        machine_intent.putExtra("machineName", machineName);
//        machine_intent.putExtra("machineDescription", machineDescp);
//        machine_intent.putExtra("machinePrice", machinePrice);
//        machine_intent.putExtra("machineLocation", machineLocat);
//        machine_intent.putExtra("machineType", machineType);
//        machine_intent.putExtra("machineParts", machineParts);
//        machine_intent.putExtra("maintainencePlan", maintainPlan);
//        machine_intent.putExtra("machineQuant", machineQuant);
//        startActivity(machine_intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //removed @nullable
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21) { // removed && resultCode == RESULT_OK
            String machineName = data.getStringExtra("machineName");
            String machineDescription = data.getStringExtra("machineDescription");
            String machinePrice = data.getStringExtra("machinePrice");
            String machineLocation = data.getStringExtra("machineLocation");
            String machineType = data.getStringExtra("machineType");
            String machineParts = data.getStringExtra("machineParts");
            String machinePlan = data.getStringExtra("maintainencePlan");
            String machineQuant = data.getStringExtra("machineQuant");
            //Toast.makeText(this, "machine name"+machineName+"lalal", Toast.LENGTH_SHORT).show();
            saveMachineToDB(machineName, machineDescription, machinePrice, machineLocation,
                    machineType, machineParts, machinePlan, machineQuant);
        }
    }


    private void saveMachineToDB(String machineName, String machineDescription, String machinePrice, String machineLocation,
                                 String machineType, String machineParts, String machinePlan, String machineQuant) {
        //final String machine_Name = machineName;
        //currentUserID = mAuthSetting.getCurrentUser().getUid();
//        machineRef = FirebaseDatabase.getInstance().getReference().child("machines");
//        //userRef2 = userRef.child("comments").child(Rest_ID);
//        machineRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // Log.i("snapshot", "Inside onDataChange!!!");
        machine_info machine = new machine_info(machineName, machineDescription, machinePrice, machineLocation,
                machineType, machineParts, machinePlan, machineQuant);
        mDatabase.child(machineName).setValue(machine);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }




    /*
        mDatabase = FirebaseDatabase.getInstance().getReference("machines");
        mDatabase = FirebaseDatabase.getInstance().getReference("machines");

        //       CustomOutlineView customOutline = new CustomOutlineView(2, 2);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            machineBar.setOutlineProvider(customOutline);
//        }

        final ArrayList<String> listData = new ArrayList<String>();

        sv = (SearchView) findViewById(R.id.machine_search);
        lv = (ListView) findViewById(R.id.list_of_machines); //will need this later
        machine_infoList = new ArrayList<>();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        //lv.setAdapter(adapter);

        add_machine = (Button) findViewById(R.id.add_machine_button);
        add_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_machine_intent = new Intent(view.getContext() , machine_add.class);
                startActivity(add_machine_intent);
                //finish();
            }
        });
        add_machine.setOnClickListener(this);
        add_machine.setOnClickListener(this);

        // https://www.youtube.com/watch?v=H3JAy94UFw0
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                adapter.getFilter().filter(s);
//                return false;
//            }
//        });
//
//        // https://stackoverflow.com/questions/30455723/android-make-whole-search-bar-clickable
//        sv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sv.setIconified(false);
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                machine_infoList.clear();
                for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                    machine_info machine = machineSnapshot.getValue(machine_info.class);
                    machine_infoList.add(machine);
                }
                //Toast.makeText(machine_main_fragment.this, machine_infoList.get(0).machine_name+machine_infoList.get(1).machine_name, Toast.LENGTH_SHORT).show();
                MachineinfoAdapter machineinfoAdapter = new MachineinfoAdapter(machine_main.this,
                        machine_infoList);
                lv.setAdapter(machineinfoAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // "machine" was clicked
                        machine_selected(i, machine_infoList, view);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void machine_selected(int i, ArrayList<machine_info> machine_infoList, View view){
        String machineName = machine_infoList.get(i).machine_name;
        String machineDescp = machine_infoList.get(i).machine_descrip;
        String machinePrice = machine_infoList.get(i).machine_price;
        String machineLocat = machine_infoList.get(i).machine_location;
        String machineType = machine_infoList.get(i).machine_type;
        String machineParts = machine_infoList.get(i).machine_parts;
        String maintainPlan = machine_infoList.get(i).maintain_plan;
        String machineQuant = machine_infoList.get(i).machine_quant;

        Intent machine_intent = new Intent(view.getContext(), machine_disp.class);
        machine_intent.putExtra("machineName", machineName);
        machine_intent.putExtra("machineDescription", machineDescp);
        machine_intent.putExtra("machinePrice", machinePrice);
        machine_intent.putExtra("machineLocation", machineLocat);
        machine_intent.putExtra("machineType", machineType);
        machine_intent.putExtra("machineParts", machineParts);
        machine_intent.putExtra("maintainencePlan", maintainPlan);
        machine_intent.putExtra("machineQuant", machineQuant);
        startActivity(machine_intent);
    }



    public void add_machine (View view){
        Intent add_machine_intent = new Intent(view.getContext() , machine_add.class);
        startActivityForResult(add_machine_intent, 21);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK) {
            String machineName = data.getStringExtra("machineName");
            String machineDescription = data.getStringExtra("machineDescription");
            String machinePrice = data.getStringExtra("machinePrice");
            String machineLocation = data.getStringExtra("machineLocation");
            String machineType = data.getStringExtra("machineType");
            String machineParts = data.getStringExtra("machineParts");
            String machinePlan = data.getStringExtra("maintainencePlan");
            String machineQuant = data.getStringExtra("machineQuant");
            Toast.makeText(this, "machine name"+machineName+"lalal", Toast.LENGTH_SHORT).show();
            saveMachineToDB(machineName, machineDescription, machinePrice, machineLocation,
                    machineType, machineParts, machinePlan, machineQuant);
        }
    }


    private void saveMachineToDB(String machineName, String machineDescription, String machinePrice, String machineLocation,
                                 String machineType, String machineParts, String machinePlan, String machineQuant) {
        //final String machine_Name = machineName;
        //currentUserID = mAuthSetting.getCurrentUser().getUid();
//        machineRef = FirebaseDatabase.getInstance().getReference().child("machines");
//        //userRef2 = userRef.child("comments").child(Rest_ID);
//        machineRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // Log.i("snapshot", "Inside onDataChange!!!");
        machine_info machine = new machine_info(machineName, machineDescription, machinePrice, machineLocation,
                machineType, machineParts, machinePlan, machineQuant);
        mDatabase.child(machineName).setValue(machine);
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
    */


}
