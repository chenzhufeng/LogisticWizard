package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class machine_edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String machineName;
    String machineName2;
    String machineDescp;
    String machinePrice;
    String machineLocat;
    String machineType;
    String machineParts;
    String maintainPlan;
    String machineQuant;
    List<String> temple=new ArrayList<>();
    private EditText name;
    private EditText type;
    private EditText part;
    private EditText price;
    private EditText location;
    private Spinner machinePlanSpinner;
    private Spinner machineQuantitySpinner;
    private EditText description;
    protected DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("machines");;

    private Button save;
    private View.OnClickListener saveOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            saveInfo();
            Intent machine_intent = new Intent(v.getContext(), machine_disp.class);
            machine_intent.putExtra("machineName", machineName2);
            machine_intent.putExtra("machineDescription", machineDescp);
            machine_intent.putExtra("machinePrice", machinePrice);
            machine_intent.putExtra("machineLocation", machineLocat);
            machine_intent.putExtra("machineType", machineType);
            machine_intent.putExtra("machineParts", machineParts);
            machine_intent.putExtra("maintainencePlan", maintainPlan);
            machine_intent.putExtra("machineQuant", machineQuant);
            startActivity(machine_intent);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_edit);

        machineQuantitySpinner = findViewById(R.id.quantityMachineSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineQuantitySpinner.setAdapter(adapter);
        machineQuantitySpinner.setOnItemSelectedListener(this);


        machinePlanSpinner = findViewById(R.id.maintenancePlanSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.machinePlanStringArray, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machinePlanSpinner.setAdapter(adapter1);
        machinePlanSpinner.setOnItemSelectedListener(this);

        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();

        machineName = (String)data.get("machineName");
        machineDescp = (String)data.get("machineDescription");
        machinePrice = (String)data.get("machinePrice");
        machineLocat = (String)data.get("machineLocation");
        machineType = (String)data.get("machineType");
        machineParts = (String)data.get("machineParts");
        maintainPlan = (String)data.get("maintainencePlan");
        machineQuant = (String)data.get("machineQuant");

        int machinePlan2 = Integer.parseInt(maintainPlan);

        name = findViewById(R.id.nameText);
        type = findViewById(R.id.typeText);
        part = findViewById(R.id.partsText);
        price = findViewById(R.id.priceText);
        location = findViewById(R.id.locationText);
        description = findViewById(R.id.descriptionText);

        //make save button onclickable
        save = findViewById(R.id.saveButton);

        name.setText(machineName);
        type.setText(machineType);
        part.setText(machineParts);
        price.setText(machinePrice);
        location.setText(machineLocat);
        description.setText(machineDescp);
        machinePlanSpinner.setSelection(machinePlan2);

        save.setOnClickListener(saveOnClickListener);



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void saveInfo(){
        machineName2 = name.getText().toString().trim();
        machineDescp = description.getText().toString().trim();
        machinePrice = price.getText().toString().trim();
        machineLocat = location.getText().toString().trim();
        machineType = type.getText().toString().trim();
        machineParts = part.getText().toString().trim();
        maintainPlan = machinePlanSpinner.getSelectedItem().toString().trim();
        //machineQuant = machineQuantitySpinner.getSelectedItem().toString().trim();

        if (machineName2.equals("")||machineDescp.equals("")||machinePrice.equals("")||machineLocat.equals("")
                ||machineType.equals("")||machineParts.equals("")||maintainPlan.equals("")||machineQuant.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        }else {
            if(!machineName2.equals(machineName)) {
                mDatabase.child(machineName).removeValue();
            }

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                        if(!machineSnapshot.getKey().equals(machineName)){
                            temple.add(machineSnapshot.getKey());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if(temple.contains(machineName2)){
                Toast.makeText(machine_edit.this,
                        "Machine already exists, please enter a new name.", Toast.LENGTH_LONG).show();
                return;

            }
            else {
                machine_info machine = new machine_info(machineName2, machineDescp, machinePrice, machineLocat,
                        machineType, machineParts, maintainPlan, machineQuant);
                mDatabase.child(machineName2).setValue(machine);
            }

        }
    }

}
