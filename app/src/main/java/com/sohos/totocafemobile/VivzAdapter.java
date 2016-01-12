package com.sohos.totocafemobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;


/**
 * Created by hakan on 12.01.2016.
 */
public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;

    List<Information> data = Collections.emptyList();
    public VivzAdapter(Context context , List<Information> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position){
        data.remove(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.costum_row,parent,false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        Log.d("HAKKE", "onBindViewHolder" + position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {

            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //Toast.makeText(context , "Item clicked at " + getLayoutPosition() , Toast.LENGTH_SHORT).show();
            //context.startActivity(new Intent(context, Splash.class));

            if(clickListener !=null){
                clickListener.itemClicked(v,getLayoutPosition());
            }
        }
    }

    public  interface ClickListener{
        public  void itemClicked(View view , int position);
    }
}
