package com.example.todocodepath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>  {

    public  interface  OnClickListener{
        void onItemClickListener(int position);
    }

    public  interface  OnLongClickListener{
        void onItemLongClickListener(int position);
    }

    List<String>items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;
    public ItemAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items=items;
        this.longClickListener=longClickListener;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customelayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       String item= items.get(position);
       holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    //Container to provide easy access to views that represent each row in the list --> (View Holder)

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem= itemView.findViewById(R.id.itemInList);
            layout= itemView.findViewById(R.id.relativeLayout);
        }

        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClickListener(getAdapterPosition());
                }
            });

            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemLongClickListener(getAdapterPosition());
                    return false;
                }
            });
        }
    }
}
