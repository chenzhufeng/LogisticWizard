package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

public class tools extends AppCompatActivity implements View.OnClickListener {

    protected DatabaseReference mDatabase;
    private Button add_tool;
    private SearchView sv;
    private ListView lv;
    ArrayList<tool_info> tool_infoList;

    //just for now
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        mDatabase = FirebaseDatabase.getInstance().getReference("tools");

        final ArrayList<String> listData = new ArrayList<String>();

        sv = (SearchView) findViewById(R.id.tool_search);
        lv = (ListView) findViewById(R.id.list_of_tools); //will need this later
        tool_infoList = new ArrayList<>();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        //lv.setAdapter(adapter);

        add_tool = (Button) findViewById(R.id.add_tool_button);
        add_tool.setOnClickListener(this);

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
                ToolinfoAdapter toolinfoAdapter = new ToolinfoAdapter(tools.this,
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
        String toolParts = tool_infoList.get(i).tool_parts;
        String maintainPlan = tool_infoList.get(i).maintain_plan;
        String toolQuant = tool_infoList.get(i).tool_quant;

        Intent tool_intent = new Intent(view.getContext(), Tooldisp.class);
        tool_intent.putExtra("toolName", toolName);
        tool_intent.putExtra("toolDescription", toolDescp);
        tool_intent.putExtra("toolPrice", toolPrice);
        tool_intent.putExtra("toolLocation", toolLocat);
        tool_intent.putExtra("toolType", toolType);
        tool_intent.putExtra("toolParts", toolParts);
        tool_intent.putExtra("maintainencePlan", maintainPlan);
        tool_intent.putExtra("toolQuant", toolQuant);
        startActivity(tool_intent);
    }



    public void add_tool (View view){
        Intent add_tool_intent = new Intent(view.getContext() , add_a_tool.class);
        startActivityForResult(add_tool_intent, 21);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK) {
            String toolName = data.getStringExtra("toolName");
            String toolDescription = data.getStringExtra("toolDescription");
            String toolPrice = data.getStringExtra("toolPrice");
            String toolLocation = data.getStringExtra("toolLocation");
            String toolType = data.getStringExtra("toolType");
            String toolParts = data.getStringExtra("toolParts");
            String toolPlan = data.getStringExtra("maintainencePlan");
            String toolQuant = data.getStringExtra("toolQuant");
            Toast.makeText(this, "tool name"+toolName+"lalal", Toast.LENGTH_SHORT).show();
            savetoolToDB(toolName, toolDescription, toolPrice, toolLocation,
                    toolType, toolParts, toolPlan, toolQuant);
        }
    }


    private void savetoolToDB(String toolName, String toolDescription, String toolPrice, String toolLocation,
                                 String toolType, String toolParts, String toolPlan, String toolQuant) {
        //final String tool_Name = toolName;
        //currentUserID = mAuthSetting.getCurrentUser().getUid();
//        toolRef = FirebaseDatabase.getInstance().getReference().child("tools");
//        //userRef2 = userRef.child("comments").child(Rest_ID);
//        toolRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        // Log.i("snapshot", "Inside onDataChange!!!");
        tool_info tool = new tool_info(toolName, toolDescription, toolPrice, toolLocation,
                toolType, toolParts, toolPlan, toolQuant);
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
