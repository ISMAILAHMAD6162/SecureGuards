package com.secure.secureguards.ActiveShift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.secure.secureguards.Models.Shift;
import com.secure.secureguards.Models.Site;
import com.secure.secureguards.R;

import java.util.List;

public class Shift_Start_Activity extends AppCompatActivity {

    private FirebaseFirestore db;
    String shiftId;
    public Site site;
    public Shift shift;
    private Button open_shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_start);
        open_shift=findViewById(R.id.open_shift);
        db=FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if (intent.hasExtra("object")) {
         shift = (Shift) intent.getSerializableExtra("object");

            getSiteData(shift.siteId);


            open_shift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Active_Shift_Activity.class);
                startActivity(intent);

                }
            });

        }

    }

    public void getSiteData(String siteId) {
        db.collection("Site").document(siteId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                 site = documentSnapshot.toObject(Site.class);

                Toast.makeText(getApplicationContext(), "Faild"+site.locationLatitude, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}