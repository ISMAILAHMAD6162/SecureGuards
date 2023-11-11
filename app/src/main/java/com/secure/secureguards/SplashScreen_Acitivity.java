package com.secure.secureguards;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreen_Acitivity extends AppCompatActivity {


    Button start_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_acitivity);
        this.getSupportActionBar().hide();
        start_login=findViewById(R.id.startLogin);
        start_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SplashScreen_Acitivity.this,Login_activity.class);
                startActivity(intent);

            }
        });

    }
}