package com.secure.secureguards;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Guard;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Fragment extends Fragment {


    private FirebaseFirestore db;
    Button read_profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        read_profile=view.findViewById(R.id.read_profile);

        db=FirebaseFirestore.getInstance();
        getGuardProfile(view);
        read_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                read_profile.setText("Ahmad");



            }
        });



    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_profile_, container, false);

    }


    public void getGuardProfile(View view)
    {

        CollectionReference guardProfileRef = db.collection("GuardProfile");

        guardProfileRef.document("abc").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Toast.makeText(view.getContext(),documentSnapshot.toString(),Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                {

                    Toast.makeText(view.getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}