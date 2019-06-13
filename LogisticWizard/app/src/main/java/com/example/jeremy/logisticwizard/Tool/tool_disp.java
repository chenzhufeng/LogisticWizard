package com.example.jeremy.logisticwizard.Tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.jeremy.logisticwizard.Machine.machine_disp;
import com.example.jeremy.logisticwizard.Machine.machine_main;
import com.example.jeremy.logisticwizard.R;
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

public class tool_disp extends Activity implements View.OnClickListener {
    private String role = home_page.role;
    private ListView lv;
    private Button editButton;
    private ImageView tool_image;
    private Button deleteButton;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("tools");

    String toolName;
    String toolDescription;
    String toolPrice;
    String toolLocation;
    String toolType;
    String toolQuant;
    String toolImage = "null";
    protected StorageReference mStorage;
    StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_disp);

        mStorage = FirebaseStorage.getInstance().getReference();

        editButton = findViewById(R.id.editToolButton);
        editButton.setOnClickListener(this);
        deleteButton = findViewById(R.id.deleteTools);
        deleteButton.setOnClickListener(this);
        lv = findViewById(R.id.ToolInfoList);
        tool_image = findViewById(R.id.tool_image);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent tool_info = getIntent();
        Bundle data = tool_info.getExtras();

        //Facility Worker cannot edit tools
        if (role.equals("Facility Worker")) {
            editButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
        } else {
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }

        //Get data from tool_main
        toolName = (String) data.get("toolName");
        toolDescription = (String) data.get("toolDescription");
        toolPrice = (String) data.get("toolPrice");
        toolLocation = (String) data.get("toolLocation");
        toolType = (String) data.get("toolType");
        toolQuant = (String) data.get("toolQuant");
        toolImage = (String)data.get("toolImage");

        final ArrayList<String> listData = new ArrayList<String>();
        LinkedHashMap<String, String> toolInfoHashMap = new LinkedHashMap<>();
        toolInfoHashMap.put("Name", toolName);
        toolInfoHashMap.put("Type", toolType);
        toolInfoHashMap.put("Price", toolPrice);
        toolInfoHashMap.put("Location", toolLocation);
        toolInfoHashMap.put("Quantity", toolQuant);
        toolInfoHashMap.put("Description", toolDescription);


        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.tool_disp_list,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.item, R.id.data});

        Iterator it = toolInfoHashMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        lv.setAdapter(adapter);

        imageRef = mStorage.child(toolImage);
        try {
            final File localimage = File.createTempFile(toolName, "jpg");
            imageRef.getFile(localimage).addOnSuccessListener(new OnSuccessListener
                    <FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localimage.getPath());
                    tool_image.setImageBitmap(bitmap);

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
        if (v == editButton) {
            Intent intent = new Intent(v.getContext(), tool_edit.class);
            intent.putExtra("toolName", toolName);
            intent.putExtra("toolDescription", toolDescription);
            intent.putExtra("toolPrice", toolPrice);
            intent.putExtra("toolLocation", toolLocation);
            intent.putExtra("toolType", toolType);
            intent.putExtra("toolQuant", toolQuant);
            intent.putExtra("toolImage", toolImage);
            startActivity(intent);
        }
        if(v == deleteButton){
            showNormalDialog();
        }
    }
    private void showNormalDialog() {

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);

        normalDialog.setTitle("Delete Confirmation");
        normalDialog.setMessage("Do you want to delete this tool?");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child(toolName).removeValue();
                        Toast.makeText(tool_disp.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (tool_disp.this, tool_main.class);
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
        normalDialog.show();
    }
}
