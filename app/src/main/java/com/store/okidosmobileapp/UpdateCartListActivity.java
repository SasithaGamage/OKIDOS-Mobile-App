package com.store.okidosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.store.okidosmobileapp.Prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateCartListActivity extends AppCompatActivity {

    private Button updateCartButton;
    private ImageView updateCartImage;
    private ElegantNumberButton updateCartNumberButton;
    private TextView updateCartProductName;
    private TextView updateCartProductDescription;
    private TextView updateCartProductPrice;
    private String updateCartCategory;
    private String updateCartProductID;
    private String addToCartProductID = "";
    private String updateItemID = "";
    CartList cartList;
    private String Cartkey;
    private String userKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cart_list);

        addToCartProductID = getIntent().getStringExtra("productID");
        updateItemID = getIntent().getStringExtra("cartItemID");

        updateCartButton = (Button) findViewById(R.id.update_cart_button);
        updateCartNumberButton = (ElegantNumberButton) findViewById(R.id.update_number_btn);
        updateCartImage = (ImageView) findViewById(R.id.update_product_image);
        updateCartProductName = (TextView) findViewById(R.id.update_product_name);
        updateCartProductDescription = (TextView) findViewById(R.id.update_product_description);
        updateCartProductPrice = (TextView) findViewById(R.id.update_product_price);

        userKey = Prevalent.CurrentOnlineUser.getPhone();

        getProductDetails(addToCartProductID);

        cartList = new CartList();

        updateCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                addingToCartList();

            }
        });

    }

    private void addingToCartList()
    {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        String saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calendar.getTime());

        DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        cartList.setCartItemID(updateItemID);
        cartList.setCartProductID(updateCartProductID);
        cartList.setCartItemDate(saveCurrentDate);
        cartList.setCartItemTime(saveCurrentTime);
        cartList.setCartItemName(updateCartProductName.getText().toString().trim());
        cartList.setCartItemCategory(updateCartCategory);
        cartList.setCartItemPrice(updateCartProductPrice.getText().toString().trim());
        cartList.setCartItemQuantity(updateCartNumberButton.getNumber());

        CartListRef.child(userKey).child(updateItemID).setValue(cartList);

        Toast.makeText(UpdateCartListActivity.this, "Cart Updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateCartListActivity.this, ShoppingCartActivity.class);
        startActivity(intent);

    }

    private void getProductDetails(final String addToCartProductID)
    {

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Product");

        productsRef.child(addToCartProductID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if(snapshot.exists())
                {
                    Product product = snapshot.getValue(Product.class);

                    updateCartProductName.setText(product.getProductName());
                    updateCartProductID = product.getProductID();
                    updateCartProductDescription.setText(product.getProductDescription());
                    updateCartProductPrice.setText(product.getProductPrice());
                    updateCartCategory = product.getProductCategory();
                    Picasso.get().load(product.getProductImage()).into(updateCartImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}