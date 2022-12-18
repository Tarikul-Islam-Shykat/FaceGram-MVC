package com.example.facegram2.Model;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userProfile_Model {
    Context context;
    Uri filePath;
    String uName;


    String fetched_uName ;
    String fetched_uImage;

    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("userProfile");
    public StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();

    List<String> list = new ArrayList<>();

    public userProfile_Model(Context context, Uri filePath, String  uName) {
        this.context = context;
        this.filePath = filePath;
        this.uName  = uName;
    }

    public  void saveIntoFireBase(){
        final StorageReference uploader = storageReference.child("profileimages/"+"img"+System.currentTimeMillis());

        uploader.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // get the download url from storage
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Map<String, Object> map = new HashMap<>();
                        map.put("uImage", uri.toString());
                        map.put("uName",uName);

                        // now upload it in the realtime database.
                        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()) // here we are checking if the user id already exists in the database, if true then update else remove.
                                    databaseReference.child(userId).updateChildren(map); // if value already exists we upate it
                                else
                                    databaseReference.child(userId).setValue(map); // if not we set it.

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Storage failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


