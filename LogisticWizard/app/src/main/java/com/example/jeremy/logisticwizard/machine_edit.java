package com.example.jeremy.logisticwizard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class machine_edit extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String machineName;
    String machineName2;
    String machineDescp;
    String machinePrice;
    String machineLocat;
    String machineType;
    String machineParts;
    String maintainPlan;
    String machineQuant;
    String machineImage;
    List<String> temple=new ArrayList<>();
    private EditText name;
    private EditText type;
    private EditText part;
    private EditText price;
    private EditText location;
    private Spinner machinePlanSpinner;
    private Spinner machineQuantitySpinner;
    private EditText description;
    private ImageButton edit_machine_image;
    private Uri filePath;
    protected DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("machines");;
    StorageReference storageReference;
    private Button save;

    @Override
    public void onClick(View view){
        if(view == save){
            saveInfo();
            Intent machine_intent = new Intent(view.getContext(), machine_disp.class);
            machine_intent.putExtra("machineName", machineName2);
            machine_intent.putExtra("machineDescription", machineDescp);
            machine_intent.putExtra("machinePrice", machinePrice);
            machine_intent.putExtra("machineLocation", machineLocat);
            machine_intent.putExtra("machineType", machineType);
            machine_intent.putExtra("machineParts", machineParts);
            machine_intent.putExtra("maintainencePlan", maintainPlan);
            machine_intent.putExtra("machineQuant", machineQuant);
            machine_intent.putExtra("machineImage", machineImage);
            startActivity(machine_intent);
        }
        if(view == edit_machine_image){
            showNormalDialog();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_edit);
        storageReference = FirebaseStorage.getInstance().getReference();
        machineQuantitySpinner = findViewById(R.id.quantityMachineSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineQuantitySpinner.setAdapter(adapter);
        machineQuantitySpinner.setOnItemSelectedListener(this);


        machinePlanSpinner = findViewById(R.id.maintenancePlanSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.machinePlanStringArray, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machinePlanSpinner.setAdapter(adapter1);
        machinePlanSpinner.setOnItemSelectedListener(this);

        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();

        machineName = (String)data.get("machineName");
        machineDescp = (String)data.get("machineDescription");
        machinePrice = (String)data.get("machinePrice");
        machineLocat = (String)data.get("machineLocation");
        machineType = (String)data.get("machineType");
        machineParts = (String)data.get("machineParts");
        maintainPlan = (String)data.get("maintainencePlan");
        machineQuant = (String)data.get("machineQuant");
        machineImage = (String)data.get("machineImage");

        int machinePlan2 = Integer.parseInt(maintainPlan);

        name = findViewById(R.id.nameText);
        type = findViewById(R.id.typeText);
        part = findViewById(R.id.partsText);
        price = findViewById(R.id.priceText);
        location = findViewById(R.id.locationText);
        description = findViewById(R.id.descriptionText);
        edit_machine_image = findViewById(R.id.edit_machine_image);
        //make save button onclickable
        save = findViewById(R.id.saveButton);

        name.setText(machineName);
        type.setText(machineType);
        part.setText(machineParts);
        price.setText(machinePrice);
        location.setText(machineLocat);
        description.setText(machineDescp);
        machinePlanSpinner.setSelection(machinePlan2);





    }

    @Override
    protected void onStart() {
        super.onStart();
        StorageReference imageRef = storageReference.child(machineImage);
        try {
            final File localimage = File.createTempFile(machineName,"jpg");
            imageRef.getFile(localimage).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localimage.getPath());
                    edit_machine_image.setImageBitmap(bitmap);

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void saveInfo(){
        machineName2 = name.getText().toString().trim();
        machineDescp = description.getText().toString().trim();
        machinePrice = price.getText().toString().trim();
        machineLocat = location.getText().toString().trim();
        machineType = type.getText().toString().trim();
        machineParts = part.getText().toString().trim();
        maintainPlan = machinePlanSpinner.getSelectedItem().toString().trim();
        //machineQuant = machineQuantitySpinner.getSelectedItem().toString().trim();

        if (machineName2.equals("")||machineDescp.equals("")||machinePrice.equals("")||machineLocat.equals("")
                ||machineType.equals("")||machineParts.equals("")||maintainPlan.equals("")||machineQuant.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        }else {
            if(!machineName2.equals(machineName)) {
                mDatabase.child(machineName).removeValue();
            }

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot machineSnapshot : dataSnapshot.getChildren()){
                        if(!machineSnapshot.getKey().equals(machineName)){
                            temple.add(machineSnapshot.getKey());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if(temple.contains(machineName2)){
                Toast.makeText(machine_edit.this,
                        "machine_main already exists, please enter a new name.", Toast.LENGTH_LONG).show();
                return;

            }
            else {
                if(filePath != null)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    //progressDialog.setTitle("Uploading...");
                    //progressDialog.show();

                    StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                    machineImage = ref.getPath();

                    ref.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //progressDialog.dismiss();
                                    Toast.makeText(machine_edit.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //progressDialog.dismiss();
                                    Toast.makeText(machine_edit.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });
                }

                machine_info machine = new machine_info(machineName2, machineDescp, machinePrice, machineLocat,
                        machineType, machineParts, maintainPlan, machineQuant, machineImage);
                mDatabase.child(machineName2).setValue(machine);
            }

        }
    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);

        normalDialog.setTitle(" I am a Dialog");
        normalDialog.setMessage("Which one do you want to choose?");
        normalDialog.setPositiveButton("gallery",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chooseImage();
                    }
                });
        normalDialog.setNegativeButton("camera",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takeImage();
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 88);
    }
    private void takeImage(){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, 88);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 88 && resultCode == RESULT_OK
                && data != null )
        {
            Toast.makeText(this,
                    "Error occur:"+resultCode,  Toast.LENGTH_SHORT).show();
            filePath = data.getData();
            try {
                float scale = this.getResources().getDisplayMetrics().density;
                int width = (int)(350*scale+0.5f);
                int height = (int)(200*scale+0.5f);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Picasso.with(this).load(filePath).resize(width, height).into(edit_machine_image);
                //image.setImageBitmap(bitmap);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if(data.hasExtra("data")){

                Bitmap bitMap = data.getParcelableExtra("data");

            }

        }
        else{
            boolean t = true;
            if(data.getData()==null){
                t = false;
            }
            Toast.makeText(this,
                    "Error occur:"+t,  Toast.LENGTH_SHORT).show();
        }

    }

}
