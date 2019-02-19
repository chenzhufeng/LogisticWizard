package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class homePage extends AppCompatActivity {

    //using listView and ArrayAdapter to display all sections
    ListView lv;
    String[] inventorName= {"Work Orders","Assets & equipment", "Tools & Parts", "User Profile", "Calender"};
    String[] inventorDescription= {"", "", "", ""};

    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        lv = (ListView) findViewById(R.id.listview); //will need this later
        customlistview customlistview = new customlistview(this,  inventorName, inventorDescription);
        lv.setAdapter(customlistview);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){ // "machine" was clicked

                    Intent machine_intent = new Intent(view.getContext(), Machine.class);
                    //no need to put extras
                    startActivity(machine_intent);
                }
                //Intent intent1 = new Intent(view.getContext(), .class);
                //intent1.putExtra(SINGLE, listData.get(i));
                //intent1.putExtra(LINK, links.get(i));
                //startActivity(intent1);
            }
        });

        /*
        final ArrayList<String> listData = new ArrayList<String>();
        listData.add("machine");
        listData.add("equipment");
        listData.add("tools");
        listData.add("parts");
        listData.add("asset");
        lv = (ListView) findViewById(R.id.list1); //will need this later
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        lv.setAdapter(adapter);
        */

    }
}