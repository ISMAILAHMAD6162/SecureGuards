package com.secure.secureguards.Screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.secure.secureguards.R;
import com.secure.secureguards.Utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpdateProfileActivity extends AppCompatActivity {
         ImageView frontImg,backImg,profilePic;
    private Uri frontImgUri =null;
    private Uri backImgUri =null;
    private Uri profilePicUri =null;
         EditText et_address,et_city;
    StorageReference mRef;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        checkPermission();
        profilePic=findViewById(R.id.profilePic);
        frontImg=findViewById(R.id.frontImg);
        backImg=findViewById(R.id.backImg);
        et_address=findViewById(R.id.et_address);
        et_city=findViewById(R.id.et_city);
        mRef= FirebaseStorage.getInstance().getReference("images");

        /////loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                 getProfileRecord();
    }

    public void getProfileRecord(){
     loadingDialog.show();
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().
                child("UserRecord").child(Constant.getUserId(UpdateProfileActivity.this));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                  et_address.setText(  dataSnapshot.child("Address").getValue(String.class));
                    et_city.setText(dataSnapshot.child("City").getValue(String.class));

                   if( !dataSnapshot.child("ProfilePicture").getValue(String.class).equals("empty")){
                       Picasso.with(UpdateProfileActivity.this)
                               .load(dataSnapshot.child("ProfilePicture").getValue(String.class))
                               .placeholder(R.drawable.progress_animation)
                               .fit()
                               .centerCrop()
                               .into(profilePic);
                   }
                    if( !dataSnapshot.child("BackSideBadge").getValue(String.class).equals("empty")){
                        Picasso.with(UpdateProfileActivity.this)
                                .load(dataSnapshot.child("BackSideBadge").getValue(String.class))
                                .placeholder(R.drawable.progress_animation)
                                .fit()
                                .centerCrop()
                                .into(backImg);
                    }
                    if( !dataSnapshot.child("FrontSideBadge").getValue(String.class).equals("empty")){
                        Picasso.with(UpdateProfileActivity.this)
                                .load(dataSnapshot.child("FrontSideBadge").getValue(String.class))
                                .placeholder(R.drawable.progress_animation)
                                .fit()
                                .centerCrop()
                                .into(frontImg);
                    }

                    loadingDialog.dismiss();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void selectFrontImage(View view){
        Intent intent=new Intent(Intent.ACTION_PICK,android.provider. MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }
    public void selectBackImage(View view){
        Intent intent=new Intent(Intent.ACTION_PICK,android.provider. MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2);
    }  public void selectProfileImage(View view){
        Intent intent=new Intent(Intent.ACTION_PICK,android.provider. MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,3);
    }
    public void saveRecord(View view){
        loadingDialog.show();
              if(et_address.getText().toString().isEmpty()){
                  et_address.setError("required");
              }else if(et_city.getText().toString().isEmpty()){
                  et_city.setError("required");
              }else {
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
                        DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference("UserRecord").child(Constant.getUserId(UpdateProfileActivity.this));
                        myRef.child("FrontSideBadge").setValue(downloadUrl.toString());
                        myRef.child("Address").setValue(et_address.getText().toString());
                        myRef.child("City").setValue(et_city.getText().toString());
                        loadingDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this,"profile update",Toast.LENGTH_LONG).show();



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                        DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference("UserRecord").child(Constant.getUserId(UpdateProfileActivity.this));
                        myRef.child("BackSideBadge").setValue(downloadUrl.toString());
                        myRef.child("Address").setValue(et_address.getText().toString());
                        myRef.child("City").setValue(et_city.getText().toString());

                        loadingDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this,"profile update",Toast.LENGTH_LONG).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                        DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference("UserRecord").child(Constant.getUserId(UpdateProfileActivity.this));
                        myRef.child("ProfilePicture").setValue(downloadUrl.toString());
                        myRef.child("Address").setValue(et_address.getText().toString());
                        myRef.child("City").setValue(et_city.getText().toString());

                        loadingDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this,"profile update",Toast.LENGTH_LONG).show();



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.dismiss();
                        Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    }



    public void checkPermission(){
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        Uri uri = Uri.fromParts("package",UpdateProfileActivity.this.getPackageName(), null);
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
        ContentResolver cr=UpdateProfileActivity.this.getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}