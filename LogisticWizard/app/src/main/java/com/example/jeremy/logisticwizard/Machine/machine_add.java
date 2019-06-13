package com.example.jeremy.logisticwizard.Machine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;

import com.example.jeremy.logisticwizard.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class machine_add extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //set up variables
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
        storage=FirebaseStorage.getInstance("gs://logisticwizard-6d896.appspot.com/");
        storageReference = storage.getReference();

        //link buttons and text in xml to set variables
        add_machine = (Button)findViewById(R.id.add_machine_button);
        machine_name = (EditText)findViewById(R.id.enterMachineName);
        machine_type = (EditText)findViewById(R.id.machineType);
        machine_parts = (EditText)findViewById(R.id.partsInfo);
        machine_descrip = (EditText)findViewById(R.id.enterMachineDescription);
        machine_price = (EditText)findViewById(R.id.machinePrice);
        machine_location = (EditText)findViewById(R.id.machineLocation);
        image = findViewById(R.id.add_machine_image);

        //set up spinners to select quantity and days
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

    }

    @Override
    public void onClick(View view) {
        if(view == add_machine){
            addMachine();
        }
        if(view == image){
            showNormalDialog();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * the user wants to add the information of a new machine
     * return all information as a string to Inventory(include onSuccess Code)
     *
     */
    public void addMachine(){
        //get all input information
        String machineName = machine_name.getText().toString().trim();
        String machineDescp = machine_descrip.getText().toString().trim();
        String machinePrice = machine_price.getText().toString().trim();
        String machineLocat = machine_location.getText().toString().trim();
        String machineType = machine_type.getText().toString().trim();
        String machineParts = machine_parts.getText().toString().trim();
        String maintainPlan = machinePlanSpinner.getSelectedItem().toString().trim();
        String machineQuant = machineQuantitySpinner.getSelectedItem().toString().trim();
        String machineImage = "null";

        //user cannot leave any of them blank other it will throw messages
        if (machineName.equals("")||machineDescp.equals("")||machinePrice.equals("")||
                machineLocat.equals("") ||machineType.equals("")||machineParts.equals("")
                ||maintainPlan.equals("")||machineQuant.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        }else {
            //put all input informations into variables, transfer them to another activity to save
            Intent machine_intent = new Intent();
            machine_intent.putExtra("machineName", machineName);
            machine_intent.putExtra("machineDescription", machineDescp);
            machine_intent.putExtra("machinePrice", machinePrice);
            machine_intent.putExtra("machineLocation", machineLocat);
            machine_intent.putExtra("machineType", machineType);
            machine_intent.putExtra("machineParts", machineParts);
            machine_intent.putExtra("maintainencePlan", maintainPlan);
            machine_intent.putExtra("machineQuant", machineQuant);

            //upload image of machines
            if(filePath != null)
            {
                //final ProgressDialog progressDialog = new ProgressDialog(this);
                StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                machineImage = ref.getPath(); //get path of images in gallery
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
                                Toast.makeText(machine_add.this, "Failed "+e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
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
            setResult(RESULT_OK, machine_intent);
            finish();
        }
    }

    //pick image from gallery
    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 73);
    }

    //take image with camera
    private void takeImage(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile(); //get photo file
        } catch (IOException ex) {
            // Error occurred while creating the File

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            filePath = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
            startActivityForResult(takePictureIntent, 74);
        }
    }

    //Reference: https://developer.android.com/training/camera/photobasics#TaskGallery
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        //Create an image file name
        //get image informations
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 73 && resultCode == RESULT_OK && data != null )
        {
//            Toast.makeText(this,
//                    "Error occur:"+resultCode,  Toast.LENGTH_SHORT).show();
            filePath = data.getData();
            try {
                //organize image size and display images on the screen
                float scale = this.getResources().getDisplayMetrics().density;
                int width = (int)(350*scale+0.5f);
                int height = (int)(200*scale+0.5f);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Picasso.with(this).load(filePath).resize(width, height).into(image);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if(data.hasExtra("data")){
                Bitmap bitMap = data.getParcelableExtra("data");
            }
        }else if(requestCode == 74 && resultCode == RESULT_OK
                && data != null ){
            try
            {
                float scale = this.getResources().getDisplayMetrics().density;
                int width = (int)(350*scale+0.5f);
                int height = (int)(200*scale+0.5f);
                Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath));
                Picasso.with(this).load(filePath).resize(width, height).into(image);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
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

    private void showNormalDialog(){
        /* @setIcon (set icon of dialog)
         * @setTitle (set title of dialog)
         * @setMessage (set message of dialog)
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setTitle("Add a picture for machine");
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
        // display dialog
        normalDialog.show();
    }


}
