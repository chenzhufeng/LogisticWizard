package com.example.jeremy.logisticwizard;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private String[] workOrders;
    //private Context context;

    // Constructor for the adapter
    public RecyclerViewAdapter(String[] workOrders) {
        //this.context = context;
        this.workOrders = workOrders;
    }

    // Used to create new views
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
         *  Create new view
         *  Currently causing error: Cannot convert LinearLayout to TextView
         */
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace contents of a view
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(workOrders[position]);
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, workOrders.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    // Return size of dataset
    @Override
    public int getItemCount() {
        return workOrders.length;
    }

    // Provide a reference to the views for each data item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }
}
