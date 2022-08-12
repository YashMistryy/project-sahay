package com.YashMistry.sahay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    ImageView electrical , masonry , automobile , plumbing , gardening , carpentry ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        electrical = findViewById(R.id.electrical_category);
        masonry = findViewById(R.id.masonry_category);
        automobile = findViewById(R.id.automobile_category);
        plumbing = findViewById(R.id.plumbing_category);
        gardening= findViewById(R.id.gardening_category);
        carpentry = findViewById(R.id.carpentry_category);

        electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategoryActivity.this , AdminAddProductActivity.class);
                intent.putExtra("category" , "electrical");
                startActivity(intent);
            }
        });
        masonry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategoryActivity.this , AdminAddProductActivity.class);
                intent.putExtra("category" , "masonry");
                startActivity(intent);
            }
        });
        plumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategoryActivity.this , AdminAddProductActivity.class);
                intent.putExtra("category" , "plumbing");
                startActivity(intent);
            }
        });
        automobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategoryActivity.this , AdminAddProductActivity.class);
                intent.putExtra("category" , "automobile");
                startActivity(intent);
            }
        });
        gardening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategoryActivity.this , AdminAddProductActivity.class);
                intent.putExtra("category" , "gardening");
                startActivity(intent);
            }
        });
        carpentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(AdminCategoryActivity.this , AdminAddProductActivity.class);
                intent.putExtra("category" , "carpentry");
                startActivity(intent);
            }
        });

    }
}