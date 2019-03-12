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
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tool_edit extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemSelectedListener{
    String toolName;
    String toolName2;
    String toolDescp;
    String toolPrice;
    String toolLocat;
    String toolType;
    String toolParts;
    String maintainPlan;
    String toolQuant;
    List<String> temple=new ArrayList<>();
    private EditText name;
    private EditText type;
    //private EditText part;
    private EditText price;
    private EditText location;
    //private Spinner toolPlanSpinner;
    private Spinner toolQuantitySpinner;
    private EditText description;
    protected DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("tool_main");;

    private Button save;
    private ImageButton image;
    private View.OnClickListener saveOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            saveInfo();
            Intent tool_intent = new Intent(v.getContext(), tool_disp.class);
            tool_intent.putExtra("toolName", toolName2);
            tool_intent.putExtra("toolDescription", toolDescp);
            tool_intent.putExtra("toolPrice", toolPrice);
            tool_intent.putExtra("toolLocation", toolLocat);
            tool_intent.putExtra("toolType", toolType);
            //tool_intent.putExtra("toolParts", toolParts);
            //tool_intent.putExtra("maintainencePlan", maintainPlan);
            tool_intent.putExtra("toolQuant", toolQuant);
            startActivity(tool_intent);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_edit);
        image = findViewById(R.id.imageButton);
        toolQuantitySpinner = findViewById(R.id.quantityToolsSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolQuantitySpinner.setAdapter(adapter);
        toolQuantitySpinner.setOnItemSelectedListener(this);


        Intent tool_info = getIntent();
        Bundle data = tool_info.getExtras();

        toolName = (String)data.get("toolName");
        toolDescp = (String)data.get("toolDescription");
        toolPrice = (String)data.get("toolPrice");
        toolLocat = (String)data.get("toolLocation");
        toolType = (String)data.get("toolType");
        //toolParts = (String)data.get("toolParts");
        //maintainPlan = (String)data.get("maintainencePlan");
        toolQuant = (String)data.get("toolQuant");


        name = findViewById(R.id.toolsNameText);
        type = findViewById(R.id.toolsTypeText);
        //part = findViewById(R.id.toolsPriceText);
        price = findViewById(R.id.toolsPriceText);
        location = findViewById(R.id.toolsLocationText);
        description = findViewById(R.id.toolsDescriptionText);

        //make save button onclickable
        save = findViewById(R.id.toolsSaveButton);

        name.setText(toolName);
        type.setText(toolType);
        //part.setText(toolParts);
        price.setText(toolPrice);
        location.setText(toolLocat);
        description.setText(toolDescp);


        save.setOnClickListener(saveOnClickListener);



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void saveInfo(){
        toolName2 = name.getText().toString().trim();
        toolDescp = description.getText().toString().trim();
        toolPrice = price.getText().toString().trim();
        toolLocat = location.getText().toString().trim();
        toolType = type.getText().toString().trim();
        //toolParts = part.getText().toString().trim();
        //maintainPlan = toolPlanSpinner.getSelectedItem().toString().trim();
        toolQuant = toolQuantitySpinner.getSelectedItem().toString().trim();

        if (toolName2.equals("")||toolDescp.equals("")||toolPrice.equals("")||toolLocat.equals("")
                ||toolType.equals("")||toolQuant.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        }else {
            if(!toolName2.equals(toolName)) {
                mDatabase.child(toolName).removeValue();
            }

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot toolSnapshot : dataSnapshot.getChildren()){
                        if(!toolSnapshot.getKey().equals(toolName)){
                            temple.add(toolSnapshot.getKey());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if(temple.contains(toolName2)){
                Toast.makeText(tool_edit.this,
                        "tool already exists, please enter a new name.", Toast.LENGTH_LONG).show();
                return;

            }
            else {
                tool_info tool = new tool_info(toolName2, toolDescp, toolPrice, toolLocat,
                        toolType, toolQuant);
                mDatabase.child(toolName2).setValue(tool);
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(v == image){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 71);
        }
    }
}