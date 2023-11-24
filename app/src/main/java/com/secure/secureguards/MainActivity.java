package com.secure.secureguards;
import static com.secure.secureguards.Utils.Constant.setLoginStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.secure.secureguards.Screens.AccountActivity;
import com.secure.secureguards.Screens.UpdateProfileActivity;

public class MainActivity extends AppCompatActivity {

    private Button start_dashboard;
    //private FirebaseFirestore db;
    Courses d;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }




}