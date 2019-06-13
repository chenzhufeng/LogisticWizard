package com.example.jeremy.logisticwizard.Work_order;

import android.app.ProgressDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.logisticwizard.Custom_object.workorder_info;
import com.example.jeremy.logisticwizard.R;
import com.example.jeremy.logisticwizard.home_page;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class workorder_edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String role = home_page.role;
    private EditText maintain_plan;
    private EditText order_title;
    private Spinner order_status;
    private Spinner order_priority;
    private TextView order_Creator;
    private TextInputEditText order_description;
    private EditText order_cost;
    private TextView order_Duedate;
    private EditText order_note;
    private ImageButton order_image;
    private TextView order_machine;
    private Uri filePath;
    private Button save_button;
    private Spinner maintenanceSpinner;
    String orderTitle;
    String orderImage;
    List<String> temple=new ArrayList<>();
    List<String> maintenanceList = new ArrayList<>();
    protected DatabaseReference mDatabase;
    protected DatabaseReference userDatabase;
    protected StorageReference mStorage;
    StorageReference imageRef;
    private boolean isEmployee;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_edit);

        mDatabase  = FirebaseDatabase.getInstance().getReference("orders");
        mStorage = FirebaseStorage.getInstance().getReference();
        order_title = findViewById(R.id.titleText);
        order_Creator = findViewById(R.id.creatorHolder);
        order_description=findViewById(R.id.descriptionInput);
        order_cost = findViewById(R.id.editText);
        order_Duedate = findViewById(R.id.editText2);
        order_image = findViewById(R.id.photoHolder);
        order_machine = findViewById(R.id.machineHolder);
        order_note = findViewById(R.id.order_note);
        maintain_plan = findViewById(R.id.maintain_plan);

        order_status = findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order_status.setAdapter(adapter);
        order_status.setOnItemSelectedListener(this);

        order_priority = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.priorities, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order_priority.setAdapter(adapter1);
        order_priority.setOnItemSelectedListener(this);

        save_button = findViewById(R.id.saveButton);

        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();

        orderTitle = data.get("orderTitle").toString();

        if (role.equals("Facility Worker")) {
            isEmployee = true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /* NOTICE - Accessing mDatabase MUST be put into a loop and put into its own
     * function to make code more readable and clean */
    protected void onStart(){
        super.onStart();

        order_title.setText(orderTitle);
        mDatabase.child(orderTitle).child("order_status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderStatus;
                orderStatus = (String) dataSnapshot.getValue();
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(workorder_edit.this,
                        R.array.statuses, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                order_status.setAdapter(adapter);
                if (orderStatus != null) {
                    int spinnerPosition = adapter.getPosition(orderStatus);
                    order_status.setSelection(spinnerPosition);
                }

                if (isEmployee) {
                    order_status.setEnabled(false);
                } else {
                    order_status.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_priority").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderPriority;
                orderPriority = (String) dataSnapshot.getValue();
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(workorder_edit.this,
                        R.array.priorities, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                order_priority.setAdapter(adapter1);
                if (orderPriority != null) {
                    int spinnerPosition = adapter1.getPosition(orderPriority);
                    order_priority.setSelection(spinnerPosition);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_creator").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderCreator;
                orderCreator = (String) dataSnapshot.getValue();
                order_Creator.setText(orderCreator);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }});

        mDatabase.child(orderTitle).child("order_descrip").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderDescrip;
                orderDescrip = (String) dataSnapshot.getValue();
                order_description.setText(orderDescrip);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_cost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderCost;
                orderCost = (String) dataSnapshot.getValue();
                order_cost.setText(orderCost);
                //*
                if (isEmployee) {
                    order_cost.setEnabled(false);
                } else {
                    order_cost.setEnabled(true);
                }
                //*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_dates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String orderDate;

                orderDate = (String) dataSnapshot.getValue();
                order_Duedate.setText(orderDate);
                order_Duedate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        //this style works well on my emulator: android.R.style.Theme_DeviceDefault_Light_Dialo
                        DatePickerDialog dialog = new DatePickerDialog(workorder_edit.this,
                                android.R.style.Theme_DeviceDefault_Light_Dialog, mDateSetListener,
                                year, month, day);
                        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = month + "/" + day + "/" + year;
                        order_Duedate.setText(date);
                    }
                };
                //--------------------------
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        mDatabase.child(orderTitle).child("order_note").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String orderNote;

                orderNote = (String) dataSnapshot.getValue();
                order_note.setText(orderNote);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("maintain_plan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maintain_plan.setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        mDatabase.child(orderTitle).child("order_machine").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String orderMachine;
                orderMachine = (String) dataSnapshot.getValue();
                order_machine.setText(orderMachine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child(orderTitle).child("order_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    return;
                }
                String orderImagePath;
                orderImagePath = (String) dataSnapshot.getValue();
                orderImage = orderImagePath;
                imageRef = mStorage.child(orderImagePath);
                try {
                    final File localimage = File.createTempFile(orderTitle,"jpg");
                    imageRef.getFile(localimage).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Local temp file has been created
                            Bitmap bitmap = BitmapFactory.decodeFile(localimage.getPath());
                            order_image.setImageBitmap(bitmap);

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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        //Making spinner to display all maintenance works name for admin to assign task
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
        maintenanceList.add("select worker");
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    String username = userSnapshot.child("Name").getValue(String.class);
                    String role = userSnapshot.child("Role").getValue(String.class);
                    if(role.equals("Maintenance Worker")){
                        maintenanceList.add(username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        maintenanceSpinner = findViewById(R.id.maintenanceSpinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, maintenanceList);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        maintenanceSpinner.setAdapter(adapter3);
        maintenanceSpinner.setOnItemSelectedListener(this);

        // Check which items can be edited by the current user
        if (role.equals("Facility Worker")) {
            maintenanceSpinner.setEnabled(false);
            order_note.setEnabled(false);
            maintain_plan.setEnabled(false);
            order_priority.setEnabled(true);
            order_description.setEnabled(true);
            order_Duedate.setEnabled(true);
        } else if (role.equals("Maintenance Worker")) {
            maintenanceSpinner.setEnabled(true);
            order_note.setEnabled(true);
            maintain_plan.setEnabled(true);
            order_priority.setEnabled(false);
            order_description.setEnabled(false);
            order_Duedate.setEnabled(false);
        } else {
            maintenanceSpinner.setEnabled(true);
            order_note.setEnabled(true);
            maintain_plan.setEnabled(true);
            order_priority.setEnabled(true);
            order_description.setEnabled(true);
            order_Duedate.setEnabled(true);
        }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_edition();
                Intent intent = new Intent (view.getContext(), workorder_view.class);
                intent.putExtra("orderTitle", order_title.getText().toString().trim());
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
                finish();
            }
        });


        order_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalDialog();
            }
        });
    }

    private void save_edition(){
        String orderTitle2 = order_title.getText().toString().trim();
        String orderCreator = order_Creator.getText().toString().trim();
        String orderDescrip = order_description.getText().toString().trim();
        String orderCost = order_cost.getText().toString().trim();
        String orderDuedate = order_Duedate.getText().toString().trim();

        String orderStatus = order_status.getSelectedItem().toString().trim();
        String orderPriority = order_priority.getSelectedItem().toString().trim();
        String orderMachine = order_machine.getText().toString().trim();
        String orderNote = order_note.getText().toString().trim();
        String orderPlan = maintain_plan.getText().toString().trim();
        String maintenance_worker = maintenanceSpinner.getSelectedItem().toString().trim();

        if (orderTitle2.equals("")||orderCreator.equals("")||orderDescrip.equals("")||orderCost.equals("")
                || orderDuedate.equals("")||orderStatus.equals("")||orderPriority.equals("")||maintenance_worker.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        } else {
            if(!orderTitle2.equals(orderTitle)) {
                mDatabase.child(orderTitle).removeValue();
            }

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot orderSnapshot : dataSnapshot.getChildren()){
                        if(!orderSnapshot.getKey().equals(orderTitle)) {
                            temple.add(orderSnapshot.getKey());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
            if(temple.contains(orderTitle2)){
                Toast.makeText(workorder_edit.this,
                        "workorder already exists, please enter a new name.", Toast.LENGTH_LONG).show();
                return;

            } else {
                if(filePath != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    //progressDialog.setTitle("Uploading...");
                    //progressDialog.show();

                    StorageReference ref = mStorage.child("images/"+ UUID.randomUUID().toString());
                    orderImage = ref.getPath();

                    ref.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //progressDialog.dismiss();
                                    Toast.makeText(workorder_edit.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //progressDialog.dismiss();
                                    Toast.makeText(workorder_edit.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                workorder_info order = new workorder_info(orderTitle2, orderDescrip, orderNote, orderDuedate,
                        orderCost, orderPriority, orderPlan, orderStatus, orderImage, orderCreator, orderMachine, maintenance_worker);
                mDatabase.child(orderTitle2).setValue(order);
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

        normalDialog.setTitle(" Uploading picture");
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 41);
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
            startActivityForResult(takePictureIntent, 42);
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
        if(requestCode == 41 && resultCode == RESULT_OK && data != null ) {
            Toast.makeText(this,
                    "Error occur:"+resultCode,  Toast.LENGTH_SHORT).show();
            filePath = data.getData();
            try {
                float scale = this.getResources().getDisplayMetrics().density;
                int width = (int)(350*scale+0.5f);
                int height = (int)(200*scale+0.5f);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Picasso.with(this).load(filePath).resize(width, height).into(order_image);
                //image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(data.hasExtra("data")){
                Bitmap bitMap = data.getParcelableExtra("data");
            }

        } else if(requestCode == 42 && resultCode == RESULT_OK && data != null ) {
            try {
                float scale = this.getResources().getDisplayMetrics().density;
                int width = (int)(350*scale+0.5f);
                int height = (int)(200*scale+0.5f);
                Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath));
                Picasso.with(this).load(filePath).resize(width, height).into(order_image);
                //edit_machine_image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*
            boolean t = true;
            if(data.getData() == null) {
                t = false;
            }
            //*/
            Toast.makeText(this, "No image added", Toast.LENGTH_SHORT).show();
        }
    }
}
