package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;




public class add_a_machine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button add_machine;
    private EditText machine_name;
    private EditText machine_type;
    private EditText machine_parts;
    private EditText machine_descrip;
    private EditText machine_price;
    private EditText machine_location;
    private Spinner machinePlanSpinner;
    private Spinner machineQuantitySpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_machine);
        add_machine = (Button)findViewById(R.id.add_machine_button);
        machine_name = (EditText)findViewById(R.id.enterMachineName);
        machine_type = (EditText)findViewById(R.id.machineType);
        machine_parts = (EditText)findViewById(R.id.partsInfo);
        machine_descrip = (EditText)findViewById(R.id.enterMachineDescription);
        machine_price = (EditText)findViewById(R.id.machinePrice);
        machine_location = (EditText)findViewById(R.id.machineLocation);



        machineQuantitySpinner = findViewById(R.id.quantity_of_machine);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineQuantitySpinner.setAdapter(adapter);
        machineQuantitySpinner.setOnItemSelectedListener(this);

        machinePlanSpinner = findViewById(R.id.maintenancePlan_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.machinePlanStringArray, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machinePlanSpinner.setAdapter(adapter1);
        machinePlanSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == add_machine){
            addMachine();
            //Intent intent = new Intent(view.getContext(),Machine.class);
            //startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * the user wants to add the information of a new machine
     * return all information as a string to Inventory(include onSuccess Code)
     *
     */
    public void addMachine(){
        String machineName = machine_name.getText().toString().trim();
        String machineDescp = machine_descrip.getText().toString().trim();
        String machinePrice = machine_price.getText().toString().trim();
        String machineLocat = machine_location.getText().toString().trim();
        String machineType = machine_type.getText().toString().trim();
        String machineParts = machine_parts.getText().toString().trim();
        String maintainPlan = machinePlanSpinner.getSelectedItem().toString().trim();
        String machineQuant = machineQuantitySpinner.getSelectedItem().toString().trim();

//        if (machineName.equals("")||machineDescp.equals("")||machinePrice.equals("")||machineLocat.equals("")||maintainPlan.equals("")) {
//            Toast.makeText(this,
//                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
//            return;
//        }

        Intent machine_intent = new Intent();
        machine_intent.putExtra("machineName", machineName);
        machine_intent.putExtra("machineDescription", machineDescp);
        machine_intent.putExtra("machinePrice", machinePrice);
        machine_intent.putExtra("machineLocation", machineLocat);
        machine_intent.putExtra("machineType", machineType);
        machine_intent.putExtra("machineParts", machineParts);
        machine_intent.putExtra("maintainencePlan", maintainPlan);
        machine_intent.putExtra("machineQuant", machineQuant);
        setResult(RESULT_OK, machine_intent);
        finish();
    }


}
