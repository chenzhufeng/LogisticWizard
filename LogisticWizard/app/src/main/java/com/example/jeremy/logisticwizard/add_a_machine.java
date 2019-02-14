package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class add_a_machine extends AppCompatActivity implements View.OnClickListener {
    private Button add_machine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_machine);

        add_machine = (Button)findViewById(R.id.add_machine_button);

        //machine type
        //description of machine
        //image of machine
        //unit price per machine
        //quantity --> spinner
        //location of machine(s)
        //maintainance plan

    }

    @Override
    public void onClick(View view) {
        if(view == add_machine){

            Intent intent = new Intent(view.getContext(),Machine.class);
            startActivity(intent);
        }
    }
}
