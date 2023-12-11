package com.secure.secureguards;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.secure.secureguards.ActiveShift.Shift_Start_Activity;
import com.secure.secureguards.Models.Shift;
import com.secure.secureguards.Shift_Managment.CurrentDateItemListernInterface;
import com.secure.secureguards.Shift_Managment.CurrentDateShitRecyviewAdapter;
import com.secure.secureguards.Shift_Managment.ShiftLogRecycleView;
import com.secure.secureguards.Shift_Managment.ShiftReyceviewAdapter;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Schedule_Fragment extends Fragment implements CurrentDateItemListernInterface {

    private FirebaseFirestore db;
    private RecyclerView currentDateShiftRecycleView;
    private CurrentDateShitRecyviewAdapter dateShitRecyviewAdapter;
    private ArrayList<Shift> currentDatashiftArray;
    private ShiftLogRecycleView shiftLogRecycleView;
    private RecyclerView shiftRecycleviewlog;
    private List<String> shiftIdList;
    private ArrayList<Shift> shiftArrayListData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_schedule_, container, false);

       db = FirebaseFirestore.getInstance();
        shiftArrayListData=new ArrayList<Shift>();
        shiftIdList=new ArrayList<String>();
        currentDatashiftArray=new ArrayList<Shift>();
        currentDateShiftRecycleView=view.findViewById(R.id.currentdayrecyclview);
       dateShitRecyviewAdapter=new CurrentDateShitRecyviewAdapter(currentDatashiftArray,this::curentItemShiftClick);
        shiftLogRecycleView=new ShiftLogRecycleView(shiftArrayListData);
        shiftRecycleviewlog=view.findViewById(R.id.shift_current_date_shiftss_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        shiftRecycleviewlog.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        currentDateShiftRecycleView.setLayoutManager(linearLayoutManager2);
        shiftRecycleviewlog.setAdapter(shiftLogRecycleView);
        currentDateShiftRecycleView.setAdapter(dateShitRecyviewAdapter);
        retrieveItemsForMonth(2023,11,"123456789");
        return view;

    }


    private void retrieveItemsForMonth(int year, int month,String guardId) {

        CollectionReference siteRotaCollection=db.collection("GUARDROTA").document(guardId).collection("YEARS");

        CollectionReference monthCollection = siteRotaCollection
                .document(String.valueOf(year))
                .collection("MONTHS")
                .document(String.valueOf(month)).collection("DAYS");
      //  Toast.makeText(getContext(),"out 1 ",Toast.LENGTH_LONG).show();

        monthCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                //    Toast.makeText(getContext(),"in 1 "+task.getResult().size(),Toast.LENGTH_LONG).show();

                    for (DocumentSnapshot dayDocument : task.getResult().getDocuments())
                    {

                      //  Toast.makeText(getContext(),"in 2 ",Toast.LENGTH_LONG).show();

                        List<String> items = (List<String>) dayDocument.get("ShiftIds");
                        if (items != null) {
                            // Do something with the retrieved items for each day
                            for (String item : items) {

                              //  Toast.makeText(getContext(),"here is loop "+item,Toast.LENGTH_LONG).show();
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
        Calendar calendar= Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month="11";//String.valueOf(calendar.get(Calendar.MONTH));
        String day= "29";///String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

       // Toast.makeText(getContext(), "object with year"+year+"month "+month+"day"+day, Toast.LENGTH_LONG).show();

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

                                        shiftLogRecycleView.notifyDataSetChanged();
                                        //
                                        Shift obj = document.toObject(Shift.class);

                                     //   Toast.makeText(getContext(), "object with year"+obj.year+"month "+obj.month+"day"+obj.day, Toast.LENGTH_LONG).show();

                                        if(obj.year.equals(year)&&obj.month.equals(month)&&obj.day.equals(day))
                                        {
                                           currentDatashiftArray.add(obj);

                                     //       Toast.makeText(getContext(), "object with year"+obj.year+"month "+obj.month+"day"+obj.day, Toast.LENGTH_LONG).show();

                                           dateShitRecyviewAdapter.notifyDataSetChanged();
                                        }
                                        else {
                                            shiftArrayListData.add(obj);
                                        }


                                        shiftLogRecycleView.notifyDataSetChanged();


                                    }
                                    // For example, String field = document.getString("field_name");
                                }
                            }
                        });
            }
        }

    }

    @Override
    public void curentItemShiftClick(int index) {

       // Toast.makeText(getContext(),"INDEX"+currentDatashiftArray.get(index).shiftId,Toast.LENGTH_LONG).show();
        Intent intent=new Intent(getContext(), Shift_Start_Activity.class);
        intent.putExtra("object",currentDatashiftArray.get(index));

        startActivity(intent);

    }
}