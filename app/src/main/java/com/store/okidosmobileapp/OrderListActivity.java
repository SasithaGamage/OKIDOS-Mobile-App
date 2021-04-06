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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.store.okidosmobileapp.Prevalent.Prevalent;
import com.store.okidosmobileapp.ViewHolder.OrdersViewHolder;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    String userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        recyclerView = findViewById(R.id.display_Orders_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        userKey = Prevalent.CurrentOnlineUser.getPhone();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference OrderListRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(userKey);
        FirebaseRecyclerOptions<Orders> options = new FirebaseRecyclerOptions.Builder<Orders>().setQuery(OrderListRef,Orders.class).build();
        FirebaseRecyclerAdapter<Orders, OrdersViewHolder> adapter = new FirebaseRecyclerAdapter<Orders, OrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder ordersViewHolder, int i, @NonNull final Orders orders) {
                ordersViewHolder.txtDate.setText(orders.getDate());
                ordersViewHolder.txtTime.setText(orders.getTime());
                ordersViewHolder.txtPrice.setText("Total Amount = Lkr "+orders.getQuantity());
                ordersViewHolder.txtAddress.setText(orders.getAddress());
                ordersViewHolder.txtPhone.setText(orders.getCusPhone());

                ordersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Delete"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderListActivity.this);
                        builder.setTitle("Orders Options");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0){
                                    Intent intent = new Intent(OrderListActivity.this,ConfirmFinalOrderActivity.class);
                                    intent.putExtra("OId",orders.getID());
                                    intent.putExtra("OName",orders.getCusName());
                                    intent.putExtra("OPhone",orders.getCusPhone());
                                    intent.putExtra("OAddress",orders.getAddress());
                                    intent.putExtra("OEmail",orders.getCusEmail());
                                    intent.putExtra("total",orders.getQuantity());


                                    startActivity(intent);
                                }
                                if(i==1){
                                    OrderListRef.child(orders.getID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(OrderListActivity.this, "Order Removed", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(OrderListActivity.this, HomeFragment.class);
//                                                startActivity(intent);

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
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                OrdersViewHolder holder = new OrdersViewHolder(view);
                return  holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}