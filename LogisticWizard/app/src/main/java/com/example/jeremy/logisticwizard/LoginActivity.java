package com.example.jeremy.logisticwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import android.view.View;
import android.support.v7.app.ActionBar;

public class LoginActivity extends AppCompatActivity {
    private Button SignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(8);
        setContentView(R.layout.activity_login);

        //invoking Button
        SignupButton=(Button)findViewById(R.id.SignUpBut);
        SignupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (v==SignupButton){
                    Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });
        //View decorView = getWindow().getDecorView();
// Hide the status bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        //decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
}
