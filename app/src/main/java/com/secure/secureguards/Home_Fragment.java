package com.secure.secureguards;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.secure.secureguards.Models.Shift;
import com.secure.secureguards.Shift_Managment.ShiftReyceviewAdapter;

import java.util.ArrayList;
import java.util.List;


public class Home_Fragment extends Fragment {
    private FirebaseFirestore db;
    private String activeShiftId;
     private ShiftReyceviewAdapter shiftReyceviewAdapter;
    private RecyclerView shiftRecycleview;
    private List<String> shiftIdList;
    private ArrayList<Shift> shiftArrayListData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_, container, false);
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        shiftArrayListData=new ArrayList<Shift>();
        shiftIdList=new ArrayList<String>();
shiftReyceviewAdapter=new ShiftReyceviewAdapter(shiftArrayListData);
         shiftRecycleview=view.findViewById(R.id.shift_item_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        shiftRecycleview.setLayoutManager(linearLayoutManager);
         shiftRecycleview.setAdapter(shiftReyceviewAdapter);

        retrieveItemsForMonth(2023,11,"123456789");
        chckGrdActveShft("123456789");
        return view;


    }


    private void retrieveItemsForMonth(int year, int month,String guardId) {

        CollectionReference siteRotaCollection=db.collection("GUARDROTA").document(guardId).collection("YEARS");

        CollectionReference monthCollection = siteRotaCollection
                .document(String.valueOf(year))
                .collection("MONTHS")
                .document(String.valueOf(month)).collection("DAYS");
        //Toast.makeText(getContext(),"out 1 ",Toast.LENGTH_LONG).show();

        monthCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                  //  Toast.makeText(getContext(),"in 1 "+task.getResult().size(),Toast.LENGTH_LONG).show();

                    for (DocumentSnapshot dayDocument : task.getResult().getDocuments())
                    {

                       // Toast.makeText(getContext(),"in 2 ",Toast.LENGTH_LONG).show();

                        List<String> items = (List<String>) dayDocument.get("ShiftIds");
                        if (items != null) {
                            // Do something with the retrieved items for each day
                            for (String item : items) {

                             //   Toast.makeText(getContext(),"here is loop "+item,Toast.LENGTH_LONG).show();
                                shiftIdList.add(item);
                            }

                        }
                    }
                    getShiftData(shiftIdList);
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }
            }
        });
    }

    public void getShiftData(List< String> shiftIds)
    {

if (shiftIdList!=null) {

    if (shiftIdList.size() > 0) {

        // Reference to the collection
        // Replace "your_collection_name" with the actual name of your Firestore collection
        CollectionReference collectionReference = db.collection("SHIFT");

        // Use the 'whereIn' query to fetch documents based on an array of document IDs
        collectionReference.whereIn(FieldPath.documentId(), shiftIds)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        //  Toast.makeText(getApplicationContext(), "get All shift with ID"+queryDocumentSnapshots.getDocuments(), Toast.LENGTH_LONG).show();

                        // Handle the query result
                        // queryDocumentSnapshots contains the documents matching the document IDs
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            // Access the data of each document

                            if (queryDocumentSnapshots.size() > 0) {

                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                 shiftReyceviewAdapter.notifyDataSetChanged();
                                //
                                Shift obj = document.toObject(Shift.class);
                                shiftArrayListData.add(obj);
                                  shiftReyceviewAdapter.notifyDataSetChanged();

                                //  Toast.makeText(getApplicationContext(), "get All shift with ID LOOP COUNT" + document.getString("shiftId"), Toast.LENGTH_LONG).show();

                            }
                            // For example, String field = document.getString("field_name");
                        }
                    }
                });
    }
}
    }

public  void chckGrdActveShft(String guardId)
{

    CollectionReference collectionReference = db.collection("ACTIVE_SHIFT");

    collectionReference.document(guardId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {


               activeShiftId=documentSnapshot.getString("Id");
            Toast.makeText(getContext(), "get All shift with ID LOOP COUNT" + activeShiftId, Toast.LENGTH_LONG).show();


        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });

}




}