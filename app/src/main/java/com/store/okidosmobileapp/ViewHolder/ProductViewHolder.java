package com.store.okidosmobileapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.store.okidosmobileapp.Interface.ItemClickListner;

import com.store.okidosmobileapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.display_product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.display_product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.display_product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.display_product_price);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {

        listner.onClick(view, getAdapterPosition(), false);

    }
}
