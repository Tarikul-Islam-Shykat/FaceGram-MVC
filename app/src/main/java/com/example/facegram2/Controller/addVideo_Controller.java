package com.example.facegram2.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.facegram2.Model.ViewHolder.addVideo_Model;
import com.example.facegram2.R;
import com.example.facegram2.databinding.ActivityAddVideoControllerBinding;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;


public class addVideo_Controller extends AppCompatActivity {
    // binding
    ActivityAddVideoControllerBinding bind_addVideo;
    Uri video_URI; // selecting a video provides a uri, this stores the uri of that video, so that we can save it in the firebase/
    int VIDEO_REQUEST_CODE = 101;
    MediaController mediaController;
    //ProgressDialog progressDialog;

    // modal class for execution
    addVideo_Model addVideo_model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind_addVideo = ActivityAddVideoControllerBinding.inflate(getLayoutInflater());
        setContentView(bind_addVideo.getRoot());

        mediaController = new MediaController(this);
        bind_addVideo.videoViewAddvideo.setMediaController(mediaController);
        bind_addVideo.videoViewAddvideo.start();

        //progress dialog
        // progressDialog = new ProgressDialog(this);
        //progressDialog.setTitle("Please wait");

        //brows video in gallery
        bind_addVideo.btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addVideo_Controller.this, "clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
            }
        });

        bind_addVideo.btnUpload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String video_title = bind_addVideo.txtVtitle.getText().toString();
                String video_uri_ = video_URI.toString();
                addVideo_model = new addVideo_Model(video_URI, getApplicationContext(), video_title, video_uri_);
                addVideo_model.storageUpload();
                startActivity(new Intent(getApplicationContext(), DashBoard_Controller.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == VIDEO_REQUEST_CODE && resultCode == RESULT_OK){
            video_URI = data.getData(); // here we get the uri of that video
            bind_addVideo.videoViewAddvideo.setVideoURI(video_URI);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashBoard_Controller.class));
    }
}