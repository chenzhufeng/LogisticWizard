package com.example.jeremy.logisticwizard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.in;

public class editMachine extends AppCompatActivity implements View.OnClickListener{
    private ListView lv;
    private Button saveButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_machine);

        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        lv = findViewById(R.id.editMachineInfoList);

        LinkedHashMap<String, String> machineInfoHashMap = new LinkedHashMap<>();
        machineInfoHashMap.put("Name", "Name data here");
        machineInfoHashMap.put("Type", "Type data here");
        machineInfoHashMap.put("Parts information", "Parts data here");
        machineInfoHashMap.put("Price", "Price data here");
        machineInfoHashMap.put("Location", "Location data here");
        machineInfoHashMap.put("Maintenance Plan", "Maintenance data here");
        machineInfoHashMap.put("Description", "Description data");

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.layoutfor_editmachinelist,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.editItem, R.id.editData});

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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                foreach (ListViewItem item in listView1.Items)
                {x
                    total += Convert.ToInt32(item.SubItems[1].Text);

                }

                //Intent intent1 = new Intent(view.getContext(), .class);
                //intent1.putExtra(SINGLE, listData.get(i));
                //intent1.putExtra(LINK, links.get(i));
                //startActivity(intent1);
            }
        });





    }

    @Override
    public void onClick(View v) {

    }
}
