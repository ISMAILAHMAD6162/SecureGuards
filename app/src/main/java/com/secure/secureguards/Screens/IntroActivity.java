package com.secure.secureguards.Screens;

import static com.secure.secureguards.Utils.Constant.setAppIntro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.secure.secureguards.R;
import com.secure.secureguards.Screens.SplashActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @SuppressLint("ResourceAsColor")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        addSlide(AppIntroFragment.newInstance("", "", "First Description ", "", R.drawable.app_logo, Color.parseColor("#77d4ff"), 0, Color.parseColor("#000000")));
        addSlide(AppIntroFragment.newInstance("","" ,"First Description","", R.drawable.app_logo, Color.parseColor("#77d4ff"), 0, Color.parseColor("#000000")));
        showSkipButton(false);
        setDoneText("Next");

    }



    @Override
    public void onDonePressed(Fragment currentFragment) {
        setAppIntro(IntroActivity.this,false);
        startActivity(new Intent( IntroActivity.this, SplashActivity.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
