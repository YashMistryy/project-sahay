package com.YashMistry.sahay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YashMistry.sahay.Models.ProductModel;
import com.YashMistry.sahay.ProductDetailActivity;
import com.YashMistry.sahay.ProductListActivity;
import com.YashMistry.sahay.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProductListAdapter extends FirebaseRecyclerAdapter<ProductModel,ProductListAdapter.mYviewHodlder> {
   public Context context;

    public ProductListAdapter(@NonNull FirebaseRecyclerOptions<ProductModel> options, Context context) {
        super(options);
        this.context = context;
    }

    class mYviewHodlder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name , price , desc;

        public mYviewHodlder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.custimg2);
            name = itemView.findViewById(R.id.text3);
            price = itemView.findViewById(R.id.text4);


        }
    }

    @NonNull
    @Override
    public  mYviewHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_list_layout,parent,false);
        return new mYviewHodlder(view);
    }
    @Override
    protected void onBindViewHolder(@NonNull mYviewHodlder holder, int position, @NonNull ProductModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(context,  ProductDetailActivity.class);
                intent.putExtra("pid" , model.getPid());
                intent.putExtra("category" , model.getCategory());
                intent.putExtra("unit" , model.getUnit());
                context.startActivity(intent);
            }
        });
    }
}
