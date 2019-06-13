package com.example.jeremy.logisticwizard.Machine;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.jeremy.logisticwizard.R;
import com.example.jeremy.logisticwizard.Work_order.workorder_main;
import com.example.jeremy.logisticwizard.Work_order.workorder_view;
import com.example.jeremy.logisticwizard.home_page;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class machine_disp extends Activity implements View.OnClickListener{
    //set up variables
    private ListView lv;
    private Button editButton;
    private Button historyButton;
    private Button deleteButton;
    private ImageView machine_image;
    String machineName;
    String machineDescription;
    String machinePrice;
    String machineLocation;
    String machineType;
    String machineParts;
    String machinePlan;
    String machineQuant;
    String machineImage = "None";
    protected StorageReference mStorage;
    StorageReference imageRef;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("machines");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_disp);

        mStorage = FirebaseStorage.getInstance().getReference();

        //link up variables to correspond views in xml
        editButton = findViewById(R.id.editMachineButton);
        editButton.setOnClickListener(this);
        lv = findViewById(R.id.machineInfoList);
        historyButton = findViewById(R.id.histroyButton);
        historyButton.setOnClickListener(this);
        deleteButton = findViewById(R.id.deleteMachine);
        deleteButton.setOnClickListener(this);

        machine_image = findViewById(R.id.machine_image);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //only administrator should be able to edit machiens
        String role = home_page.role;
        if (!role.equals("Admin")) {
            editButton.setVisibility(View.INVISIBLE);
            historyButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
        }

        //get passed information from another page
        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();
        machineName = (String)data.get("machineName");
        machineDescription = (String)data.get("machineDescription");
        machinePrice = (String)data.get("machinePrice");
        machineLocation = (String)data.get("machineLocation");
        machineType = (String)data.get("machineType");
        machineParts = (String)data.get("machineParts");
        machinePlan = (String)data.get("maintainencePlan");
        machineQuant = (String)data.get("machineQuant");
        machineImage = (String)data.get("machineImage");

        //store them into a hash map
        LinkedHashMap<String, String> machineInfoHashMap = new LinkedHashMap<>();
        machineInfoHashMap.put("Name", machineName);
        machineInfoHashMap.put("Description", machineDescription);
        machineInfoHashMap.put("Type", machineType);
        machineInfoHashMap.put("Parts information", machineParts);
        machineInfoHashMap.put("Price", machinePrice);
        machineInfoHashMap.put("Location", machineLocation);
        machineInfoHashMap.put("Maintenance Plan", machinePlan);
        machineInfoHashMap.put("machineQuant", machineQuant);

        //link up hash map and adapter
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.machine_disp_list,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.item, R.id.data});

        Iterator it = machineInfoHashMap.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        //display information by adapter
        lv.setAdapter(adapter);

        //load images
        imageRef = mStorage.child(machineImage);
        try {
            final File localimage = File.createTempFile(machineName, "jpg");
            imageRef.getFile(localimage).addOnSuccessListener(new OnSuccessListener
                    <FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localimage.getPath());
                    machine_image.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    onStart();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        //edit button brings user to edit page
        if (v == editButton) {
            //put data in variables and pass them to another page
            Intent machine_intent = new Intent(v.getContext(), machine_edit.class);
            machine_intent.putExtra("machineName", machineName);
            machine_intent.putExtra("machineDescription", machineDescription);
            machine_intent.putExtra("machinePrice", machinePrice);
            machine_intent.putExtra("machineLocation", machineLocation);
            machine_intent.putExtra("machineType", machineType);
            machine_intent.putExtra("machineParts", machineParts);
            machine_intent.putExtra("maintainencePlan", machinePlan);
            machine_intent.putExtra("machineQuant", machineQuant);
            machine_intent.putExtra("machineImage", machineImage);
            startActivity(machine_intent);
        }

        //history button brings user to check all machine history
        if(v == historyButton){
            Intent intent = new Intent (v.getContext(), machine_history.class);
            intent.putExtra("machineName", machineName);
            startActivity(intent);
        }

        //delete button call a dialog ask users if they want delete this machine
        if(v== deleteButton){
            showNormalDialog();
        }
    }

    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);

        normalDialog.setTitle("Delete Confirmation");
        normalDialog.setMessage("Do you want to delete this machine?");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child(machineName).removeValue();
                        Toast.makeText(machine_disp.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (machine_disp.this, machine_main.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
        normalDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}
