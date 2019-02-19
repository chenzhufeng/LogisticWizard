package com.example.jeremy.logisticwizard;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class customlistview extends ArrayAdapter<String> {

    private String[] inventorName;
    private String[] inventorDescription;
    private Activity context;

    public customlistview(Activity context, String[] inventorName, String[] inventorDescription) {
        super(context, R.layout.activity_homepage_layout, inventorName);

        this.context=context;
        this.inventorName=inventorName;
        this.inventorDescription=inventorDescription;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r=convertView;
        viewHolder viewHolder=null;
        if (r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.activity_homepage_layout,null,true);
            viewHolder = new viewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (viewHolder) r.getTag();
        }
        viewHolder.tvw1.setText(inventorName[position]);
        viewHolder.tvw2.setText(inventorDescription[position]);


        return r;


    }

    class viewHolder{
        TextView tvw1;
        TextView tvw2;
        viewHolder(View v){
            tvw1 = (TextView) v.findViewById(R.id.sectionName);
            tvw2 = (TextView) v.findViewById(R.id.sectionDescription);

        }
    }
}
