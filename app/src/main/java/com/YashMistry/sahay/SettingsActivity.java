package com.YashMistry.sahay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.YashMistry.sahay.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    EditText etFullName , etPhoneNumber , etAddres;
    ImageView userProfile , ivBackbtn;
//    String checker = "";
//    private static final int GalleryPick= 1;
    DatabaseReference databaseReference;
//    String myUri ;
//    Uri imageUri ;966
    StorageReference storageReference ;
    TextView tvupdate , tvChangePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        etFullName = findViewById(R.id.etfullname);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddres = findViewById(R.id.etAddres);
        ivBackbtn = findViewById(R.id.ivBackbtn);
        tvupdate = findViewById(R.id.tvUpdate);
        tvChangePhoto = findViewById(R.id.tvChangePhoto);
        userProfile = findViewById(R.id.userprofile);
        databaseReference = FirebaseDatabase.getInstance("\"https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference().child("users").child(Prevalent.currentOnlineUser.getPhone());
        storageReference = FirebaseStorage.getInstance("gs://sahay-3b63e.appspot.com/").getReference().child("products");
        tvChangePhoto.setText(Prevalent.currentOnlineUser.getName());
        displayUserInfo(etPhoneNumber,etAddres,etFullName);

        ivBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, Prevalent.currentOnlineUser.getName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SettingsActivity.this, Prevalent.currentOnlineUser.getPhone(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SettingsActivity.this, Prevalent.currentOnlineUser.getAddres(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SettingsActivity.this, Prevalent.currentOnlineUser.getPass(), Toast.LENGTH_SHORT).show();
            }
        });
//        tvChangePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checker = "clicked";
//                OpenGallery();
//
//
//
//            }
//        });
        tvupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(checker.equals("clicked")){
//                    updateAllData();
//                }else {
                    updateInfoOnly();
//                }
            }
        });

    }
//    private void OpenGallery() {
//        Intent galleryintent = new Intent();
//        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryintent.setType("image/*");
//        startActivityForResult(galleryintent,GalleryPick);
//    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null){
//            imageUri = data.getData();
//            userProfile.setImageURI(imageUri);
//
//        }else{
//            Toast.makeText(this, "error fetching the image", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SettingsActivity.this , SettingsActivity.class));
//        }
//
//    }
    private void displayUserInfo(EditText etPhoneNumber, EditText etAddres, EditText etFullName) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
//                    Toast.makeText(SettingsActivity.this, "Yes user exists, datasnapshot exists!", Toast.LENGTH_SHORT).show();

//                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();

//                        Picasso.get().load(image).into(userProfile);
                        etFullName.setText(name);
                        etPhoneNumber.setText(phone);
                        etAddres.setText(address);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
//    private void updateAllData() {
//
//        if(TextUtils.isEmpty(etFullName.getText().toString().trim())){
//            etFullName.setError("enter name!");
//        }else if(TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())){
//            etPhoneNumber.setError("enter phone number!");
//        }else if (TextUtils.isEmpty(etAddres.getText().toString().trim())){
//            etAddres.setError("enter address!");
//        }else if(imageUri == null) {
//            Toast.makeText(this, "select an image first!", Toast.LENGTH_SHORT).show();
//        }else{
//            uploadImage();
//        }
//
//   }
    private void updateInfoOnly() {
        if(TextUtils.isEmpty(etFullName.getText().toString().trim())){
            etFullName.setError("enter name!");
        }else if(TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())){
            etPhoneNumber.setError("enter phone number!");
        }else if (TextUtils.isEmpty(etAddres.getText().toString().trim())){
            etAddres.setError("enter address!");
        }else{

            HashMap<String , Object> userMap = new HashMap<>();
            userMap.put("name" , etFullName.getText().toString());
            userMap.put("phone" , etPhoneNumber.getText().toString());
            userMap.put("address" , etAddres.getText().toString());
            databaseReference.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SettingsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        Prevalent.currentOnlineUser.setName(etFullName.getText().toString());
                        Prevalent.currentOnlineUser.setPhone(etPhoneNumber.getText().toString());
                        Prevalent.currentOnlineUser.setAddres(etAddres.getText().toString());

                    }
                    else
                    {
                        Toast.makeText(SettingsActivity.this, "Profile didn't upadted :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            startActivity(new Intent(SettingsActivity.this , MainActivity.class));
        }

    }
//    private void uploadImage() {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Updating profile");
//        progressDialog.setMessage("pls wait while we update your profile");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        if(imageUri!=null){
//            StorageReference filepath = storageReference.child(Prevalent.currentOnlineUser.getPhone());
//            final UploadTask uploadTask = filepath.putFile(imageUri);
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public  Object  then(@NonNull Task task) throws Exception {
//                    if (!task.isSuccessful()){
//                        throw task.getException();
//                    }
//
//                    return storageReference.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                Uri downoad = task.getResult();
//                myUri = downoad.toString();
//                DatabaseReference dataref = FirebaseDatabase.getInstance("https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference().child("users");
//
//                    HashMap<String , Object> userMap = new HashMap<>();
//                    userMap.put("name" , etFullName.getText().toString());
//                    userMap.put("phone" , etPhoneNumber.getText().toString());
//                    userMap.put("address" , etAddres.getText().toString());
//                    userMap.put("image" , myUri);
//                    dataref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(userMap);
//                    Toast.makeText(SettingsActivity.this, "image updatded", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SettingsActivity.this , MainActivity.class));
//                }
//            });
//
//        }else{
//            Toast.makeText(this, "image not selected!", Toast.LENGTH_SHORT).show();
//        }
//    }
}
