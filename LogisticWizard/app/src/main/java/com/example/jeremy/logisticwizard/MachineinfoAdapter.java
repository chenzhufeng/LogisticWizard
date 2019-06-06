package com.example.jeremy.logisticwizard;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MachineinfoAdapter extends ArrayAdapter<machine_info> {
    private Activity context;
    private List<machine_info> machine_infoList;

    public MachineinfoAdapter (Activity context, List<machine_info>machine_infoList){
        super(context, android.R.layout.simple_list_item_1, machine_infoList);
        this.context = context;
        this.machine_infoList = machine_infoList;
    }

    @NonNull
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View ListView = inflater.inflate(android.R.layout.simple_list_item_1, null, true);

        TextView machineName = (TextView)ListView.findViewById(android.R.id.text1);
        //Toast.makeText(machine_main.this, "Please enter an email !", Toast.LENGTH_SHORT).show();
        machine_info machine = machine_infoList.get(position);
        machineName.setText(machine.machine_name);

        return ListView;


    }
}
