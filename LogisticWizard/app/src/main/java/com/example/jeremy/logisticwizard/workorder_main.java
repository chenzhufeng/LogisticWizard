package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class workorder_main extends AppCompatActivity {
    private Button newOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_main_activity);

        BottomNavigationView bottomNav  = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(0).setCheckable(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new workorder_main_fragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            //menuItem.setCheckable(true);
                            Intent intent = new Intent(workorder_main.this, home_page.class);
                            startActivity(intent);
                            //selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_orders:
                            //Intent intent2 = new Intent(workOrders.this, workOrders.class);
                            //startActivity(intent2);
                            menuItem.setCheckable(true);
                            selectedFragment = new workorder_main_fragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                        case R.id.nav_profile:
                            //Intent intent3 = new Intent(workOrders.this, calendar.class);
                            //startActivity(intent3);
                            menuItem.setCheckable(true);
                            selectedFragment = new profile_main_fragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                    }
                    return true; //return clicked item
                }
            };

}
