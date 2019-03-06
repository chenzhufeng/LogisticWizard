package com.example.jeremy.logisticwizard;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

public class workorder_add extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    final static int galleryPic = 1;

    private TextInputEditText orderTitle;
    private TextInputEditText orderDescription;
    private TextInputEditText orderImage;
    private Spinner orderPriority;
    private Spinner orderPlanSpinner;
    private TextInputEditText orderNote;
    private Spinner orderStatus;
    private TextInputEditText orderDueDate;
    private EditText orderCost;
    private Button submit;
    private ImageButton image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_add);
        orderTitle = findViewById(R.id.titleInput);
        orderDescription = findViewById(R.id.textInputLayout);
        image = findViewById(R.id.imageButton);
        orderNote = findViewById(R.id.textInputLayout2);
        orderDueDate = findViewById(R.id.dueDateInput);
        orderCost = findViewById(R.id.orderCost);
        submit = findViewById(R.id.submitButton);

        orderPriority = findViewById(R.id.orderPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priorities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderPriority.setAdapter(adapter);
        orderPriority.setOnItemSelectedListener(this);

        orderStatus = findViewById(R.id.orderStatus);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderStatus.setAdapter(adapter1);
        orderStatus.setOnItemSelectedListener(this);

        orderPlanSpinner = findViewById(R.id.maintenanceSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.machineQuantityStringArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderPlanSpinner.setAdapter(adapter2);
        orderPlanSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view == submit){
            addOrder();
            //Intent intent = new Intent(view.getContext(),order.class);
            //startActivity(intent);
        }
//        if(view == image){
//            Intent pictureIntent = new Intent();
//            pictureIntent.setAction(Intent.ACTION_PICK);
//            pictureIntent.setType("image/*");
//            startActivityForResult(pictureIntent, galleryPic);
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * the user wants to add the information of a new order
     * return all information as a string to Inventory(include onSuccess Code)
     *
     */
    public void addOrder(){
        String order_title = orderTitle.getText().toString().trim();
        String order_descp = orderDescription.getText().toString().trim();
        String order_note = orderNote.getText().toString().trim();
        String order_DueDate = orderDueDate.getText().toString().trim();
        String order_cost = orderCost.getText().toString().trim();
        String user_image = "null";

        String maintainPlan = orderPlanSpinner.getSelectedItem().toString().trim();
        String order_priority = orderPriority.getSelectedItem().toString().trim();
        String order_status = orderStatus.getSelectedItem().toString().trim();


        if (order_title.equals("")||order_descp.equals("")||order_note.equals("")||order_DueDate.equals("")
                ||order_cost.equals("")||order_priority.equals("")||maintainPlan.equals("")||order_status.equals("")) {
            Toast.makeText(this,
                    "Please enter all information or leave NONE.", Toast.LENGTH_LONG).show();
            return;
        }else {

            Intent order_intent = new Intent();
            order_intent.putExtra("orderTitle", order_title);
            order_intent.putExtra("orderDescription", order_descp);
            order_intent.putExtra("orderNote", order_note);
            order_intent.putExtra("orderDueDate", order_DueDate);
            order_intent.putExtra("orderCost", order_cost);
            order_intent.putExtra("userImage", user_image);
            order_intent.putExtra("orderPriority", order_priority);
            order_intent.putExtra("maintainencePlan", maintainPlan);
            order_intent.putExtra("orderStatus", order_status);
            setResult(RESULT_OK, order_intent);
            finish();
        }
    }



}
