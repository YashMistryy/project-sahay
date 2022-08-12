package com.YashMistry.sahay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YashMistry.sahay.Models.CategoryModel;
import com.YashMistry.sahay.R;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {
    Context context;
    //    String[] categoryNamesArray;
//    int[] categoryImgArray ;
    ArrayList <CategoryModel> arrayList;
//    public CategoryAdapter(Context context, String[] categoryNamesArray, int[] categoryImgArray) {
//        this.context = context;
//        this.categoryImgArray = categoryImgArray;
//        this.categoryNamesArray = categoryNamesArray;
//    }

    public CategoryAdapter(Context context, ArrayList<CategoryModel> carrayList) {
        this.context = context;
        this.arrayList = carrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category_layout_main,parent,false);

        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_category_img);
            textView = itemView.findViewById(R.id.custom_category_name);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(arrayList.get(position).getImg());
        holder.textView.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
