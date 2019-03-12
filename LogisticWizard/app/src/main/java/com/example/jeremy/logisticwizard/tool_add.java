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
import android.widget.EditText;

public class tool_add extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button add_tool;
    private EditText tool_name;
    private EditText tool_type;
    //private EditText tool_parts;
    private EditText tool_descrip;
    private EditText tool_price;
    private EditText tool_location;
    //private Spinner toolPlanSpinner;
    private Spinner toolQuantitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_add);
        add_tool = (Button)findViewById(R.id.add_tool_button);
        tool_name = (EditText)findViewById(R.id.enterToolName);
        tool_type = (EditText)findViewById(R.id.toolType);
        //tool_parts = (EditText)findViewById(R.id.partsInfo);
        tool_descrip = (EditText)findViewById(R.id.enterToolDescription);
        tool_price = (EditText)findViewById(R.id.toolPrice);
        tool_location = (EditText)findViewById(R.id.toolLocation);



        toolQuantitySpinner = findViewById(R.id.quantity_of_tool);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolQuantitySpinner.setAdapter(adapter);
        toolQuantitySpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view == add_tool){
            addMachine();
            //Intent intent = new Intent(view.getContext(),machine_main_fragment.class);
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
        String toolName = tool_name.getText().toString().trim();
        String toolDescp = tool_descrip.getText().toString().trim();
        String toolPrice = tool_price.getText().toString().trim();
        String toolLocat = tool_location.getText().toString().trim();
        String toolType = tool_type.getText().toString().trim();
        //String toolParts = tool_parts.getText().toString().trim();
        //String toolPlan = toolPlanSpinner.getSelectedItem().toString().trim();
        String toolQuant = toolQuantitySpinner.getSelectedItem().toString().trim();

        if (toolName.equals("")||toolDescp.equals("")||toolPrice.equals("")||toolLocat.equals("")
                ||toolType.equals("")||toolQuant.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        }else {

            Intent machine_intent = new Intent();
            machine_intent.putExtra("toolName", toolName);
            machine_intent.putExtra("toolDescription", toolDescp);
            machine_intent.putExtra("toolPrice", toolPrice);
            machine_intent.putExtra("toolLocation", toolLocat);
            machine_intent.putExtra("toolType", toolType);
            //machine_intent.putExtra("toolParts", toolParts);
            //machine_intent.putExtra("maintainencePlan", toolPlan);
            machine_intent.putExtra("toolQuant", toolQuant);
            setResult(RESULT_OK, machine_intent);
            finish();
        }
    }




}
