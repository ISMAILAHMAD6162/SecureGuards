package com.secure.secureguards.Fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class SignupFragment extends Fragment {

    private EditText et_register_email, etRegisterPassword,
            etRegisterConfirmPassword,et_first_name,et_last_name,et_licence_number,et_dob,et_expire_date,et_user_number;

    TextView tv_login;
    Button btnRegister;
    private Dialog loadingDialog;
    private FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
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
                .addOnCompleteListener(getCreateUserWithEmailOnClickListener());
    }
    private OnCompleteListener<AuthResult> getCreateUserWithEmailOnClickListener() {
        return task -> {
            if (task.isSuccessful()) {
                addRecord();
            } else {
                loadingDialog.dismiss();
                Toast.makeText(getContext(),"Registration failed!",Toast.LENGTH_LONG).show();

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


            loadingDialog.dismiss();
            Toast.makeText(getContext(),"Registration successful",Toast.LENGTH_LONG).show();
            ((AccountActivity)getActivity()).showLoginScreen();

    }

}