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

public class ShiftReyceviewAdapter extends RecyclerView.Adapter<MyShiftRecycelViewHolder>
{
    ArrayList<Shift> shiftArrayList;


    public ShiftReyceviewAdapter(ArrayList<Shift> arrayList)
    {
        this.shiftArrayList=arrayList;
    }
    @NonNull
    @Override
    public MyShiftRecycelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_item_view_upcoming,parent,false);
        MyShiftRecycelViewHolder myShiftRecycelViewHolder =new MyShiftRecycelViewHolder(view);
        return myShiftRecycelViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyShiftRecycelViewHolder holder, int position) {

        holder.shiftId.setText(shiftArrayList.get(position).shiftId);
    }

    @Override
    public int getItemCount() {
        return shiftArrayList.size();
    }
}

class MyShiftRecycelViewHolder extends RecyclerView.ViewHolder
{

    TextView shiftId;

    public MyShiftRecycelViewHolder(@NonNull View itemView) {
        super(itemView);

        shiftId=itemView.findViewById(R.id.shiftid);
    }
}
