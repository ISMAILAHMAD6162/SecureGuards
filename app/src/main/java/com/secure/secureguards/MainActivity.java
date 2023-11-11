package com.secure.secureguards;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Button start_dashboard;
    private FirebaseFirestore db;
    Courses d;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_dashboard=findViewById(R.id.dashboar_start);
        d=new Courses();
        db = FirebaseFirestore.getInstance();

        start_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Start DashBoarButon cliked",Toast.LENGTH_SHORT).show();
                 start_dashboard();


            }
        });


        //addDataToFirestore("abc","SBC","22");



    }

    private void addDataToFirestore(String courseName, String courseDescription, String courseDuration) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbCourses = db.collection("Courses");

        // adding our data to our courses object class.
        Courses courses = new Courses(courseName, courseDescription, courseDuration);

        // below method is use to add data to Firebase Firestore.
        dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(MainActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(MainActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void start_dashboard()
    {
        Intent intent=new Intent(MainActivity.this,Dash_Board_Activity.class);
        startActivity(intent);
    }


}