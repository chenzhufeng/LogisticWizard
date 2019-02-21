package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.graphics.Outline;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.annotation.TargetApi;

public class Machine extends AppCompatActivity {

    private Button add_machine;
    private SearchView sv;
    private ListView lv;
    private View machineBar;

    //just for now
    private ArrayAdapter<String> adapter;

    @TargetApi(21)
    private class CustomOutlineView extends ViewOutlineProvider {
        private int width;
        private int height;

        CustomOutlineView(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRect(0, 0, width, height);
        }

    }

/*    machineBar = (View) findViewById(R.id.machine_bar);
    CustomOutlineView customOutline = new CustomOutlineView(2, 2);
    machineBar.setOutlineProvider(customOutline);
*/

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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){ // "machine" was clicked

                    Intent machine_intent = new Intent(view.getContext(), machineDisp.class);
                    //no need to put extras
                    startActivity(machine_intent);
                }
                //Intent intent1 = new Intent(view.getContext(), .class);
                //intent1.putExtra(SINGLE, listData.get(i));
                //intent1.putExtra(LINK, links.get(i));
                //startActivity(intent1);
            }
        });


        add_machine = (Button) findViewById(R.id.add_machine_button);
        add_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_machine_intent = new Intent(view.getContext() , add_a_machine.class);
                startActivity(add_machine_intent);
                //finish();
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
