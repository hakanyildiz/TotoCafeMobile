package com.sohos.totocafemobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.pojo.CategoryTable;
import com.sohos.totocafemobile.pojo.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by okano on 17.01.2016.
 */
public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolderProduct> {

    Context mContext;
    CustomItemClickListener listener;
    private ArrayList<Product> listProduct = new ArrayList<Product>();
    private LayoutInflater layoutInflater;

    public AdapterProduct(Context context ,ArrayList<Product> listProduct ,  CustomItemClickListener listener){
        layoutInflater = LayoutInflater.from(context);
        this.listener = listener;
        this.listProduct = listProduct;
    }

    public void setProductList(ArrayList<Product> listProduct){
        this.listProduct = listProduct;
        //update the adapter to reflect the new set of movies
        notifyItemRangeChanged(0 , listProduct.size());
        Log.d("HAKKE", "size in adapterProduct : " + listProduct.size());
    }

    @Override
    public ViewHolderProduct onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.costum_product_menu , parent , false);
        final ViewHolderProduct viewHolder = new ViewHolderProduct(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(v,viewHolder.getPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderProduct holder, int position) {
        Log.d("HAKKE", "Positions:" + position);
        Product currentProduct = listProduct.get(position);
        Log.d("HAKKE", "Current product : " + currentProduct.getProductName());
        holder.productName.setText(currentProduct.getProductName());
        holder.credit.setText("Credit: " + currentProduct.getCredit());
        holder.detail.setText(currentProduct.getDetail());
        holder.price.setText("Price:" + currentProduct.getPrice());
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }


    public static class ViewHolderProduct extends RecyclerView.ViewHolder{
        private TextView productName;
        private TextView detail;
        private TextView price;
        private TextView credit;

        public ViewHolderProduct(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.tvProductName);
            detail = (TextView) itemView.findViewById(R.id.tvDetails);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            credit = (TextView) itemView.findViewById(R.id.tvCredit);

        }
    }


}
