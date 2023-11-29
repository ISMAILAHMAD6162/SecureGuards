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

public class ShiftLogRecycleView extends RecyclerView.Adapter<MyshiftLogViewHolder>{



    ArrayList<Shift> shiftArrayList;
    public ShiftLogRecycleView(ArrayList<Shift> arrayList)
    {
        this.shiftArrayList=arrayList;
    }

    @NonNull
    @Override
    public MyshiftLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_log_item_view,parent,false);
        MyshiftLogViewHolder myshiftLogViewHolder =new MyshiftLogViewHolder(view);
        return myshiftLogViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyshiftLogViewHolder holder, int position) {
        holder.shiftId.setText(shiftArrayList.get(position).shiftId);
        holder.shiftTime.setText("Start Time"+shiftArrayList.get(position).startTime+"   End Time"+shiftArrayList.get(position).endTime);
        holder.shiftDate.setText(shiftArrayList.get(position).year+"-"+shiftArrayList.get(position).month+"-"+shiftArrayList.get(position).day);

    }

    @Override
    public int getItemCount() {
        return shiftArrayList.size();
    }
}
class MyshiftLogViewHolder extends RecyclerView.ViewHolder {
    TextView shiftId, shiftTime, shiftDate;

    public MyshiftLogViewHolder(@NonNull View itemView) {
        super(itemView);

        shiftId = itemView.findViewById(R.id.shiftidlog);
        shiftTime = itemView.findViewById(R.id.shiftimelog);
        shiftDate = itemView.findViewById(R.id.shiftDatelog);
    }
}