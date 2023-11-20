package com.secure.secureguards.Screens;

import static com.secure.secureguards.Utils.Constant.getLoginStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.secure.secureguards.Dash_Board_Activity;
import com.secure.secureguards.MainActivity;
import com.secure.secureguards.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    if(getLoginStatus(SplashActivity.this)){
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }

                    // if no one login move to login screen
                    else {
                        startActivity(new Intent(SplashActivity.this, AccountActivity.class));
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}