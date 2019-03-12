package com.example.jeremy.logisticwizard;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;
import android.content.Intent;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private String[] workOrders;
    private String[] Priorities;
    private String[] Dates;
    private String[] Creators;
    private String[] Progress;
    private Context context;

    // Constructor for the adapter
    public RecyclerViewAdapter(
            String[] workOrders, String[] Priorities, String[] Dates,
            String[] Creators, String[] Progress,
            Context context
    ) {
        //this.context = context;
        this.workOrders = workOrders;
        this.Priorities = Priorities;
        this.Dates = Dates;
        this.Creators = Creators;
        this.Progress = Progress;
        this.context = context;
    }

    // Used to create new views
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace contents of a view
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(workOrders[position]);
        holder.priority.setText(Priorities[position]);
        holder.date.setText(Dates[position]);
        holder.creator.setText(Creators[position]);
        holder.progress.setText(Progress[position]);
    }

    // Return size of dataset
    @Override
    public int getItemCount() {
        return workOrders.length;
    }

    // Provide a reference to the views for each data item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView priority;
        public TextView date;
        public TextView creator;
        public TextView progress;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.Title);
            priority = itemView.findViewById(R.id.priority);
            date = itemView.findViewById(R.id.date);
            creator = itemView.findViewById(R.id.creator);
            progress = itemView.findViewById(R.id.progress);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context c = view.getContext();
                    Intent intent = new Intent(c, workorder_view.class);
                    c.startActivity(intent);
                }
            });
        }
    }
}
