package com.example.jeremy.logisticwizard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class machine_add extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button add_machine;
    private EditText machine_name;
    private EditText machine_type;
    private EditText machine_parts;
    private EditText machine_descrip;
    private EditText machine_price;
    private EditText machine_location;
    private Spinner machinePlanSpinner;
    private Spinner machineQuantitySpinner;
    private ImageButton image;

    private FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_add);
        add_machine = (Button)findViewById(R.id.add_machine_button);
        machine_name = (EditText)findViewById(R.id.enterMachineName);
        machine_type = (EditText)findViewById(R.id.machineType);
        machine_parts = (EditText)findViewById(R.id.partsInfo);
        machine_descrip = (EditText)findViewById(R.id.enterMachineDescription);
        machine_price = (EditText)findViewById(R.id.machinePrice);
        machine_location = (EditText)findViewById(R.id.machineLocation);

        image = findViewById(R.id.imageButton);

        machineQuantitySpinner = findViewById(R.id.quantity_of_machine);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineQuantitySpinner.setAdapter(adapter);
        machineQuantitySpinner.setOnItemSelectedListener(this);

        machinePlanSpinner = findViewById(R.id.maintenancePlan_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.machinePlanStringArray, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machinePlanSpinner.setAdapter(adapter1);
        machinePlanSpinner.setOnItemSelectedListener(this);

        storage= FirebaseStorage.getInstance("gs://logisticwizard-6d896.appspot.com/");
        storageReference = storage.getReference();


    }

    @Override
    public void onClick(View view) {
        if(view == add_machine){
            addMachine();
            //Intent intent = new Intent(view.getContext(),machine_main.class);
            //startActivity(intent);
        }
        if(view == image){
            chooseImage();
        }
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 72);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 72 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                float scale = this.getResources().getDisplayMetrics().density;
                int width = (int)(350*scale+0.5f);
                int height = (int)(200*scale+0.5f);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Picasso.with(this).load(filePath).resize(width, height).into(image);
                //image.setImageBitmap(bitmap);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * the user wants to add the information of a new machine
     * return all information as a string to Inventory(include onSuccess Code)
     *
     */
    public void addMachine() {
        String machineName = machine_name.getText().toString().trim();
        String machineDescp = machine_descrip.getText().toString().trim();
        String machinePrice = machine_price.getText().toString().trim();
        String machineLocat = machine_location.getText().toString().trim();
        String machineType = machine_type.getText().toString().trim();
        String machineParts = machine_parts.getText().toString().trim();
        String maintainPlan = machinePlanSpinner.getSelectedItem().toString().trim();
        String machineQuant = machineQuantitySpinner.getSelectedItem().toString().trim();
        String machineImage;


        if (machineName.equals("") || machineDescp.equals("") || machinePrice.equals("") || machineLocat.equals("")
                || machineType.equals("") || machineParts.equals("") || maintainPlan.equals("") || machineQuant.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        } else {

            final Intent machine_intent = new Intent();
            machine_intent.putExtra("machineName", machineName);
            machine_intent.putExtra("machineDescription", machineDescp);
            machine_intent.putExtra("machinePrice", machinePrice);
            machine_intent.putExtra("machineLocation", machineLocat);
            machine_intent.putExtra("machineType", machineType);
            machine_intent.putExtra("machineParts", machineParts);
            machine_intent.putExtra("maintainencePlan", maintainPlan);
            machine_intent.putExtra("machineQuant", machineQuant);
            if (filePath != null) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                //progressDialog.setTitle("Uploading...");
                //progressDialog.show();

                StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
                machineImage = ref.getPath();
                machine_intent.putExtra("machineImage", machineImage);
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //progressDialog.dismiss();
                                Toast.makeText(machine_add.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //progressDialog.dismiss();
                                Toast.makeText(machine_add.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }
            setResult(RESULT_OK, machine_intent);
            finish();
        }

    }
}
