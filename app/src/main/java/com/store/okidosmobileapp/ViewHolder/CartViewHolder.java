package com.store.okidosmobileapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.store.okidosmobileapp.Interface.ItemClickListner;
import com.store.okidosmobileapp.R;

import java.text.BreakIterator;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtShoppingCartProductName;
    public TextView txtShoppingCartProductQuantity;
    public TextView txtShoppingCartProductCategory;
    public TextView txtShoppingCartProductPrice;
    public TextView txtShoppingCartProductTotPrice;

    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView)
    {
        super(itemView);

        txtShoppingCartProductName = itemView.findViewById(R.id.cart_list_product_name);
        txtShoppingCartProductQuantity = itemView.findViewById(R.id.cart_list_product_quantity);
        txtShoppingCartProductCategory = itemView.findViewById(R.id.cart_list_product_category);
        txtShoppingCartProductPrice = itemView.findViewById(R.id.cart_list_product_price);
        txtShoppingCartProductTotPrice = itemView.findViewById(R.id.cart_list_product_tot);


    }

    @Override
    public void onClick(View view)
    {

        itemClickListner.onClick(view, getAdapterPosition(), false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
