package com.example.jeremy.logisticwizard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class machineDisp extends Activity implements View.OnClickListener{

    private ListView lv;
    private Button editButton;
    private Button backButton;

    String machineName;
    String machineDescription;
    String machinePrice;
    String machineLocation;
    String machineType;
    String machineParts;
    String machinePlan;
    String machineQuant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_disp);

        editButton = findViewById(R.id.editMachineButton);
        editButton.setOnClickListener(this);
        lv = findViewById(R.id.machineInfoList);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();

        machineName = (String)data.get("machineName");
        machineDescription = (String)data.get("machineDescription");
        machinePrice = (String)data.get("machinePrice");
        machineLocation = (String)data.get("machineLocation");
        machineType = (String)data.get("machineType");
        machineParts = (String)data.get("machineParts");
        machinePlan = (String)data.get("maintainencePlan");
        machineQuant = (String)data.get("machineQuant");


        LinkedHashMap<String, String> machineInfoHashMap = new LinkedHashMap<>();
        machineInfoHashMap.put("Name", machineName);
        machineInfoHashMap.put("Description", machineDescription);
        machineInfoHashMap.put("Type", machineType);
        machineInfoHashMap.put("Parts information", machineParts);
        machineInfoHashMap.put("Price", machinePrice);
        machineInfoHashMap.put("Location", machineLocation);
        machineInfoHashMap.put("Maintenance Plan", machinePlan);
        machineInfoHashMap.put("machineQuant", machineQuant);


        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.machine_disp_list,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.item, R.id.data});

        Iterator it = machineInfoHashMap.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        lv.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v == editButton) {
            Intent machine_intent = new Intent(v.getContext(), editInformation.class);
            machine_intent.putExtra("machineName", machineName);
            machine_intent.putExtra("machineDescription", machineDescription);
            machine_intent.putExtra("machinePrice", machinePrice);
            machine_intent.putExtra("machineLocation", machineLocation);
            machine_intent.putExtra("machineType", machineType);
            machine_intent.putExtra("machineParts", machineParts);
            machine_intent.putExtra("maintainencePlan", machinePlan);
            machine_intent.putExtra("machineQuant", machineQuant);
            startActivity(machine_intent);
        }
        if(v == backButton){
            Intent intent = new Intent (v.getContext(), Machine.class);
            startActivity(intent);
        }
    }
}
