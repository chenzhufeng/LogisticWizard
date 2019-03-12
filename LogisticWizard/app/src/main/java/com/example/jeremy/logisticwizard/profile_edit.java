package com.example.jeremy.logisticwizard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class profile_edit extends Fragment {
    private Button saveProfileButton;
    //private EditText lastName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.profile_edit, container, false);
        saveProfileButton = view.findViewById(R.id.saveProfileButton);
        //lastName = view.findViewById(R.id.lastNameEditText);



        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //((profile_main)getActivity()).setViewPager(0);
            }
        });

        return view;
    }

}
