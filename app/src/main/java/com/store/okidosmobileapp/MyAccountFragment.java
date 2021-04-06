package com.store.okidosmobileapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.store.okidosmobileapp.Prevalent.Prevalent;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyAccountFragment extends AppCompatActivity {

    private ShapeableImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText, passwordEditText;
    private TextView profileChangeTextBtn, closeTextBtn, saveTextButton;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureReference;
    private String checker = "";
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount_fragment);

        storageProfilePictureReference = FirebaseStorage.getInstance().getReference().child("Profile Pictures");

        profileImageView = (ShapeableImageView) findViewById(R.id.profile_pic_user_account);
        fullNameEditText = (EditText) findViewById(R.id.fullname_user_account);
        userPhoneEditText = (EditText) findViewById(R.id.phonenumber_user_account);
        addressEditText = (EditText) findViewById(R.id.address_user_account);
        passwordEditText = (EditText) findViewById(R.id.password_user_account);
        profileChangeTextBtn = (TextView) findViewById(R.id.profile_pic_change_btn);
        closeTextBtn = (TextView) findViewById(R.id.close_user_account_btn);
        saveTextButton = (TextView) findViewById(R.id.update_user_account_btn);

        users = new Users();

        userInfoDisplay(profileImageView, fullNameEditText, userPhoneEditText, addressEditText);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                finish();

            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(checker.equals("clicked"))
                {

                    userInfoSaved();

                }
                else
                {

                    updateOnlyUserInfo();

                }

            }
        });

        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                checker = "clicked";

                CropImage.activity(imageUri).setAspectRatio(1,1)
                        .start(MyAccountFragment.this);

            }
        });

    }

    private void updateOnlyUserInfo()
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        users.setName(fullNameEditText.getText().toString().trim());
        users.setAddress(addressEditText.getText().toString().trim());
        users.setPhone(userPhoneEditText.getText().toString().trim());
        users.setPassword(passwordEditText.getText().toString().trim());

        ref.child(Prevalent.CurrentOnlineUser.getPhone()).setValue(users);

        Intent intent = new Intent(MyAccountFragment.this, MyAccountFragment.class);
        startActivity(intent);

        Toast.makeText(MyAccountFragment.this, "User Account Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Againg", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(MyAccountFragment.this, MyAccountFragment.class));
            finish();
        }
    }

    private void userInfoSaved()
    {

        if(TextUtils.isEmpty(fullNameEditText.getText().toString()))
        {
            Toast.makeText(this, "User Name must be Entered", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Address must be Entered", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(userPhoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Phone Number must be Entered", Toast.LENGTH_SHORT).show();
        }
        else if(!(isValidPhone(userPhoneEditText.getText().toString()))){
            Toast.makeText(this, "Enter Valid Contact Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(passwordEditText.getText().toString()))
        {
            Toast.makeText(this, "Password must be Entered", Toast.LENGTH_SHORT).show();
        }
        else if(!(isValidPassword(passwordEditText.getText().toString()))){
            Toast.makeText(this, "Password is not Strong Enough", Toast.LENGTH_SHORT).show();

        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }

    }

    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are Updating your Account Information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imageUri != null)
        {
            final StorageReference fileRef = storageProfilePictureReference.child(Prevalent.CurrentOnlineUser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {

                    if(task.isSuccessful())
                    {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        users.setName(fullNameEditText.getText().toString().trim());
                        users.setAddress(addressEditText.getText().toString().trim());
                        users.setPhone(userPhoneEditText.getText().toString().trim());
                        users.setPassword(passwordEditText.getText().toString().trim());
                        users.setImage(myUrl);

                        ref.child(Prevalent.CurrentOnlineUser.getPhone()).setValue(users);

                        progressDialog.dismiss();

                        Intent intent = new Intent(MyAccountFragment.this, MyAccountFragment.class);
                        startActivity(intent);

                        Toast.makeText(MyAccountFragment.this, "User Account Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(MyAccountFragment.this, "Error, Try Again Shortly", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else
        {
            Toast.makeText(this, "Image must be Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(final ShapeableImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText)
    {

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.CurrentOnlineUser.getPhone());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if(snapshot.exists())
                {
                    if(snapshot.child("image").exists())
                    {
                        String uimage = snapshot.child("image").getValue().toString();
                        String uname = snapshot.child("name").getValue().toString();
                        String upassword = snapshot.child("password").getValue().toString();
                        String uphone = snapshot.child("phone").getValue().toString();
                        String uaddress = snapshot.child("address").getValue().toString();

                        Picasso.get().load(uimage).into(profileImageView);
                        fullNameEditText.setText(uname);
                        passwordEditText.setText(upassword);
                        userPhoneEditText.setText(uphone);
                        addressEditText.setText(uaddress);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public static boolean isValidPassword(String password)
    {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";


        Pattern p = Pattern.compile(regex);


        if (password == null) {
            return false;
        }


        Matcher m = p.matcher(password);


        return m.matches();
    }
    public static boolean isValidPhone(String s)
    {
        Pattern p = Pattern.compile("(0/94)?[0-9]{10}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

}
