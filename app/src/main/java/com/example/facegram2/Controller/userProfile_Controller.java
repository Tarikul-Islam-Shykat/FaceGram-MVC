package com.example.facegram2.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.facegram2.Model.userProfile_Model;
import com.example.facegram2.R;
import com.example.facegram2.databinding.ActivityUserProfileControllerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

public class userProfile_Controller extends AppCompatActivity {

    ActivityUserProfileControllerBinding bind_userProfile;

    Uri filePath;
    Bitmap bitmap;
    int IMAGE_REQUEST_CODE= 101;

    userProfile_Model userProfile_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind_userProfile = ActivityUserProfileControllerBinding.inflate(getLayoutInflater());
        setContentView(bind_userProfile.getRoot());

        Toast.makeText(this, "came", Toast.LENGTH_SHORT).show();

        bind_userProfile.userProfileUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });


        bind_userProfile.userProfileUserSaveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfile_model = new userProfile_Model(getApplicationContext(), filePath, bind_userProfile.userProfileUserName.getText().toString());
                userProfile_model.saveIntoFireBase();
            }
        });
    }

    // open camera and get image
    private void openCamera() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Please Select file"),IMAGE_REQUEST_CODE);
    }

    // set the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            filePath = data.getData(); // this file path is collected, for firebase
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                bind_userProfile.userProfileUserImage.setImageBitmap(bitmap);
            }
            catch (Exception ex){
                Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // for going back to the dashBoard
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashBoard_Controller.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("userProfile");

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    bind_userProfile.userNameTxt.setText(snapshot.child("uName").getValue().toString());
                    Glide.with(getApplicationContext()).load(snapshot.child("uImage").getValue().toString()).into(bind_userProfile.userProfileUserImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}