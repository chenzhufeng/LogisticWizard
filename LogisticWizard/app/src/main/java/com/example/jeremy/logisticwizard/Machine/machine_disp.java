package com.example.jeremy.logisticwizard.Machine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.jeremy.logisticwizard.R;
import com.example.jeremy.logisticwizard.home_page;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private Button backButton;
    private Button historyButton;
    private ImageView machine_image;
    String machineName;
    String machineDescription;
    String machinePrice;
    String machineLocation;
    String machineType;
    String machineParts;
    String machinePlan;
    String machineQuant;
    String machineImage = "null";
    protected StorageReference mStorage;
    StorageReference imageRef;

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

        machine_image = findViewById(R.id.machine_image);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //only administrator should be able to edit machiens
        String role = home_page.role;
        if (!role.equals("Admin")) {
            editButton.setVisibility(View.INVISIBLE);
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
    }
}
