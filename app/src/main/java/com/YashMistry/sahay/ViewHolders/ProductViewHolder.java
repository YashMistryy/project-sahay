package com.YashMistry.sahay.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YashMistry.sahay.Interfaces.ItemClickListners;
import com.YashMistry.sahay.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name , price , desc;
    public ImageView image;
    public ItemClickListners listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.custimg2);
        name = itemView.findViewById(R.id.text3);
        price = itemView.findViewById(R.id.text4);

    }
public void setItemClickListner(ItemClickListners listner){
        this.listner = listner;
}
    @Override
    public void onClick(View view) {
    listner.onClick(view , getAbsoluteAdapterPosition(),false);
    }
}
