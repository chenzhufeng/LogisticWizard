package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.LinearLayoutManager;

public class workorder_main extends AppCompatActivity implements View.OnClickListener {
    private Button newOrder;
    //private RecyclerView.LayoutManager linearLayoutManager;
    //ArrayList workOrders = new ArrayList<>(Arrays.asList("Person 1, Person 2, Person 3"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_main);

        newOrder = findViewById(R.id.new_order);
        newOrder.setOnClickListener(this);

        //RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        //linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        //mAdapter = new MyAdapter(myDataset);
        //recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == newOrder) {
            Intent intent = new Intent(view.getContext(), workorder_add.class);
            startActivity(intent);
        }
    }
}
