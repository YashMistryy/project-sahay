package com.YashMistry.sahay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.YashMistry.sahay.Models.CartItemModel;
import com.YashMistry.sahay.Models.ProductModel;
import com.YashMistry.sahay.Prevalent.CommonIntent;
import com.YashMistry.sahay.Prevalent.Prevalent;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {
    RecyclerView rvCartList;
    Button btnCheckout ;
    ImageView btnBack ;
    TextView tvTotalPrice ;
    DatabaseReference databaseReference;
    CartListAdapter adapter;
    public int totalPrice = 0;
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCartList = findViewById(R.id.rvCartlist);
        btnBack = findViewById(R.id.imgBackBtn);
        btnCheckout = findViewById(R.id.btnCheckout);
       tvTotalPrice = findViewById(R.id.tvTotalPrice);
        databaseReference=  FirebaseDatabase.getInstance("https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference().child("cart").child("users view").child(Prevalent.currentOnlineUser.getPhone()).child("cartProducts");
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this , ConfirmOrderActivity.class);
                intent.putExtra("totalPrice" , String.valueOf(totalPrice));
                Toast.makeText(CartActivity.this, "total"+totalPrice, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        tvTotalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "total"+totalPrice , Toast.LENGTH_SHORT).show();

            }
        });
        FirebaseRecyclerOptions<CartItemModel> options =
                new FirebaseRecyclerOptions.Builder<CartItemModel>()
                        .setQuery(databaseReference,CartItemModel.class).build();
        adapter = new CartListAdapter(options , CartActivity.this);
        rvCartList.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        rvCartList.setAdapter(adapter);




    }

    class CartListAdapter extends FirebaseRecyclerAdapter<CartItemModel, CartListAdapter.mYviewHodlder> {
        public Context context;
        DatabaseReference databaseReference;


        public CartListAdapter(@NonNull FirebaseRecyclerOptions<CartItemModel> options, Context context) {
            super(options);
            this.context = context;
            databaseReference=  FirebaseDatabase.getInstance("https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference().child("cart").child("users view").child(Prevalent.currentOnlineUser.getPhone()).child("cartProducts");

        }

        class mYviewHodlder extends RecyclerView.ViewHolder{
            ImageView image , removeBtn;
            TextView name , price , quantity;

            public mYviewHodlder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.CartProdImg);
                name = itemView.findViewById(R.id.CartProdName);
                price = itemView.findViewById(R.id.CartProdPrice);
                quantity = itemView.findViewById(R.id.CartProdQuantity);
                removeBtn = itemView.findViewById(R.id.deleteCartBtn);
            }
        }

        @NonNull
        @Override
        public CartListAdapter.mYviewHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart_list_layout,parent,false);
            return new CartListAdapter.mYviewHodlder(view);
        }
        @Override
        protected void onBindViewHolder(@NonNull CartListAdapter.mYviewHodlder holder, int position, @NonNull CartItemModel model) {
            holder.name.setText(model.getName());
            holder.price.setText(model.getPrice());
            holder.quantity.setText(model.getQuantity());
            Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);

            totalPrice = totalPrice + Integer.parseInt(model.getPrice())*(Integer.parseInt(model.getQuantity()));
            tvTotalPrice.setText("total  = "+totalPrice);
            holder.removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "remove clicked", Toast.LENGTH_SHORT).show();
//                lets make choice whether we want to remove project or simply edit the quantity

                    CharSequence options[] = new CharSequence[]{
                            "Edit",
                            "Remove"
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("What do you want ?");

                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0){
//                            edit option - edit quantity by going to productDetail activity
                                Intent intent  = new Intent(context , ProductDetailActivity.class);
                                intent.putExtra("pid" , model.getPid());
                                intent.putExtra("category" , model.getCategory());
                                context.startActivity(intent);
                            }
                            if(i == 1){
//                            remove option
                                databaseReference.child(model.getPid()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show();
                                                new CommonIntent(context , CartActivity.class);
                                            }
                                        });
                            }
                        }
                    });
                    builder.show();
                }

            });



        }

    }

    @Override
    public void onBackPressed() {
        new CommonIntent(CartActivity.this , MainActivity.class);
           }
}