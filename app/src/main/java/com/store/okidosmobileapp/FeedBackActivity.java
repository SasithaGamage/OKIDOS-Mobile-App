package com.store.okidosmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.store.okidosmobileapp.Prevalent.Prevalent;

public class FeedBackActivity extends AppCompatActivity {
    private Button btn;
    private String userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        userKey = Prevalent.CurrentOnlineUser.getPhone();
        btn=findViewById(R.id.btnMainActivity);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Feedback Form");
        dialog.setMessage("Provide us your valuable feedback");

        LayoutInflater inflater=LayoutInflater.from(this);
        View reg_layout=inflater.inflate(R.layout.send_feedback,null);

        final MaterialEditText edtEmail=reg_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtName=reg_layout.findViewById(R.id.edtName);
        final MaterialEditText edtFeedback=reg_layout.findViewById(R.id.edtFeedback);

        dialog.setView(reg_layout);

        //set button

        dialog.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //check validation

                if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    Toast.makeText(FeedBackActivity.this, "Please enter your email...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edtName.getText().toString())){
                    Toast.makeText(FeedBackActivity.this, "Name field cannot be empty...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edtFeedback.getText().toString())){
                    Toast.makeText(FeedBackActivity.this, "Feedback field cannot be empty...", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseDatabase database=FirebaseDatabase.getInstance();

                DatabaseReference myRef=database.getReference();

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Object value=dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(FeedBackActivity.this, "Failed to read value", Toast.LENGTH_SHORT).show();

                    }
                });

                myRef.child("FeedBack").child(userKey).child(edtName.getText().toString()).child("Email").setValue(edtEmail.getText().toString());
                myRef.child("FeedBack").child(userKey).child(edtName.getText().toString()).child("Feedback").setValue(edtFeedback.getText().toString());
                myRef.child("FeedBack").child(userKey).child(edtName.getText().toString()).child("Name").setValue(edtName.getText().toString());

                Toast.makeText(FeedBackActivity.this, "Thanks for your feedback..", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}