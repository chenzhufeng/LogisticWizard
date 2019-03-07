package com.example.connormonson.zhufeng_navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.graphics.Outline;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.annotation.TargetApi;

public class machine_main_fragment extends Fragment {

    protected DatabaseReference mDatabase;
    private Button add_machine;
    private SearchView sv;
    private ListView lv;
    //private View machineBar = (View) findViewById(R.id.machine_bar);
    //ArrayList<machine_info> machine_infoList;
    //private View machineBar = (View) findViewById(R.id.machine_bar);

    //just for now
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_machine_main, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("machines");

        mDatabase = FirebaseDatabase.getInstance().getReference("machines");

        return v;
    }
}
