package com.secure.secureguards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_activity extends AppCompatActivity {

    Button start_dashbaord_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();

        start_dashbaord_activity=findViewById(R.id.startdashbaord);
        start_dashbaord_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login_activity.this,Dash_Board_Activity.class);
                startActivity(intent);

            }
        });

    }
}