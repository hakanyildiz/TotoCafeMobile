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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by okano on 17.01.2016.
 */
public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolderCategory> {

    Context mContext;
    CustomItemClickListener listener;
    private ArrayList<CategoryTable> listCategories = new ArrayList<CategoryTable>();
    private LayoutInflater layoutInflater;

    public AdapterCategory(Context context ,ArrayList<CategoryTable> listCategories ,  CustomItemClickListener listener){
        layoutInflater = LayoutInflater.from(context);
        this.listener = listener;
        this.listCategories = listCategories;
    }

    public void setCategoryList(ArrayList<CategoryTable> listCategories){
        this.listCategories = listCategories;
        //update the adapter to reflect the new set of movies
        notifyItemRangeChanged(0 , listCategories.size());
        Log.d("HAKKE", "size in adapterCategory : " + listCategories.size());
    }

    @Override
    public ViewHolderCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_category_menu , parent , false);
        final ViewHolderCategory viewHolder = new ViewHolderCategory(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(v,viewHolder.getPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderCategory holder, int position) {
        Log.d("HAKKE", "Positions:" + position);
        CategoryTable currentCategory = listCategories.get(position);
        Log.d("HAKKE", "Current Category : " + currentCategory.getCategoryName());
        holder.categoryName.setText(currentCategory.getCategoryName());

    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }


    public static class ViewHolderCategory extends RecyclerView.ViewHolder{
        private TextView categoryName;

        public ViewHolderCategory(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.tvCategoryName);
        }

    }


}
