package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class workorder_edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView order_title;
    private Spinner order_status;
    private Spinner order_priority;
    private TextView order_Creator;
    private TextInputEditText order_description;
    private TextView order_cost;
    private TextView order_Duedate;
    private TextView order_note;
    private ImageView order_image;


    private Button edit_button;
    private Button back_button;

    String orderTitle;


    protected DatabaseReference mDatabase;
    protected StorageReference mStorage;
    StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_edit);

        mDatabase  = FirebaseDatabase.getInstance().getReference("orders");
        mStorage = FirebaseStorage.getInstance().getReference();
        //imageRef = mStorage.child("/images/cfd4b4b0-6cff-424d-af23-62d4e792f340");
        order_title = findViewById(R.id.titleText);
        order_Creator = findViewById(R.id.creatorHolder);
        order_description=findViewById(R.id.descriptionInput);
        order_cost=findViewById(R.id.priceText);
        order_Duedate=findViewById(R.id.editText2);
        order_image=findViewById(R.id.orderImage);

        order_status = findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order_status.setAdapter(adapter);
        order_status.setOnItemSelectedListener(this);

        order_priority = findViewById(R.id.prioritySpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.priorities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order_priority.setAdapter(adapter1);
        order_priority.setOnItemSelectedListener(this);

        edit_button = findViewById(R.id.editButton);

        back_button=findViewById(R.id.backButton);

        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();

        orderTitle = data.get("orderTitle").toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void onStart(){
        super.onStart();

        order_title.setText(orderTitle);
        mDatabase.child(orderTitle).child("order_status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderStatus;
                orderStatus = (String) dataSnapshot.getValue();
                int orderStatus2 = Integer.parseInt(orderStatus);
                order_status.setSelection(orderStatus2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_priority").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderPriority;
                orderPriority = (String) dataSnapshot.getValue();
                int orderPriority2 = Integer.parseInt(orderPriority);
                order_priority.setSelection(orderPriority2);

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
                //System.out.println(orderCost);
                order_cost.setText(orderCost);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_dates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderDuedate;
                orderDuedate = (String) dataSnapshot.getValue();
                order_Duedate.setText(orderDuedate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        mDatabase.child(orderTitle).child("order_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderImagePath;
                orderImagePath = (String) dataSnapshot.getValue();
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

    }

}
