package com.example.facegram2.Model.ViewHolder;

import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.facegram2.Modal.videoFileModal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class addVideo_Model  {

    Uri videoUri;
    Context context;

    String video_title;
    String video_uri_;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("myVideos");

    public addVideo_Model(Uri videoUri, Context context, String video_title, String video_uri_) {
        this.videoUri = videoUri;
        this.context = context;
        this.video_title = video_title;
        this.video_uri_ = video_uri_;
    }

    public void storageUpload(){
        StorageReference uploader = storageReference.child("uploadedVideos/"+System.currentTimeMillis()+"."+getExtension_of_Video());
        uploader.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // at this point the file successfully uploaded in the firebase storage, now the main task is to get the url.
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(context, "Storage Upload Successful", Toast.LENGTH_SHORT).show();
                        // now its time ot upload the video url in the firebase realtime database.
                        // we are going to provide the object/ modal to the firebase Realtime database, which is helpful for not providing one info at one time.
                        videoFileModal object_of_Video = new videoFileModal(video_title, uri.toString());
                        videoInfo_to_RealTimeDataBase(object_of_Video);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void videoInfo_to_RealTimeDataBase(videoFileModal object_of_video) {
        databaseReference.child(databaseReference.push().getKey())
                .setValue(object_of_video)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "RealTime Database Successfull", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    // so every video hav different extension, to figure that out, we have this function\
    public  String getExtension_of_Video(){
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(context.getContentResolver().getType(videoUri));
    }



}
