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

public class Tooldisp extends Activity implements View.OnClickListener {

    private ListView lv;
    private Button editButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tooldisp);

        //editButton = findViewById(R.id.edittoolButton);
        //editButton.setOnClickListener(this);
        lv = findViewById(R.id.ToolInfoList);

        Intent tool_info = getIntent();
        Bundle data = tool_info.getExtras();

        String toolName = (String)data.get("toolName");
        String toolDescription = (String)data.get("toolDescription");
        String toolPrice = (String)data.get("toolPrice");
        String toolLocation = (String)data.get("toolLocation");
        String toolType = (String)data.get("toolType");
        String toolParts = (String)data.get("toolParts");
        String toolPlan = (String)data.get("maintainencePlan");
        String toolQuant = (String)data.get("toolQuant");


        final ArrayList<String> listData = new ArrayList<String>();
        LinkedHashMap<String, String> toolInfoHashMap = new LinkedHashMap<>();
        toolInfoHashMap.put("Name", toolName);
        toolInfoHashMap.put("Type", toolType);
        toolInfoHashMap.put("Parts information", toolParts);
        toolInfoHashMap.put("Price", toolPrice);
        toolInfoHashMap.put("Location", toolLocation);
        toolInfoHashMap.put("Maintenance Plan", toolPlan);
        toolInfoHashMap.put("Description", toolQuant);


        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.layoutfor_toolinfolist,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.item, R.id.data});

        Iterator it = toolInfoHashMap.entrySet().iterator();
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
           // Intent intent = new Intent(v.getContext(), editTool.class);
            //startActivity(intent);
        }
    }
}
