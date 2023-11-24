package com.secure.secureguards;

import static com.secure.secureguards.Utils.Constant.setLoginStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.secure.secureguards.Screens.AccountActivity;

public class Dash_Board_Activity extends AppCompatActivity {


    NavigationBarView bottomNavigationItemView;
    Home_Fragment home;
    Schedule_Fragment shift;
    Profile_Fragment profile;

    private DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

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
        navigationView =findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawer, (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar),R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    setLoginStatus(Dash_Board_Activity.this,false);
                    startActivity(new Intent(Dash_Board_Activity.this, AccountActivity.class));
                    finish();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

         bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                 if (item.getItemId() == R.id.person) {

                     getSupportFragmentManager()
                             .beginTransaction()
                             .replace(R.id.flFragment, profile)
                             .commit();

                     return true;
                 }
                else if (item.getItemId() == R.id.setting) {
                     getSupportFragmentManager()
                             .beginTransaction()
                             .replace(R.id.flFragment, shift)
                             .commit();
                     return true;
                 }
                 else if (item.getItemId() == R.id._home) {
                     getSupportFragmentManager()
                             .beginTransaction()
                             .replace(R.id.flFragment, home)
                             .commit();
                     return true;
                 }
                 else  return false;
             }
                 });





}}