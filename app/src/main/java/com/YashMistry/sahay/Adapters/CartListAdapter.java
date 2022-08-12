//package com.YashMistry.sahay.Adapters;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.YashMistry.sahay.CartActivity;
//import com.YashMistry.sahay.Models.CartItemModel;
//import com.YashMistry.sahay.Models.ProductModel;
//import com.YashMistry.sahay.Prevalent.CommonIntent;
//import com.YashMistry.sahay.Prevalent.Prevalent;
//import com.YashMistry.sahay.ProductDetailActivity;
//import com.YashMistry.sahay.R;
//import com.bumptech.glide.Glide;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class CartListAdapter extends FirebaseRecyclerAdapter<CartItemModel,CartListAdapter.mYviewHodlder> {
//    public Context context;
//    DatabaseReference databaseReference;
//
//
//    public CartListAdapter(@NonNull FirebaseRecyclerOptions<CartItemModel> options, Context context) {
//        super(options);
//        this.context = context;
//        databaseReference=  FirebaseDatabase.getInstance("https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference().child("cart").child("users view").child(Prevalent.currentOnlineUser.getPhone()).child("cartProducts");
//
//    }
//
//    class mYviewHodlder extends RecyclerView.ViewHolder{
//        ImageView image , removeBtn;
//        TextView name , price , quantity , tvTotalPrice;
//
//        public mYviewHodlder(@NonNull View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.CartProdImg);
//            name = itemView.findViewById(R.id.CartProdName);
//            price = itemView.findViewById(R.id.CartProdPrice);
//            quantity = itemView.findViewById(R.id.CartProdQuantity);
//            removeBtn = itemView.findViewById(R.id.deleteCartBtn);
//            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
//        }
//    }
//
//    @NonNull
//    @Override
//    public  mYviewHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart_list_layout,parent,false);
//        return new mYviewHodlder(view);
//    }
//    @Override
//    protected void onBindViewHolder(@NonNull mYviewHodlder holder, int position, @NonNull CartItemModel model) {
//        holder.name.setText(model.getName());
//        holder.price.setText(model.getPrice());
//        holder.quantity.setText(model.getQuantity());
//        Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);
//
//        String itemTotalPrice = (model.getPrice());
//        totalPrice = totalPrice + Integer.parseInt(itemTotalPrice);
//        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "remove clicked", Toast.LENGTH_SHORT).show();
////                lets make choice whether we want to remove project or simply edit the quantity
//
//                CharSequence options[] = new CharSequence[]{
//                        "Edit",
//                        "Remove"
//                };
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("What do you want ?");
//
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (i == 0){
////                            edit option - edit quantity by going to productDetail activity
//                            Intent intent  = new Intent(context , ProductDetailActivity.class);
//                            intent.putExtra("pid" , model.getPid());
//                            intent.putExtra("category" , model.getCategory());
//                            context.startActivity(intent);
//                        }
//                        if(i == 1){
////                            remove option
//                            databaseReference.child(model.getPid()).removeValue()
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                            Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show();
//                                            new CommonIntent(context , CartActivity.class);
//                                        }
//                                    });
//                        }
//                    }
//                });
//                builder.show();
//            }
//        });
////        holder.tvTotalPrice.setText("total cost = "+String.valueOf(totalPrice));
//        holder.tvTotalPrice.setText("total cost = "+itemTotalPrice);
//
//    }
//
//}
