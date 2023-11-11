package com.secure.secureguards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;

public class Dash_Board_Activity extends AppCompatActivity {


    NavigationBarView bottomNavigationItemView;
    Home_Fragment home;
    Schedule_Fragment shift;
    Profile_Fragment profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

          bottomNavigationItemView=findViewById(R.id.bottomNavigationView);
          home=new Home_Fragment();
          shift=new Schedule_Fragment();
          profile=new Profile_Fragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, home)
                .commit();

         bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                 if (item.getItemId() == R.id.person) {
                     getSupportFragmentManager()
                             .beginTransaction()
                             .replace(R.id.flFragment, home)
                             .commit();
                     return true;
                 }
                else if (item.getItemId() == R.id.settings) {
                     getSupportFragmentManager()
                             .beginTransaction()
                             .replace(R.id.flFragment, shift)
                             .commit();
                     return true;
                 }
                 else if (item.getItemId() == R.id.home) {
                     getSupportFragmentManager()
                             .beginTransaction()
                             .replace(R.id.flFragment, profile)
                             .commit();
                     return true;
                 }
                 else  return false;
             }
                 });

                 // setContentView(R.layout.activity_main);
                 this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                 getSupportActionBar().setDisplayShowCustomEnabled(true);
                 getSupportActionBar().setCustomView(R.layout.action_bar);
                 //getSupportActionBar().setElevation(0);
                 View view = getSupportActionBar().getCustomView();



}}