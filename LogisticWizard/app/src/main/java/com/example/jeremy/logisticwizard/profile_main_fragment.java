package com.example.jeremy.logisticwizard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_main_fragment extends Fragment {
    
    TextView user_name;
    String temp;
    TextView user_shortbio;
    TextView email;
    TextView phone_number;
    TextView role;
    Button edit;
    protected DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String Uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.profile_main_fragment, container, false);
        user_name = rootView.findViewById(R.id.user_profile_name);
        email = rootView.findViewById(R.id.user_profile_email);
        phone_number = rootView.findViewById(R.id.user_profile_phone);
        role = rootView.findViewById(R.id.user_profile_type);
        edit = rootView.findViewById(R.id.profile_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), profile_edit.class);
                startActivity(intent);
            }
        });
        phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_dialog(view);
            }
        });
        return  rootView;
    }

    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        Uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        //mDatabase.child(Uid).child("Name").toString();
        mDatabase.child(Uid).child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                temp = (String) dataSnapshot.getValue();
                user_name.setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(Uid).child("Email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                temp = (String) dataSnapshot.getValue();
                email.setText("Username/Email: "+temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(Uid).child("Phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                temp = (String) dataSnapshot.getValue();
                phone_number.setText("Phone Number: "+temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(Uid).child("Role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                temp = (String) dataSnapshot.getValue();
                role.setText("User Type:"+temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});


    }

    void confirm_dialog(View view){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(view.getContext());


        normalDialog.setMessage("Do you want to change it?");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        change_phone();//check the textview to changeable
                    }
                });
        normalDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no(); //just cancel
                    }
                });
        // 显示
        normalDialog.show();
    }

    void change_phone(){
        //show new dialog which allow user to input new phone number
        LayoutInflater factory = LayoutInflater.from(getView().getContext());
        final View textEntryView = factory.inflate(R.layout.change_phone_dialog, null);
        final EditText input_phone = textEntryView.findViewById(R.id.editPhoneNumber);
        //input_phone.setAutofillHints(phone_number.getText().toString().trim());
        final AlertDialog.Builder change_phone_Dialog =
                new AlertDialog.Builder(getView().getContext());
        change_phone_Dialog.setView(textEntryView);
        change_phone_Dialog.setMessage("Enter your new phone number");
        change_phone_Dialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(input_phone.getText().toString().trim().equals("")){
                            Toast.makeText(getView().getContext(),
                                    "Please enter your new phone number.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            mDatabase.child(Uid).child("Phone").setValue(input_phone.getText().toString().trim());
                            Toast.makeText(getView().getContext(),
                                    "Phone number updated.", Toast.LENGTH_LONG).show();
                            onStart();
                        }
                    }
                });
        change_phone_Dialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no(); //just cancel
                    }
                });
        // 显示
        change_phone_Dialog.show();
    }

    void save_phone(){

    }
}
