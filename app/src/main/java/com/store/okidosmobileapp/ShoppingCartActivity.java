package com.store.okidosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.store.okidosmobileapp.Prevalent.Prevalent;
import com.store.okidosmobileapp.ViewHolder.CartViewHolder;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button PayNowbutton;
    private TextView TotalShoppingCartPrice;
    String cartItemPrice;
    String cartItemQty;
    String userKey;
    private double overallTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.display_cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PayNowbutton = findViewById(R.id.pay_now_button);
        TotalShoppingCartPrice = findViewById(R.id.total_products_price);
        userKey = Prevalent.CurrentOnlineUser.getPhone();



        PayNowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TotalShoppingCartPrice.setText("Total Price = Rs"+String.valueOf(overallTotalPrice));

                Intent intent = new Intent(ShoppingCartActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("TotalPrice", String.valueOf(overallTotalPrice));
                startActivity(intent);
                finish();

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("CartList").child(userKey);

        FirebaseRecyclerOptions<CartList> options = new FirebaseRecyclerOptions.Builder<CartList>().setQuery(CartListRef, CartList.class).build();

        FirebaseRecyclerAdapter<CartList, CartViewHolder> adapter = new FirebaseRecyclerAdapter<CartList, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final CartList model)
            {

                holder.txtShoppingCartProductName.setText(model.getCartItemName());
                holder.txtShoppingCartProductQuantity.setText(model.getCartItemQuantity());
                holder.txtShoppingCartProductCategory.setText(model.getCartItemCategory());
                holder.txtShoppingCartProductPrice.setText("Item Price = Lkr "+model.getCartItemPrice());



                cartItemPrice = model.getCartItemPrice();
                cartItemQty = model.getCartItemQuantity();

                Double qty = Double.parseDouble(cartItemQty);
                Double price = Double.parseDouble(cartItemPrice);

                Double totPrice = qty*price;

                overallTotalPrice = overallTotalPrice + totPrice;

                holder.txtShoppingCartProductTotPrice.setText("Total Price = Lkr "+totPrice.toString());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Delete"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                if(i == 0)
                                {
                                    Intent intent = new Intent(ShoppingCartActivity.this, UpdateCartListActivity.class);
                                    intent.putExtra("productID", model.getCartProductID());
                                    intent.putExtra("cartItemID", model.getCartItemID());
                                    startActivity(intent);
                                }
                                if(i == 1)
                                {
                                    CartListRef.child(model.getCartItemID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {

                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(ShoppingCartActivity.this, "Item Successfully Removed", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ShoppingCartActivity.this, ShoppingCartActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });
                                }

                            }
                        });

                        builder.show();

                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

}