package com.example.jeremy.logisticwizard.Work_order;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class workorder_view extends AppCompatActivity {
    private String role = home_page.role;
    private String name = home_page.name;
    private TextView maintain_plan;
    private TextView order_title;
    private TextView order_status;
    private TextView order_priority;
    private TextView order_Creator;
    private TextView order_description;
    private TextView order_cost;
    private TextView order_Duedate;
    private TextView order_note;
    private TextView order_machine;
    private TextView maintenance_worker;
    private ImageView order_image;
    private Button edit_button;
    private Button delete_button;
    private String orderTitle;
    protected DatabaseReference mDatabase;
    protected StorageReference mStorage;
    StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_disp);
        mDatabase = FirebaseDatabase.getInstance().getReference("orders");
        mStorage = FirebaseStorage.getInstance().getReference();
        //imageRef = mStorage.child("/images/cfd4b4b0-6cff-424d-af23-62d4e792f340");
        order_title = findViewById(R.id.titleText);
        order_Creator = findViewById(R.id.creatorHolder);
        order_status = findViewById(R.id.currentStatusText);
        order_priority = findViewById(R.id.currentPriorityText);
        order_description = findViewById(R.id.descriptionInput);
        order_cost = findViewById(R.id.priceText);
        order_Duedate = findViewById(R.id.editText2);
        order_image = findViewById(R.id.orderImage);
        order_machine = findViewById(R.id.order_machine);
        order_note = findViewById(R.id.note);
        maintenance_worker = findViewById(R.id.maintenance_worker);
        maintain_plan = findViewById(R.id.maintain_plan);

        edit_button = findViewById(R.id.editButton);
        delete_button = findViewById(R.id.deleteButton);

        Intent machine_info = getIntent();
        Bundle data = machine_info.getExtras();

        orderTitle = data.get("orderTitle").toString();


    }

    protected void onStart() {
        super.onStart();
        //String role = home_page.role;
        order_title.setText(orderTitle);
        mDatabase.child(orderTitle).child("order_status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderStatus;
                orderStatus = (String) dataSnapshot.getValue();
                order_status.setText(orderStatus);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        mDatabase.child(orderTitle).child("order_priority").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderPriority;
                orderPriority = (String) dataSnapshot.getValue();
                order_priority.setText(orderPriority);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child(orderTitle).child("order_creator").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String orderCreator;
                orderCreator = (String) dataSnapshot.getValue();
                //Log.d("ORDER", "orderCreator = " + orderCreator, null);
                order_Creator.setText(orderCreator);

                Log.d("NAME", orderCreator, null);
                if ((role.equals("Facility Worker")) && (!name.equals(orderCreator))) {
                    edit_button.setVisibility(View.INVISIBLE);
                    delete_button.setVisibility(View.INVISIBLE);
                } else {
                    edit_button.setVisibility(View.VISIBLE);
                    delete_button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child(orderTitle).child("order_descrip").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String orderDescrip;
                orderDescrip = (String) dataSnapshot.getValue();
                order_description.setText(orderDescrip);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

            }
        });

        mDatabase.child(orderTitle).child("order_dates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String orderDuedate;
                orderDuedate = (String) dataSnapshot.getValue();
                order_Duedate.setText(orderDuedate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
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

        mDatabase.child(orderTitle).child("maintenance_worker").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String maintenanceWorker;
                maintenanceWorker = (String) dataSnapshot.getValue();
                maintenance_worker.setText(maintenanceWorker);
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
                imageRef = mStorage.child(orderImagePath);
                try {
                    final File localimage = File.createTempFile(orderTitle, "jpg");
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
                            onStart();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        mDatabase.child(orderTitle).child("order_note")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        order_note.setText((String) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError databaseError) {}
                });

        //*
        mDatabase.child(orderTitle).child("maintain_plan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maintain_plan.setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        //*/

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalDialog();
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), workorder_edit.class);
                intent.putExtra("orderTitle", order_title.getText());
                startActivity(intent);
            }
        });
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
        normalDialog.setMessage("Do you want to delete this work order?");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child(orderTitle).removeValue();
                        Toast.makeText(workorder_view.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (workorder_view.this, workorder_main.class);
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