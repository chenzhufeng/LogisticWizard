package com.example.jeremy.logisticwizard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class workorder_main_fragment extends Fragment {
    private Button newOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.workorder_main_fragment, container, false);
        /*
        final FragmentManager fragmentManager = getFragmentManager();
        newOrder = (Button) v.findViewById(R.id.new_order);
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fragment selectedFragment = new workOrders_add();
                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.replace(R.id.fragment_container, selectedFragment).commit();
            }
        });*/
        return v;
    }
}