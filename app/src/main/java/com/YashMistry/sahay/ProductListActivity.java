package com.YashMistry.sahay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.YashMistry.sahay.Adapters.ProductListAdapter;
import com.YashMistry.sahay.Models.ProductModel;
import com.YashMistry.sahay.ViewHolders.ProductViewHolder;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductListActivity extends AppCompatActivity {
    RecyclerView rvProduct;
    TextView tvdisplay;
    DatabaseReference ProductRef;
    ProductListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        String category = intent.getStringExtra("category");
        setContentView(R.layout.activity_product_list);
        rvProduct = findViewById(R.id.rvProduct);
        ProductRef=  FirebaseDatabase.getInstance("https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference().child("products").child(category);

        FirebaseRecyclerOptions<ProductModel> options =
                new FirebaseRecyclerOptions.Builder<ProductModel>()
                        .setQuery(ProductRef,ProductModel.class).build();

        adapter = new ProductListAdapter(options , ProductListActivity.this);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.setAdapter(adapter);



    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}