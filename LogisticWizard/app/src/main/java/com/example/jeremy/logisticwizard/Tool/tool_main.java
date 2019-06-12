package com.example.jeremy.logisticwizard.Tool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.logisticwizard.Calendar.calendar_main_fragment;
import com.example.jeremy.logisticwizard.R;
import com.example.jeremy.logisticwizard.home_page;
import com.example.jeremy.logisticwizard.Profile.profile_main_fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import android.widget.AdapterView;

public class tool_main extends AppCompatActivity implements View.OnClickListener {

    protected DatabaseReference mDatabase;
    private Button add_tool;
    private ListView lv;
    private TextView bt;
    ArrayList<tool_info> tool_infoList;
    //added
    View top;
    View v2;

    //just for now
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_main);

        BottomNavigationView bottomNav  = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(0).setCheckable(false);

        mDatabase = FirebaseDatabase.getInstance().getReference("tools");

        final ArrayList<String> listData = new ArrayList<String>();

        lv = (ListView) findViewById(R.id.list_of_tools); //will need this later
        bt = (TextView) findViewById(R.id.button_text);

        //added
        top = (View) findViewById(R.id.top_view);
        v2 = (View) findViewById(R.id.list_view);

        tool_infoList = new ArrayList<>();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        //lv.setAdapter(adapter);

        add_tool = (Button) findViewById(R.id.add_tool_button);
        add_tool.setOnClickListener(this);

        // https://www.youtube.com/watch?v=H3JAy94UFw0
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                adapter.getFilter().filter(s);
//                return false;
//            }
//        });
//
//        // https://stackoverflow.com/questions/30455723/android-make-whole-search-bar-clickable
//        sv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sv.setIconified(false);
//            }
//        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            //menuItem.setCheckable(true);
                            Intent intent = new Intent(tool_main.this, home_page.class);
                            startActivity(intent);
                            //selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_orders:
                            //Intent intent2 = new Intent(machine_main.this, workorder_main.class);
                            //intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            //startActivity(intent2);
                            lv.setVisibility(View.INVISIBLE);
                            add_tool.setVisibility(View.INVISIBLE);
                            top.setVisibility(View.INVISIBLE);
                            v2.setVisibility(View.INVISIBLE);
                            bt.setVisibility(View.INVISIBLE);

                            //need other layouts

                            menuItem.setCheckable(true);
                            selectedFragment = new calendar_main_fragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                        case R.id.nav_profile:
                            //Intent intent3 = new Intent(machine_main.this, profile_main.class);
                            //startActivity(intent3);
                            //menuItem.setCheckable(true);
                            lv.setVisibility(View.INVISIBLE);
                            add_tool.setVisibility(View.INVISIBLE);
                            top.setVisibility(View.INVISIBLE);
                            v2.setVisibility(View.INVISIBLE);
                            bt.setVisibility(View.INVISIBLE);
                            //need other layouts

                            selectedFragment = new profile_main_fragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                    }
                    return true; //return clicked item
                }

            };

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tool_infoList.clear();
                for(DataSnapshot toolSnapshot : dataSnapshot.getChildren()){
                    tool_info tool = toolSnapshot.getValue(tool_info.class);
                    tool_infoList.add(tool);
                }
                //Toast.makeText(tool.this, tool_infoList.get(0).tool_name+tool_infoList.get(1).tool_name, Toast.LENGTH_SHORT).show();
                ToolinfoAdapter toolinfoAdapter = new ToolinfoAdapter(tool_main.this,
                        tool_infoList);
                lv.setAdapter(toolinfoAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // "tool" was clicked
                        tool_selected(i, tool_infoList, view);
                    }
                });
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void tool_selected(int i, ArrayList<tool_info> tool_infoList, View view){
        String toolName = tool_infoList.get(i).tool_name;
        String toolDescp = tool_infoList.get(i).tool_descrip;
        String toolPrice = tool_infoList.get(i).tool_price;
        String toolLocat = tool_infoList.get(i).tool_location;
        String toolType = tool_infoList.get(i).tool_type;
        //String toolParts = tool_infoList.get(i).tool_parts;
        //String maintainPlan = tool_infoList.get(i).maintain_plan;
        String toolQuant = tool_infoList.get(i).tool_quant;

        Intent tool_intent = new Intent(view.getContext(), tool_disp.class);
        tool_intent.putExtra("toolName", toolName);
        tool_intent.putExtra("toolDescription", toolDescp);
        tool_intent.putExtra("toolPrice", toolPrice);
        tool_intent.putExtra("toolLocation", toolLocat);
        tool_intent.putExtra("toolType", toolType);
        //tool_intent.putExtra("toolParts", toolParts);
        //tool_intent.putExtra("maintainencePlan", maintainPlan);
        tool_intent.putExtra("toolQuant", toolQuant);
        startActivity(tool_intent);
    }



    public void add_tool (View view){
        Intent add_tool_intent = new Intent(view.getContext() , tool_add.class);
        startActivityForResult(add_tool_intent, 22);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK) {
            String toolName = data.getStringExtra("toolName");
            String toolDescription = data.getStringExtra("toolDescription");
            String toolPrice = data.getStringExtra("toolPrice");
            String toolLocation = data.getStringExtra("toolLocation");
            String toolType = data.getStringExtra("toolType");
            //String toolParts = data.getStringExtra("toolParts");
            //String toolPlan = data.getStringExtra("maintainencePlan");
            String toolQuant = data.getStringExtra("toolQuant");
            Toast.makeText(this, "tool name"+toolName+"lalal", Toast.LENGTH_SHORT).show();
            savetoolToDB(toolName, toolDescription, toolPrice, toolLocation,
                    toolType, toolQuant);
        }
    }


    private void savetoolToDB(String toolName, String toolDescription, String toolPrice, String toolLocation,
                                 String toolType, String toolQuant) {
        //final String tool_Name = toolName;
        //currentUserID = mAuthSetting.getCurrentUser().getUid();
//        toolRef = FirebaseDatabase.getInstance().getReference().child("tool_main");
//        //userRef2 = userRef.child("comments").child(Rest_ID);
//        toolRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // Log.i("snapshot", "Inside onDataChange!!!");
        tool_info tool = new tool_info(toolName, toolDescription, toolPrice, toolLocation,
                toolType,  toolQuant);
        mDatabase.child(toolName).setValue(tool);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    public void onClick(View view){
        if (view == add_tool) {
            add_tool(view);
        }
    }
}
