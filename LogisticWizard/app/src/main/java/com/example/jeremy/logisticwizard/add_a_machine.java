package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_a_machine extends AppCompatActivity{
    private Button add_machine;
    private EditText machine_name;
    private EditText machine_descrip;
    private EditText machine_price;
    private EditText machine_location;
    private EditText maintain_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_machine);

        add_machine = (Button)findViewById(R.id.add_machine_button);
        machine_name = (EditText)findViewById(R.id.enterMachineName);
        machine_descrip = (EditText)findViewById(R.id.enterMachineDescription);
        machine_price = (EditText)findViewById(R.id.machinePrice);
        machine_location = (EditText)findViewById(R.id.machinePrice);
        maintain_plan = (EditText)findViewById(R.id.maintenancePlan);


        //machine type
        //description of machine
        //image of machine
        //unit price per machine
        //quantity --> spinner
        //location of machine(s)
        //maintainance plan

    }

    /**
     * the user wants to add the information of a new machine
     * return all information as a string to Inventory(include onSuccess Code)
     *
     * @param view
     */
    public void addMachine(View view){
        String machineName = machine_name.getText().toString().trim();
        String machineDescp = machine_descrip.getText().toString().trim();
        String machinePrice = machine_price.getText().toString().trim();
        String machineLocat = machine_location.getText().toString().trim();
        String maintainPlan = maintain_plan.getText().toString().trim();

        if (machineName.equals("")||machineDescp.equals("")||machinePrice.equals("")||machineLocat.equals("")||maintainPlan.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        }

        Intent machine_intent = new Intent();
        machine_intent.putExtra("machineName", machine_intent);
        machine_intent.putExtra("machineDescription", machine_intent);
        machine_intent.putExtra("machinePrice", machine_intent);
        machine_intent.putExtra("machineLocation", machine_intent);
        machine_intent.putExtra("maintainencePlan", machine_intent);
        setResult(RESULT_OK, machine_intent);
        finish();
    }


}
