package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.graphics.Outline;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
//added these
import android.support.design.widget.BottomNavigationView; //for bottom nav
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.widget.AdapterView;


public class machine_main extends AppCompatActivity implements View.OnClickListener{
    protected DatabaseReference mDatabase;
    private Button add_machine;
    private SearchView sv;
    private ListView lv;
    private View bar;
    private View list;
    private String role = home_page.role;
    ArrayList<machine_info> machine_infoList;
    private TextView bt;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_main);
        bar = findViewById(R.id.machine_bar);
        list = (View) findViewById(R.id.list_view);
        bt = (TextView) findViewById(R.id.button_text);

        BottomNavigationView bottomNav  = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(0).setCheckable(false);

        mDatabase = FirebaseDatabase.getInstance().getReference("machines");

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0,0, view.getWidth(), view.getHeight());
            }
        };
        bar.setOutlineProvider(viewOutlineProvider);
        bar.setClipToOutline(true);

        final ArrayList<String> listData = new ArrayList<String>();

        sv = (SearchView) findViewById(R.id.machine_search);
        lv = (ListView) findViewById(R.id.list_of_machines); //will need this later
        machine_infoList = new ArrayList<>();

        add_machine = (Button) findViewById(R.id.add_machine_button);
        add_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_machine_intent = new Intent(view.getContext() , machine_add.class);
                startActivity(add_machine_intent);
            }
        });
        add_machine.setOnClickListener(this);
        add_machine.setOutlineProvider(viewOutlineProvider);
        add_machine.setClipToOutline(true);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:

                            Intent intent = new Intent(machine_main.this, home_page.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_orders:
                            sv.setVisibility(View.INVISIBLE);
                            lv.setVisibility(View.INVISIBLE);
                            bar.setVisibility(View.INVISIBLE);
                            list.setVisibility(View.INVISIBLE);
                            add_machine.setVisibility(View.INVISIBLE);
                            bt.setVisibility(View.INVISIBLE);
                            menuItem.setCheckable(true);
                            selectedFragment = new calendar_main_fragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                        case R.id.nav_profile:
                            sv.setVisibility(View.INVISIBLE);
                            lv.setVisibility(View.INVISIBLE);
                            bar.setVisibility(View.INVISIBLE);
                            list.setVisibility(View.INVISIBLE);
                            add_machine.setVisibility(View.INVISIBLE);
                            bt.setVisibility(View.INVISIBLE);
                            selectedFragment = new profile_main_fragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                    }
                    return true;
                }

            };

    @Override
    protected void onStart() {
        super.onStart();

        if (!role.equals("Admin")) {
            add_machine.setVisibility(View.GONE);
            bt.setVisibility(View.GONE);
        }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            machine_infoList.clear();
                for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                    machine_info machine = machineSnapshot.getValue(machine_info.class);
                    machine_infoList.add(machine);
                }
                MachineinfoAdapter machineinfoAdapter = new MachineinfoAdapter(machine_main.this,
                        machine_infoList);
                lv.setAdapter(machineinfoAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            machine_selected(i, machine_infoList, view);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void machine_selected(int i, ArrayList<machine_info> machine_infoList, View view){
       String machineName = machine_infoList.get(i).machine_name;
       String machineDescp = machine_infoList.get(i).machine_descrip;
       String machinePrice = machine_infoList.get(i).machine_price;
       String machineLocat = machine_infoList.get(i).machine_location;
       String machineType = machine_infoList.get(i).machine_type;
       String machineParts = machine_infoList.get(i).machine_parts;
       String maintainPlan = machine_infoList.get(i).maintain_plan;
       String machineQuant = machine_infoList.get(i).machine_quant;
       String machineImage = machine_infoList.get(i).machine_image;
        Intent machine_intent = new Intent(view.getContext(), machine_disp.class);
        machine_intent.putExtra("machineName", machineName);
        machine_intent.putExtra("machineDescription", machineDescp);
        machine_intent.putExtra("machinePrice", machinePrice);
        machine_intent.putExtra("machineLocation", machineLocat);
        machine_intent.putExtra("machineType", machineType);
        machine_intent.putExtra("machineParts", machineParts);
        machine_intent.putExtra("maintainencePlan", maintainPlan);
        machine_intent.putExtra("machineQuant", machineQuant);
        machine_intent.putExtra("machineImage", machineImage);
        startActivity(machine_intent);
    }



    public void add_machine (View view){
        Intent add_machine_intent = new Intent(view.getContext() , machine_add.class);
        startActivityForResult(add_machine_intent, 21);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK) {
            String machineName = data.getStringExtra("machineName");
            String machineDescription = data.getStringExtra("machineDescription");
            String machinePrice = data.getStringExtra("machinePrice");
            String machineLocation = data.getStringExtra("machineLocation");
            String machineType = data.getStringExtra("machineType");
            String machineParts = data.getStringExtra("machineParts");
            String machinePlan = data.getStringExtra("maintainencePlan");
            String machineQuant = data.getStringExtra("machineQuant");
            String machineImage = data.getStringExtra("machineImage");
            Toast.makeText(this, "machine name"+machineName+"lalal", Toast.LENGTH_SHORT).show();
            saveMachineToDB(machineName, machineDescription, machinePrice, machineLocation,
                    machineType, machineParts, machinePlan, machineQuant, machineImage);
        }
    }

    private void saveMachineToDB(String machineName, String machineDescription,
                                 String machinePrice, String machineLocation,
                                 String machineType, String machineParts,
                                 String machinePlan, String machineQuant,
                                 String machineImage) {
        machine_info machine = new machine_info(machineName, machineDescription,
                machinePrice, machineLocation, machineType, machineParts,
                machinePlan, machineQuant, machineImage);
            mDatabase.child(machineName).setValue(machine);

    }

    @Override
    public void onClick(View view){
        if (view == add_machine) {
            add_machine(view);
        }
    }
}
