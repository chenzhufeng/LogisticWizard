package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class Machine extends AppCompatActivity {

    private Button add_machine;
    private SearchView sv;
    private ListView lv;

    //just for now
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);


        final ArrayList<String> listData = new ArrayList<String>();
        listData.add("sample data 1");
        listData.add("sample data 2");
        listData.add("sample data 3");
        listData.add("sample data 4");
        listData.add("sample data 5");

        sv = (SearchView) findViewById(R.id.machine_search);
        lv = (ListView) findViewById(R.id.list_of_machines); //will need this later
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        lv.setAdapter(adapter);


        add_machine = (Button) findViewById(R.id.add_machine_button);
        add_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_machine_intent = new Intent(view.getContext() , add_a_machine.class);
                startActivity(add_machine_intent);
            }
        });

        // https://www.youtube.com/watch?v=H3JAy94UFw0
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });

        // https://stackoverflow.com/questions/30455723/android-make-whole-search-bar-clickable
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.setIconified(false);
            }
        });

    }
}
