package com.YashMistry.sahay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.YashMistry.sahay.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView ivProductImg;
    TextView tvProdName , tvProdPrice , tvProdDesc ,tvQuantity;
    Button btnAddToCart , btnIcr , btnDcr ;
    DatabaseReference databaseReference;
    DatabaseReference cartRef;
    String image , category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pid = getIntent().getStringExtra("pid");
         category = getIntent().getStringExtra("category");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("products").child(category).child(pid);
        cartRef = FirebaseDatabase.getInstance().getReference().child("cart");

        setContentView(R.layout.activity_product_detail);
        ivProductImg = findViewById(R.id.iv_productdetails);
        tvProdPrice = findViewById(R.id.tv_productdetail_price);
        tvProdDesc = findViewById(R.id.tv_productdetail_desc);
        tvProdName = findViewById(R.id.tv_productdetail_name);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnIcr = findViewById(R.id.btnIcr);
        btnDcr = findViewById(R.id.btnDcr);
        tvQuantity = findViewById(R.id.tvQuantity);

//        tvProdDesc.setText(pid);

        displayProductsDetail(pid);



        btnIcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(tvQuantity.getText().toString());
                n++;
                tvQuantity.setText(String.valueOf(n));
            }
        });
        btnDcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(tvQuantity.getText().toString());
                if(n>0){
                    n--;
                tvQuantity.setText(String.valueOf(n));
            }}
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(pid);

            }
        });

    }

    private void addToCart(String pid) {
        String currentTime , currentDate;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd , mm ,yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");
        currentTime = timeFormat.format(calendar.getTime());

        String date = currentDate+currentTime;
        HashMap<String , Object> prodMap = new HashMap<>();
        prodMap.put("pid" , pid);
        prodMap.put("name" , tvProdName.getText().toString());
        prodMap.put("price" , tvProdPrice.getText().toString());
        prodMap.put("desc" , tvProdDesc.getText().toString());
        prodMap.put("quantity" , tvQuantity.getText().toString());
        prodMap.put("time" , date);
        prodMap.put("image" , image);
        prodMap.put("category" , category);

        cartRef.child("users view").child(Prevalent.currentOnlineUser.getPhone()).child("cartProducts").child(pid)
                .updateChildren(prodMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ProductDetailActivity.this, "item added to cart :)", Toast.LENGTH_SHORT).show();
//                    cartRef.child("users view").child(Prevalent.currentOnlineUser.getPhone()).child("cartProducts")
//                            .updateChildren(prodMap)
                }
                else {
                    Toast.makeText(ProductDetailActivity.this, "sorry some problem occured!!!!1", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }

    private void displayProductsDetail(String pid) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    String desc = snapshot.child("desc").getValue().toString();
                     image = snapshot.child("image").getValue().toString();

                    Picasso.get().load(image).into(ivProductImg);
                    tvProdDesc.setText(desc);
                    tvProdName.setText(name);
                    tvProdPrice.setText(price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}