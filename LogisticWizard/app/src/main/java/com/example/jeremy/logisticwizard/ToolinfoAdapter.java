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

public class ToolinfoAdapter extends ArrayAdapter<tool_info> {
    private Activity context;
    private List<tool_info> tool_infoList;

    public ToolinfoAdapter (Activity context, List<tool_info>tool_infoList) {
        super(context, android.R.layout.simple_list_item_1, tool_infoList);
        this.context = context;
        this.tool_infoList = tool_infoList;
    }

    @NonNull
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View ListView = inflater.inflate(android.R.layout.simple_list_item_1, null, true);

        TextView toolName = (TextView)ListView.findViewById(android.R.id.text1);
        //Toast.makeText(tool.this, "Please enter an email !", Toast.LENGTH_SHORT).show();
        tool_info tool = tool_infoList.get(position);
        toolName.setText(tool.tool_name);

        return ListView;


    }
}
