package com.example.jeremy.logisticwizard.Profile;

import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;

import com.example.jeremy.logisticwizard.R;
import com.example.jeremy.logisticwizard.Custom_object.user_info;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerViewAdapter_userInfo extends RecyclerView.Adapter<RecyclerViewAdapter_userInfo.MyViewHolder>{
    private List<user_info> userinfo;
    private Context context;
    private ArrayAdapter<CharSequence> adapter1;

    public RecyclerViewAdapter_userInfo(Context context, List<user_info> userinfo) {
        this.userinfo = userinfo;
        this.context = context;
        this.adapter1 = ArrayAdapter.createFromResource(context,
                R.array.user_role, android.R.layout.simple_spinner_item);
        this.adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    // Used to create new views
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.user_role, parent, false);
        return new MyViewHolder(v);
    }

    // Replace contents of a view
    @Override
    public void onBindViewHolder(RecyclerViewAdapter_userInfo.MyViewHolder holder,
                                 final int position) {
        user_info user = userinfo.get(position);
        holder.userid = user.u_id;
        holder.user_name.setText(user.Name);
        holder.role.setText("("+user.Role+")");
        holder.role_spinner.setAdapter(adapter1);
    }

    // Return size of dataset
    @Override
    public int getItemCount() {
        return userinfo.size();
    }

    // Provide a reference to the views for each data item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
        public TextView role;
        public Spinner role_spinner;
        String item;
        String userid;
        private FirebaseAuth mAuth;
        public MyViewHolder(final View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.name);
            role = itemView.findViewById(R.id.role);
            role_spinner = itemView.findViewById(R.id.role_spinner);
            final DatabaseReference mDatabase
                    = FirebaseDatabase.getInstance().getReference("users");

            role_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                    // On selecting a spinner item
                    item = parent.getItemAtPosition(position).toString();
                    if(!item.equals("Role")) {
                        mDatabase.child(userid).child("Role").setValue(item);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        }
    }
}