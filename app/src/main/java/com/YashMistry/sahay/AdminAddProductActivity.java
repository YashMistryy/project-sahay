package com.YashMistry.sahay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.YashMistry.sahay.Prevalent.CommonIntent;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddProductActivity extends AppCompatActivity {
    String category,price,desc,name,unit,currentDate ,currentTime;
    String productRandomKey , downloadUri;
    TextView tvCategory;
    ImageView ivInputImage;
    Button btnAddProd;
    EditText inputName , inputPrice , inputDesc , inputUnit;
    private static final int GalleryPick= 1;
    private Uri imageUri;
     StorageReference ProductImgRef;
     DatabaseReference ProductRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);
        tvCategory = findViewById(R.id.tvCategoryDisplay);
        ivInputImage = findViewById(R.id.image_input);
        inputName = findViewById(R.id.etInputName);
        inputPrice = findViewById(R.id.etInputPrice);
        inputUnit = findViewById(R.id.etInputUnit);
        inputDesc = findViewById(R.id.etInputDesc);
        btnAddProd = findViewById(R.id.btnAddProd);
        ProductImgRef = FirebaseStorage.getInstance("gs://sahay-3b63e.appspot.com/").getReference().child("products");


        category=getIntent().getExtras().get("category").toString();
        tvCategory.setText("add to "+category);

        ProductRef = FirebaseDatabase.getInstance("https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference().child("products").child(category);


        ivInputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });


    }
    private void OpenGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            ivInputImage.setImageURI(imageUri);

        }
        btnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProduct();
            }
        });
    }

    private void ValidateProduct() {
        name = inputName.getText().toString();
        price = inputPrice.getText().toString();
        desc = inputDesc.getText().toString();
        unit= inputUnit.getText().toString();

        if (imageUri == null){
            Toast.makeText(this, "Select an image first!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(name.trim())){
            inputName.setError("enter name !");
        }else if(TextUtils.isEmpty(price.trim())){
            inputName.setError("enter price !");
        }else if(TextUtils.isEmpty(desc.trim())){
            inputName.setError("enter description !");
        }else if(TextUtils.isEmpty(unit.trim())){
            inputName.setError("enter unit !");
        }else{
            StoreProductInfo();
        }

    }

    private void StoreProductInfo() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format1 = new SimpleDateFormat("MMM dd, yyyy");
        currentDate  = format1.format(calendar.getTime());

        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss a");
        currentTime  = format2.format(calendar.getTime());
        productRandomKey = currentDate+currentTime;

        StorageReference filepath = ProductImgRef.child(imageUri.getLastPathSegment()+productRandomKey+".jpeg");
        final UploadTask uploadTask = filepath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg = e.toString();
                Toast.makeText(AdminAddProductActivity.this, "error: "+msg, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddProductActivity.this, "image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadUri = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadUri = task.getResult().toString();
                            Toast.makeText(AdminAddProductActivity.this, "got product img url successfully", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDb();
                        }
                    }


                });
            }
        });
    }



    private void SaveProductInfoToDb() {
        HashMap<String , Object> productmap =  new HashMap<>();
        productmap.put("pid" , productRandomKey);
        productmap.put("date" , currentDate);
        productmap.put("time" , currentTime);
        productmap.put("name" , name);
        productmap.put("desc" , desc);
        productmap.put("price" , price);
        productmap.put("unit" , unit);
        productmap.put("image" , downloadUri);
        productmap.put("category" , category);

        ProductRef.child(productRandomKey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AdminAddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    new CommonIntent(AdminAddProductActivity.this , AdminCategoryActivity.class);
                }
                else{
                    Toast.makeText(AdminAddProductActivity.this, "error"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}