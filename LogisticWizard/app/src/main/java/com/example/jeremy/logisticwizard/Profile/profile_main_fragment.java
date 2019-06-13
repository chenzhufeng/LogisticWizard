package com.example.jeremy.logisticwizard.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.logisticwizard.Profile.profile_edit;
import com.example.jeremy.logisticwizard.R;
import com.example.jeremy.logisticwizard.home_page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class profile_main_fragment extends Fragment {
    TextView user_name;
    String temp;
    TextView user_shortbio;
    TextView email_show;
    TextView phone_number;
    TextView role;
    TextView password;
    Button edit;
    protected DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    String Uid;
    String email;
    AuthCredential credential;
    Pattern pswPattern = Pattern.compile("^[a-zA-Z]\\w{5,15}$");
    String user_role = home_page.role;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.profile_main_fragment, container, false);
        user_name = rootView.findViewById(R.id.user_profile_name);
        email_show = rootView.findViewById(R.id.user_profile_email);
        phone_number = rootView.findViewById(R.id.user_profile_phone);
        password =rootView.findViewById(R.id.user_profile_password);
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
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_dialog_password(view);
            }
        });
        return  rootView;
    }

    public void onStart() {
        super.onStart();

        if(!user_role.equals("Admin")){
            edit.setVisibility(View.INVISIBLE);
        }


        //Get the info of current user
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Uid = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
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
                email = temp;
                email_show.setText("Username/Email: "+ temp);
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
        change_phone_Dialog.show();
    }

    void confirm_dialog_password(View view){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(view.getContext());


        normalDialog.setMessage("Do you want to change your password?");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        change_password();//check the textview to changeable
                    }
                });
        normalDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no(); //just cancel
                    }
                });
        normalDialog.show();
    }

    void change_password(){
        LayoutInflater factory = LayoutInflater.from(getView().getContext());
        final View textEntryView = factory.inflate(R.layout.change_password_dialog, null);
        final EditText old_password = textEntryView.findViewById(R.id.oldPassword);
        final EditText new_password = textEntryView.findViewById(R.id.newPassword);
        final EditText confirm_password = textEntryView.findViewById(R.id.confirmPassword);
        final AlertDialog.Builder change_password_Dialog =
                new AlertDialog.Builder(getView().getContext());
        change_password_Dialog.setView(textEntryView);
        change_password_Dialog.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String oldpass = old_password.getText().toString().trim();
                        final String newpass = new_password.getText().toString().trim();
                        final String conpass = confirm_password.getText().toString().trim();

                        //Check if inputs are empty
                        if (TextUtils.isEmpty(oldpass)||TextUtils.isEmpty(newpass)||TextUtils.isEmpty(conpass)) {
                            Toast.makeText(getView().getContext(),
                                    "Please do not leave blank!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(pswPattern.matcher(newpass).matches() == false){
                            Toast.makeText(getView().getContext(),
                                    "Password should start with alpha and be 5-15 length!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //Check if type new password correctly
                        if(!newpass.equals(conpass)){
                            Toast.makeText(getView().getContext(),
                                    "Please confirm your new password.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        credential = EmailAuthProvider.getCredential(email, oldpass);
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task2) {
                                            if(task2.isSuccessful()){
                                                Toast.makeText(getView().getContext(),
                                                        "Password updated.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    onStart();
                                }else {
                                    Toast.makeText(getView().getContext(),
                                            "Your old password is not correct", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });
        change_password_Dialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //no(); //just cancel
                    }
                });
        change_password_Dialog.show();
    }
}
