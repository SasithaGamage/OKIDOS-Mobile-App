package com.store.okidosmobileapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.store.okidosmobileapp.Interface.ItemClickListner;
import com.store.okidosmobileapp.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtDate;
    public TextView txtTime;
    public TextView txtPrice;
    public TextView txtAddress;
    public TextView txtPhone;

    private ItemClickListner itemClickListner;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);
        txtDate=itemView.findViewById(R.id.txtODate);
        txtTime=itemView.findViewById(R.id.txtOTime);
        txtPrice=itemView.findViewById(R.id.txtOPrice);
        txtAddress=itemView.findViewById(R.id.txtOAddress);
        txtPhone=itemView.findViewById(R.id.txtOPhone);


    }


    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
