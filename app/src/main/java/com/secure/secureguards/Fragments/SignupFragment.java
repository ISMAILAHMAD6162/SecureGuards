package com.secure.secureguards.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.secure.secureguards.R;
import com.secure.secureguards.Screens.AccountActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupFragment extends Fragment {

    private EditText et_register_email, etRegisterPassword,
            etRegisterConfirmPassword,et_first_name,et_last_name,et_licence_number,et_dob,et_expire_date,et_user_number;

    TextView tv_login;
    Button btnRegister;
    private Dialog loadingDialog;
    private FirebaseAuth firebaseAuth;
    DatabaseReference myRef;

    DatePickerDialog datePicker,datePicker1;
    final Calendar myCalendar= Calendar.getInstance();
    final Calendar myCalendar1= Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        /////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        et_dob=view.findViewById(R.id.et_dob);
        et_user_number=view.findViewById(R.id.et_user_number);
        et_last_name=view.findViewById(R.id.et_last_name);
        et_licence_number=view.findViewById(R.id.et_licence_number);
        et_expire_date=view.findViewById(R.id.et_expire_date);
        tv_login=view.findViewById(R.id.tv_login);
        et_register_email=view.findViewById(R.id.et_register_email);
        etRegisterPassword = view.findViewById(R.id.et_register_password);
        etRegisterConfirmPassword = view.findViewById(R.id.et_register_confirm_password);
        et_first_name = view.findViewById(R.id.et_first_name);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AccountActivity)getActivity()).showLoginScreen();
            }
        });

        btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                String email = et_register_email.getText().toString();
                String confirm_password = etRegisterConfirmPassword.getText().toString();

               if (validate()) requestRegister(email,confirm_password);
            }
        });
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd/yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                et_dob.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker =  new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePicker.show();
            }
        });

        DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH,month);
                myCalendar1.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd/yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                et_expire_date.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        et_expire_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker1 =  new DatePickerDialog(getContext(),date1,myCalendar1.get(Calendar.YEAR),myCalendar1.get(Calendar.MONTH),myCalendar1.get(Calendar.DAY_OF_MONTH));
                datePicker1.getDatePicker().setMinDate(System.currentTimeMillis() + 1000);
                datePicker1.show();
            }
        });




        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private boolean validate() {
        if (et_register_email.getText().toString().isEmpty()) et_register_email.setError("Required!");
        else if (et_first_name.getText().toString().isEmpty()) et_first_name.setError("Required!");
        else if (et_last_name.getText().toString().isEmpty()) et_last_name.setError("Required!");
        else if (et_dob.getText().toString().isEmpty()) et_dob.setError("Required!");
        else if (et_user_number.getText().toString().isEmpty()) et_user_number.setError("Required!");
        else if (!et_register_email.getText().toString().contains("@")||!et_register_email.getText().toString().contains(".")) et_register_email.setError("Enter valid email!");
        else if (etRegisterPassword.getText().toString().isEmpty()) etRegisterPassword.setError("Required!");
        else if (etRegisterPassword.getText().toString().length()<6) etRegisterPassword.setError("Password must be at least 6 characters!");
        else if (etRegisterConfirmPassword.getText().toString().isEmpty()) etRegisterConfirmPassword.setError("Required!");
        else if (!etRegisterPassword.getText().toString().equals(etRegisterConfirmPassword.getText().toString())) etRegisterConfirmPassword.setError("Password not matched!");
        else return true;
        return false;
    }

    private void requestRegister(String email, String password) {

        loadingDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getCreateUserWithEmailOnClickListener(email));
    }
    private OnCompleteListener<AuthResult> getCreateUserWithEmailOnClickListener(String email) {
        return task -> {
            if (task.isSuccessful()) {
                addRecord();
            } else {
                loadingDialog.dismiss();
                Log.d("error",task.toString());
                Toast.makeText(getContext(),"Registration failed!"+task.toString(),Toast.LENGTH_LONG).show();

            }

        };
    }


    public void addRecord(){


          String id = firebaseAuth.getCurrentUser().getUid();

            myRef=  FirebaseDatabase.getInstance().getReference("UserRecord").child(id);
            myRef.child("FirstName").setValue(et_first_name.getText().toString());
            myRef.child("LastName").setValue(et_last_name.getText().toString());
            myRef.child("DOB").setValue(et_dob.getText().toString());
            myRef.child("UserId").setValue(id);
            myRef.child("Mail").setValue(et_register_email.getText().toString());
            myRef.child("PhoneNumber").setValue(et_user_number.getText().toString());
            myRef.child("LicenceNumber").setValue(et_licence_number.getText().toString());
            myRef.child("LicenceExpireDate").setValue(et_expire_date.getText().toString());

        myRef.child("ProfilePicture").setValue("empty");
        myRef.child("BackSideBadge").setValue("empty");
        myRef.child("FrontSideBadge").setValue("empty");
        myRef.child("Address").setValue("empty");
        myRef.child("City").setValue("empty");

            loadingDialog.dismiss();
            Toast.makeText(getContext(),"Registration successful",Toast.LENGTH_LONG).show();
            ((AccountActivity)getActivity()).showLoginScreen();

    }

}