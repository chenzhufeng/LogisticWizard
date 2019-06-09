package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.provider.MediaStore;
import android.content.DialogInterface;

public class add_a_machine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button add_machine;
    private ImageButton machine_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_machine);

        add_machine = (Button)findViewById(R.id.add_machine_button);
        machine_image = (ImageButton) findViewById(R.id.dummy_takephoto);
        machine_image.setOnClickListener(this);

        //machine type
        //description of machine
        //image of machine
        //unit price per machine
        //quantity --> spinner
        //location of machine(s)
        //maintainance plan
        Spinner machineQuantitySpinner = findViewById(R.id.quantity_of_machine);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineQuantitySpinner.setAdapter(adapter);
        machineQuantitySpinner.setOnItemSelectedListener(this);

        Spinner machinePlanSpinner = findViewById(R.id.maintenancePlan_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.machinePlanStringArray, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machinePlanSpinner.setAdapter(adapter1);
        machinePlanSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == add_machine){

            Intent intent = new Intent(view.getContext(),Machine.class);
            startActivity(intent);
        }
        if (view == machine_image) {
            // used ideas and code from this source:
            //https://stackoverflow.com/questions/43519497/android-imageview-imagebutton-with-image-from-gallery-or-camera
            List<String> options = new ArrayList<String>();
            options.add("camera");
            options.add("gallery");
            options.add("cancel");

            //Create sequence of items
            final CharSequence[] Sources = options.toArray(new String[options.size()]);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("select image source");
            dialogBuilder.setItems(Sources, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (item==0) {//camera
                        //Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                    }else if (item == 1) {//gallery
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                    }
                }
            });
            //Create alert dialog object via builder
            AlertDialog alertDialogObject = dialogBuilder.create();
            //Show the dialog
            alertDialogObject.show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
// imageButton where you want to set image
            machine_image.setImageBitmap(bitmap);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
