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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machinedisp_layout);

        editButton = findViewById(R.id.editMachineButton);
        editButton.setOnClickListener(this);
        lv = findViewById(R.id.machineInfoList);

        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();

        String machineName = (String)data.get("machineName");
        String machineDescription = (String)data.get("machineDescription");
        String machinePrice = (String)data.get("machinePrice");
        String machineLocation = (String)data.get("machineLocation");
        String machineType = (String)data.get("machineType");
        String machineParts = (String)data.get("machineParts");
        String machinePlan = (String)data.get("maintainencePlan");
        String machineQuant = (String)data.get("machineQuant");


        final ArrayList<String> listData = new ArrayList<String>();
        LinkedHashMap<String, String> machineInfoHashMap = new LinkedHashMap<>();
        machineInfoHashMap.put("Name", machineName);
        machineInfoHashMap.put("Type", machineType);
        machineInfoHashMap.put("Parts information", machineParts);
        machineInfoHashMap.put("Price", machinePrice);
        machineInfoHashMap.put("Location", machineLocation);
        machineInfoHashMap.put("Maintenance Plan", machinePlan);
        machineInfoHashMap.put("Description", machineDescription);


        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.layoutfor_machineinfolist,
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
            Intent intent = new Intent(v.getContext(), editMachine.class);
            startActivity(intent);
        }
    }
}
