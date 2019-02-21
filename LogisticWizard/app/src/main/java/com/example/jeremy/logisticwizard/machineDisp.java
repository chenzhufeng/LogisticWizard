package com.example.jeremy.logisticwizard;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class machineDisp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machinedisp_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //getWindow().setLayout((int) (width * .9), (int) (height * .8));


    }
}
