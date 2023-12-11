package com.secure.secureguards.Shift_Managment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secure.secureguards.Models.Shift;
import com.secure.secureguards.R;

import java.util.ArrayList;

public class CurrentDateShitRecyviewAdapter extends RecyclerView.Adapter<MyCurrentDateShitRecyviewAdapterViewHolder>{


    ArrayList<Shift> shiftArrayList;
    CurrentDateItemListernInterface currentDateItemListernInterface;


    public CurrentDateShitRecyviewAdapter(ArrayList<Shift> arrayList,CurrentDateItemListernInterface currentDateItemListernInterface)
    {
        this.shiftArrayList=arrayList;
        this.currentDateItemListernInterface=currentDateItemListernInterface;
    }
    @NonNull
    @Override
    public MyCurrentDateShitRecyviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.current_date_shift_item_view,parent,false);
        MyCurrentDateShitRecyviewAdapterViewHolder myCurrentDateShitRecyviewAdapterViewHolder =new MyCurrentDateShitRecyviewAdapterViewHolder(view,currentDateItemListernInterface);
        return myCurrentDateShitRecyviewAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCurrentDateShitRecyviewAdapterViewHolder holder, int position) {

    //    holder.shiftId.setText(shiftArrayList.get(position).shiftId);
      //  holder.shiftTime.setText("Start Time"+shiftArrayList.get(position).startTime+"   End Time"+shiftArrayList.get(position).endTime);
      //  holder.shiftDate.setText(shiftArrayList.get(position).year+"-"+shiftArrayList.get(position).month+"-"+shiftArrayList.get(position).day);

    }

    @Override
    public int getItemCount() {
        return shiftArrayList.size();
    }
}

class MyCurrentDateShitRecyviewAdapterViewHolder extends RecyclerView.ViewHolder
{


    TextView shiftId,shiftTime,shiftDate;
    public MyCurrentDateShitRecyviewAdapterViewHolder(@NonNull View itemView,CurrentDateItemListernInterface currentDateItemListernInterface) {
        super(itemView);

       itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               currentDateItemListernInterface.curentItemShiftClick(getAdapterPosition());
           }
       });
        // shiftId=itemView.findViewById(R.id.shiftidcurren);
       // shiftTime=itemView.findViewById(R.id.shiftimecurent);
       // shiftDate=itemView.findViewById(R.id.shiftDatecurrent);
    }
}
