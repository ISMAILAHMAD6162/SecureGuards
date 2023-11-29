package com.secure.secureguards.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.secure.secureguards.Dash_Board_Activity;
import com.secure.secureguards.MainActivity;
import com.secure.secureguards.Model.UserRecord;
import com.secure.secureguards.R;
import com.secure.secureguards.Screens.AccountActivity;
import com.secure.secureguards.Screens.ForgotPasswordActivity;
import com.secure.secureguards.Screens.SplashActivity;
import com.secure.secureguards.Utils.Constant;

public class LoginFragment extends Fragment {
    private EditText et_licence_number, etLoginPassword;
    TextView tv_new_register,tv_forgot_password;

    private Dialog loadingDialog;
    DatabaseReference myRef;
    String userMail;
    private FirebaseAuth firebaseAuth;
    UserRecord userRecord;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        tv_forgot_password=view.findViewById(R.id.tv_forgot_password);
        /////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        et_licence_number =view.findViewById(R.id.et_licence_number);
        etLoginPassword = view.findViewById(R.id.et_login_password);
        tv_new_register=view.findViewById(R.id.tv_new_register);


        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ForgotPasswordActivity.class));
            }
        });
        Button btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                String licenceNumber = et_licence_number.getText().toString();
                String password = etLoginPassword.getText().toString();
                // call the validate function and then request
                if (validate(licenceNumber, password))getData();
            }
        });


        tv_new_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AccountActivity)getActivity()).showSignUpScreen();
            }
        });
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private boolean validate(String licenceNumber, String password) {
        if (licenceNumber.isEmpty()) et_licence_number.setError("Enter email!");
        else if (password.isEmpty()) etLoginPassword.setError("Enter password!");
        else if (password.length()<6) etLoginPassword.setError("Password must be at least 6 characters!");
        else return true;
        return false;
    }
      public void getData(){
        loadingDialog.show();
          FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
          CollectionReference useRef = firebaseFirestore.collection("UserRecord");
          String licenseNumberToRetrieve = et_licence_number.getText().toString();
          DocumentReference documentReference = useRef.document(licenseNumberToRetrieve);

          documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                  if (documentSnapshot.exists()) {
                      // Document exists, convert it to a UserRecord object
                       userRecord = documentSnapshot.toObject(UserRecord.class);
                      if (userRecord != null) {
                        requestLogin(userRecord.getMail(),etLoginPassword.getText().toString());
                       } else {
                          loadingDialog.dismiss();
                          Toast.makeText(getContext(), "UserRecord is unsuccessful " , Toast.LENGTH_SHORT).show();

                          // Handle the case where the conversion to UserRecord is unsuccessful
                      }
                  } else {
                      // Handle the case where the document does not exist
                      loadingDialog.dismiss();
                      Toast.makeText(getContext(), "wrong licence" , Toast.LENGTH_SHORT).show();

                  }
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  // Handle failures
                  loadingDialog.dismiss();
                  Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
              }
          });

      }

    public void requestLogin(String email,String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    loadingDialog.dismiss();
                    Toast.makeText(getContext(), "wrong licence or password" + task.getException(), Toast.LENGTH_LONG).show();
                } else if (task.isSuccessful()) {
                    Constant.setLoginStatus(getContext(),true);
                    Constant.setUserEmail(getContext(),userMail);
                    //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                   // Constant.setUserId(getContext(),firebaseUser.getUid());
                    Constant.setLicenceNumber(getContext(),userRecord.getLicenceNumber());
                    startActivity(new Intent(getContext(), Dash_Board_Activity.class));
                    getActivity().finish();
                    loadingDialog.show();
                }
            }
        });
    }
}