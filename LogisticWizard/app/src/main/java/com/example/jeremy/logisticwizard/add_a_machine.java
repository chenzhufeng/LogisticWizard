package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class add_a_machine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button add_machine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_machine);

        add_machine = (Button)findViewById(R.id.add_machine_button);

        //machine type
        //description of machine
        //image of machine
        //unit price per machine
        //quantity --> spinner
        //location of machine(s)
        //maintainance plan
        Spinner machineQuantitySpinner = findViewById(R.id.quantity_of_machine);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineQuantitySpinner.setAdapter(adapter);
        machineQuantitySpinner.setOnItemSelectedListener(this);

        Spinner machinePlanSpinner = findViewById(R.id.maintenancePlan_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machinePlanSpinner.setAdapter(adapter1);
        machinePlanSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == add_machine){

            Intent intent = new Intent(view.getContext(),Machine.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
