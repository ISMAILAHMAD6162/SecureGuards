package com.secure.secureguards.ActiveShift;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.secure.secureguards.R;

public class Active_Shift_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_shift);

        Toast.makeText(getApplicationContext(),"Active Shift Activity",Toast.LENGTH_LONG).show();
    }
}