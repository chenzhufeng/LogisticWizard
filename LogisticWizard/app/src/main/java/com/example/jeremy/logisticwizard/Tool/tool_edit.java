package com.example.jeremy.logisticwizard.Tool;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
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

import com.example.jeremy.logisticwizard.Custom_object.tool_info;
import com.example.jeremy.logisticwizard.Machine.machine_edit;
import com.example.jeremy.logisticwizard.R;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class tool_edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String toolName;
    String toolName2;
    String toolDescp;
    String toolPrice;
    String toolLocat;
    String toolType;
    String toolParts;
    String maintainPlan;
    String toolQuant;
    String toolImage;
    List<String> temple=new ArrayList<>();
    private EditText name;
    private EditText type;
    //private EditText part;
    private EditText price;
    private EditText location;
    //private Spinner toolPlanSpinner;
    private Spinner toolQuantitySpinner;
    private EditText description;
    private ImageButton edit_tool_image;
    protected DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("tools");;
    private Uri filePath;
    StorageReference storageReference;

    private Button save;
    private View.OnClickListener saveOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            saveInfo();
            Intent tool_intent = new Intent(v.getContext(), tool_disp.class);
            tool_intent.putExtra("toolName", toolName2);
            tool_intent.putExtra("toolDescription", toolDescp);
            tool_intent.putExtra("toolPrice", toolPrice);
            tool_intent.putExtra("toolLocation", toolLocat);
            tool_intent.putExtra("toolType", toolType);
            //tool_intent.putExtra("toolParts", toolParts);
            //tool_intent.putExtra("maintainencePlan", maintainPlan);
            tool_intent.putExtra("toolQuant", toolQuant);
            tool_intent.putExtra("toolImage", toolImage);
            tool_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(tool_intent);

        }

    };

    private View.OnClickListener toolImageOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            showNormalDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_edit);

        storageReference = FirebaseStorage.getInstance().getReference();

        toolQuantitySpinner = findViewById(R.id.quantityToolsSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolQuantitySpinner.setAdapter(adapter);
        toolQuantitySpinner.setOnItemSelectedListener(this);


        Intent tool_info = getIntent();
        Bundle data = tool_info.getExtras();

        toolName = (String)data.get("toolName");
        toolDescp = (String)data.get("toolDescription");
        toolPrice = (String)data.get("toolPrice");
        toolLocat = (String)data.get("toolLocation");
        toolType = (String)data.get("toolType");
        //toolParts = (String)data.get("toolParts");
        //maintainPlan = (String)data.get("maintainencePlan");
        toolQuant = (String)data.get("toolQuant");
        toolImage = (String)data.get("toolImage");


        name = findViewById(R.id.toolsNameText);
        type = findViewById(R.id.toolsTypeText);
        //part = findViewById(R.id.toolsPriceText);
        price = findViewById(R.id.toolsPriceText);
        location = findViewById(R.id.toolsLocationText);
        description = findViewById(R.id.toolsDescriptionText);
        edit_tool_image = findViewById(R.id.edit_tool_image);

        //make save button onclickable
        save = findViewById(R.id.toolsSaveButton);

        name.setText(toolName);
        type.setText(toolType);
        //part.setText(toolParts);
        price.setText(toolPrice);
        location.setText(toolLocat);
        description.setText(toolDescp);

        save.setOnClickListener(saveOnClickListener);
        edit_tool_image.setOnClickListener(toolImageOnClickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        StorageReference imageRef = storageReference.child(toolImage);
        try {
            final File localimage = File.createTempFile(toolName,"jpg");
            imageRef.getFile(localimage).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localimage.getPath());
                    edit_tool_image.setImageBitmap(bitmap);

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
        toolName2 = name.getText().toString().trim();
        toolDescp = description.getText().toString().trim();
        toolPrice = price.getText().toString().trim();
        toolLocat = location.getText().toString().trim();
        toolType = type.getText().toString().trim();
        //toolParts = part.getText().toString().trim();
        //maintainPlan = toolPlanSpinner.getSelectedItem().toString().trim();
        toolQuant = toolQuantitySpinner.getSelectedItem().toString().trim();

        if (toolName2.equals("")||toolDescp.equals("")||toolPrice.equals("")||toolLocat.equals("")
                ||toolType.equals("")||toolQuant.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        }else {
            if(!toolName2.equals(toolName)) {
                mDatabase.child(toolName).removeValue();
            }

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot toolSnapshot : dataSnapshot.getChildren()){
                        if(!toolSnapshot.getKey().equals(toolName)){
                            temple.add(toolSnapshot.getKey());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if(temple.contains(toolName2)){
                Toast.makeText(tool_edit.this,
                        "tool already exists, please enter a new name.", Toast.LENGTH_LONG).show();
                return;

            }
            else {
                if(filePath != null)
                {
                    StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                    toolImage = ref.getPath();

                    ref.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(tool_edit.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(tool_edit.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

                tool_info tool = new tool_info(toolName2, toolDescp, toolPrice, toolLocat,
                        toolType, toolQuant, toolImage);
                mDatabase.child(toolName2).setValue(tool);
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            filePath = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
            startActivityForResult(takePictureIntent, 89);
        }
    }

    //Reference: https://developer.android.com/training/camera/photobasics#TaskGallery
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
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
        if(requestCode == 88 && resultCode == RESULT_OK
                && data != null )
        {
//            Toast.makeText(this,
//                    "Error occur:"+resultCode,  Toast.LENGTH_SHORT).show();
            filePath = data.getData();
            try {
                float scale = this.getResources().getDisplayMetrics().density;
                int width = (int)(350*scale+0.5f);
                int height = (int)(200*scale+0.5f);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Picasso.with(this).load(filePath).resize(width, height).into(edit_tool_image);
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
        else if(requestCode == 89 && resultCode == RESULT_OK
                && data != null ){
            try
            {
                float scale = this.getResources().getDisplayMetrics().density;
                int width = (int)(350*scale+0.5f);
                int height = (int)(200*scale+0.5f);
                Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath));
                Picasso.with(this).load(filePath).resize(width, height).into(edit_tool_image);
                //edit_machine_image.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }


        }

    }

}