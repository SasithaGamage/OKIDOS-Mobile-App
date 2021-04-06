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

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartButton;
    private ImageView addToCartImage;
    private ElegantNumberButton addToCartNumberButton;
    private TextView addToCartProductName;
    private TextView addtoCartProductDescription;
    private TextView addToCartProductPrice;
    private String addtoCartCategory;
    private String addtoCartProductID;
    private String addToCartProductID = "";
    CartList cartList;
    private String Cartkey;
    private String userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        addToCartProductID = getIntent().getStringExtra("productID");

        addToCartButton = (Button) findViewById(R.id.productd_add_to_cart_button);
        addToCartNumberButton = (ElegantNumberButton) findViewById(R.id.addtocart_number_btn);
        addToCartImage = (ImageView) findViewById(R.id.addtocart_product_image);
        addToCartProductName = (TextView) findViewById(R.id.addtocart_product_name);
        addtoCartProductDescription = (TextView) findViewById(R.id.addtocart_product_description);
        addToCartProductPrice = (TextView) findViewById(R.id.addtocart_product_price);

        userKey = Prevalent.CurrentOnlineUser.getPhone();

        getProductDetails(addToCartProductID);

        cartList = new CartList();

        addToCartButton.setOnClickListener(new View.OnClickListener() {
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

        Cartkey = CartListRef.push().getKey();
        cartList.setCartItemID(Cartkey);
        cartList.setCartProductID(addtoCartProductID);
        cartList.setCartItemDate(saveCurrentDate);
        cartList.setCartItemTime(saveCurrentTime);
        cartList.setCartItemName(addToCartProductName.getText().toString().trim());
        cartList.setCartItemCategory(addtoCartCategory);
        cartList.setCartItemPrice(addToCartProductPrice.getText().toString().trim());
        cartList.setCartItemQuantity(addToCartNumberButton.getNumber());

        CartListRef.child(userKey).child(Cartkey).setValue(cartList);

        Toast.makeText(ProductDetailsActivity.this, "Product added to Cart", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProductDetailsActivity.this, HomeFragment.class);
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

                    addToCartProductName.setText(product.getProductName());
                    addtoCartProductID = product.getProductID();
                    addtoCartProductDescription.setText(product.getProductDescription());
                    addToCartProductPrice.setText(product.getProductPrice());
                    addtoCartCategory = product.getProductCategory();
                    Picasso.get().load(product.getProductImage()).into(addToCartImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}