package com.secure.secureguards;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.secure.secureguards.Model.UserRecord;
import com.secure.secureguards.Screens.AccountActivity;
import com.secure.secureguards.Utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Profile_Fragment extends Fragment {

    private EditText et_register_email, et_first_name,et_last_name,et_licence_number,et_dob,et_expire_date,et_user_number;

    ImageView frontImg,backImg,profilePic;
    private Uri frontImgUri =null;
    private Uri backImgUri =null;
    private Uri profilePicUri =null;
    EditText et_address,et_city;
    StorageReference mRef;
    private Dialog loadingDialog;

    UserRecord userRecord;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference useRef = firebaseFirestore.collection("UserRecord");
         Button btn_save;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_profile_, container, false);
        profilePic=view.findViewById(R.id.profilePic);
        frontImg=view.findViewById(R.id.frontImg);
        backImg=view.findViewById(R.id.backImg);
        et_address=view.findViewById(R.id.et_address);
        et_city=view.findViewById(R.id.et_city);
        mRef= FirebaseStorage.getInstance().getReference("images");
        btn_save=view.findViewById(R.id.btn_save);

        et_dob=view.findViewById(R.id.et_dob);
        et_user_number=view.findViewById(R.id.et_user_number);
        et_last_name=view.findViewById(R.id.et_last_name);
        et_licence_number=view.findViewById(R.id.et_licence_number);
        et_expire_date=view.findViewById(R.id.et_expire_date);
        et_register_email=view.findViewById(R.id.et_register_email);
        et_first_name = view.findViewById(R.id.et_first_name);


        /////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
       // getProfileRecord();
        getData();
        checkPermission();

        frontImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK,android.provider. MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK,android.provider. MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK,android.provider. MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              saveRecord();
            }
        });
        return view;
    }
    public void getData(){
        final String licenceNumber=Constant.getLicenceNumber(getContext());
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference useRef = firebaseFirestore.collection("UserRecord");
        DocumentReference documentReference = useRef.document(licenceNumber);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    //convert the documentSnapshot to a User object
                    userRecord = documentSnapshot.toObject(UserRecord.class);
                    if(!userRecord.getAddress().equals("empty")){
                        et_address.setText( userRecord.getAddress());
                    }
                    if(!userRecord.getCity().equals("empty")){
                        et_city.setText( userRecord.getCity());
                    }


                    et_register_email.setText(userRecord.getMail());
                    et_first_name.setText(userRecord.getFirstName());
                    et_last_name.setText(userRecord.getLastName());
                    et_dob.setText(userRecord.getDob());
                    et_licence_number.setText(userRecord.getLicenceNumber());
                    et_user_number.setText(userRecord.getPhoneNumber());
                    et_expire_date.setText(userRecord.getLicenceExpireDate());

                    if( !userRecord.getProfilePicture().equals("empty")){
                        Picasso.with(getContext())
                                .load(userRecord.getProfilePicture())
                                .placeholder(R.drawable.progress_animation)
                                .fit()
                                .centerCrop()
                                .into(profilePic);
                    }
                    if( !userRecord.getBackSideBadge().equals("empty")){
                        Picasso.with(getContext())
                                .load(userRecord.getBackSideBadge())
                                .placeholder(R.drawable.progress_animation)
                                .fit()
                                .centerCrop()
                                .into(backImg);
                    }
                    if( !userRecord.getFrontSideBadge().equals("empty")){
                        Picasso.with(getContext())
                                .load(userRecord.getFrontSideBadge())
                                .placeholder(R.drawable.progress_animation)
                                .fit()
                                .centerCrop()
                                .into(frontImg);
                    }
                    loadingDialog.dismiss();
                }
                else {
                    loadingDialog.dismiss();
                    Toast.makeText(getContext(), "wrong licence ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public void saveRecord(){
        loadingDialog.show();
        if(et_address.getText().toString().isEmpty()){
            et_address.setError("required");
        }else if(et_city.getText().toString().isEmpty()){
            et_city.setError("required");
        }else {
//            DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference("UserRecord").child(Constant.getUserId(getContext()));
//            myRef.child("Address").setValue(et_address.getText().toString());
//            myRef.child("City").setValue(et_city.getText().toString());
            Map<String, Object> updates = new HashMap<>();
            updates.put("address", et_address.getText().toString());
            updates.put("city", et_city.getText().toString());

            useRef.document(userRecord.getLicenceNumber())
                    .update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            loadingDialog.dismiss();
                            Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingDialog.dismiss();
                            Toast.makeText(getContext(), "Error updating document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            if(backImgUri!=null){
                uploadBackImg();
            }if(frontImgUri!=null){
                uploadFrontImg();
            }
            if(profilePicUri!=null){
                uploadProfileImg();
            }


        }
    }
    //public void saveRecord(View view){
//        loadingDialog.show();
//        if(et_address.getText().toString().isEmpty()){
//            et_address.setError("required");
//        }else if(et_city.getText().toString().isEmpty()){
//            et_city.setError("required");
//        }else {
//            DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference("UserRecord").child(Constant.getUserId(getContext()));
//            myRef.child("Address").setValue(et_address.getText().toString());
//            myRef.child("City").setValue(et_city.getText().toString());
//            useRef.document(et_licence_number.getText().toString()).set(
//                    userRecord)
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            loadingDialog.dismiss();
//                            Toast.makeText(getContext(), "Error ", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            loadingDialog.dismiss();
//                            Toast.makeText(getContext(),"Registration successful",Toast.LENGTH_LONG).show();
//                            ((AccountActivity)getActivity()).showLoginScreen();                    }
//                    });
//            if(backImgUri!=null){
//                uploadBackImg();
//            }if(frontImgUri!=null){
//                uploadFrontImg();
//            }
//            if(profilePicUri!=null){
//                uploadProfileImg();
//            }
//
//
//        }
//    }



    public void uploadFrontImg(){
        loadingDialog.show();
        StorageReference storageReference = mRef.child(System.currentTimeMillis() + "." + getFileEx(frontImgUri));
        storageReference.putFile(frontImgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("frontSideBadge", downloadUrl.toString());


                        useRef.document(userRecord.getLicenceNumber())
                                .update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        loadingDialog.dismiss();
                                        Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loadingDialog.dismiss();
                                        Toast.makeText(getContext(), "Error updating document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    } public void uploadBackImg(){
        loadingDialog.show();
        StorageReference storageReference = mRef.child(System.currentTimeMillis() + "." + getFileEx(backImgUri));
        storageReference.putFile(backImgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();

                        Map<String, Object> updates = new HashMap<>();
                        updates.put("backSideBadge", downloadUrl.toString());


                        useRef.document(userRecord.getLicenceNumber())
                                .update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        loadingDialog.dismiss();
                                        Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loadingDialog.dismiss();
                                        Toast.makeText(getContext(), "Error updating document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });




                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    } public void uploadProfileImg(){
        loadingDialog.show();
        StorageReference storageReference = mRef.child(System.currentTimeMillis() + "." + getFileEx(profilePicUri));
        storageReference.putFile(profilePicUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("profilePicture", downloadUrl.toString());


                        useRef.document(userRecord.getLicenceNumber())
                                .update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        loadingDialog.dismiss();
                                        Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        loadingDialog.dismiss();
                                        Toast.makeText(getContext(), "Error updating document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    }



    public void checkPermission(){
        Dexter.withActivity(getActivity())
                .withPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
//
    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            frontImgUri  = data.getData();
            frontImg.setImageURI(frontImgUri);
        } if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            backImgUri  = data.getData();
            backImg.setImageURI(backImgUri);
        }if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            profilePicUri  = data.getData();
            profilePic.setImageURI(profilePicUri);
        }

    }

    // get the extension of file
    private String getFileEx(Uri uri){
        ContentResolver cr=getContext().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

}