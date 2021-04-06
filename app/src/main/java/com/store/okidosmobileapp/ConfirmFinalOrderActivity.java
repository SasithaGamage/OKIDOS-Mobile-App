package com.store.okidosmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.store.okidosmobileapp.Prevalent.Prevalent;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText txtCusName, txtCusEmail,txtAddress,txtCusPhone;
    private TextView txt2;
    private Button btnConfirm;
    private  String TotalAmount = " ";
    private String key;
    private Orders orders;
    private DatabaseReference orderRef;
    private String userKey;
    public String OId;
    public String OName;
    public String OPhone;
    public String OAddress;
    public String OEmail;
    private String OTotal;
    Boolean update;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        TotalAmount = getIntent().getStringExtra("TotalPrice");


        orders = new Orders();
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtCusName = (EditText)findViewById(R.id.txtCusName);
        txtCusEmail = (EditText)findViewById(R.id.txtCusEmail);
        txtCusPhone = (EditText)findViewById(R.id.txtCusPhone);
        txt2 =findViewById(R.id.txt2);

        Intent intent = getIntent();

        OId=intent.getStringExtra("OId");
        OName=intent.getStringExtra("OName");
        OPhone=intent.getStringExtra("OPhone");
        OAddress=intent.getStringExtra("OAddress");
        OEmail=intent.getStringExtra("OEmail");
        OTotal=intent.getStringExtra("total");

        txt2.setText("Total : Lkr "+TotalAmount);
        txtAddress.setText(OAddress);
        txtCusEmail.setText(OEmail);
        txtCusPhone.setText(OPhone);
        txtCusName.setText(OName);

        userKey = Prevalent.CurrentOnlineUser.getPhone();


        if(OId == null){
            update =false;
            //System.out.println("insert");
        }
        else{
            update = true;
            //System.out.println("update");
            btnConfirm.setText("Update Order");

        }




    }

    @Override
    protected void onResume() {
        super.onResume();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                check();
            }
        });
    }

    private void check(){
        if(TextUtils.isEmpty(txtCusName.getText().toString())){
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(txtCusPhone.getText().toString())){
            Toast.makeText(this, "Enter Contact Number", Toast.LENGTH_SHORT).show();

        }
        else if(!(isValidPhone(txtCusPhone.getText().toString()))){
            Toast.makeText(this, "Enter Valid Contact Number", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(txtAddress.getText().toString())){
            Toast.makeText(this, "Enter Delivery Address", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(txtCusEmail.getText().toString())){
            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show();

        }

        else if(!(isValidEmail(txtCusEmail.getText().toString()))){
            Toast.makeText(this, "Enter Valid Your Email", Toast.LENGTH_SHORT).show();

        }
        else{

            if(update==true){
                updateOrder();
            }
            else {
                confirmOrder();
            }

        }
    }

    private void confirmOrder(){
        Calendar calendar = Calendar.getInstance();
        String saveCurrentDate,saveCurrentTime;
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());


        key = orderRef.push().getKey();
        orders.setID(key);
        orders.setQuantity(TotalAmount);
        orders.setDate(saveCurrentDate);
        orders.setTime(saveCurrentTime);
        orders.setCusName(txtCusName.getText().toString().trim());
        orders.setCusPhone(txtCusPhone.getText().toString().trim());
        orders.setAddress(txtAddress.getText().toString().trim());
        orders.setCusEmail(txtCusEmail.getText().toString().trim());

        orderRef.child(userKey).child(key).setValue(orders);

        final DatabaseReference CartListRef1 = FirebaseDatabase.getInstance().getReference().child("CartList").child(userKey);
        CartListRef1.removeValue();
        Toast.makeText(this, "Order added Successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ConfirmFinalOrderActivity.this, OrderListActivity.class);
        startActivity(intent);




    }

    public  void updateOrder(){

        Calendar calendar = Calendar.getInstance();
        String saveCurrentDate,saveCurrentTime;
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        orders.setID(OId);
        orders.setQuantity(OTotal);
        orders.setDate(saveCurrentDate);
        orders.setTime(saveCurrentTime);
        orders.setCusName(txtCusName.getText().toString().trim());
        orders.setCusPhone(txtCusPhone.getText().toString().trim());
        orders.setAddress(txtAddress.getText().toString().trim());
        orders.setCusEmail(txtCusEmail.getText().toString().trim());

        orderRef.child(userKey).child(OId).setValue(orders);

        Toast.makeText(this, "Order Updated Successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ConfirmFinalOrderActivity.this, OrderListActivity.class);
        startActivity(intent);

    }

    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public static boolean isValidPhone(String s)
    {
        Pattern p = Pattern.compile("(0/94)?[0-9]{10}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

}