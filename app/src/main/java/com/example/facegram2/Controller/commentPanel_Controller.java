package com.example.facegram2.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.facegram2.Modal.commentModal;
import com.example.facegram2.Model.ViewHolder.commentViewHolder;
import com.example.facegram2.Model.commentPanel_Model;
import com.example.facegram2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class commentPanel_Controller extends AppCompatActivity {
    EditText commentTxt;
    Button comment_btn;
    String postKey;
    RecyclerView comment_recyclerView;
    String userId;

    public static commentPanel_Model commentPanel_model_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_panel_controller);
        comment_btn = findViewById(R.id.comment_submit_Btn);
        commentTxt = findViewById(R.id.comment_text_txt);
        comment_recyclerView  = findViewById(R.id.comment_recView);

        postKey = getIntent().getStringExtra("postKey").toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentPanel_model_ = new commentPanel_Model(getApplicationContext(), postKey, userId, commentTxt.getText().toString());
                commentPanel_model_.saveComment();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashBoard_Controller.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //commentPanel_model_.showComment(comment_recyclerView);
        DatabaseReference commentRef = FirebaseDatabase.getInstance().getReference().child("myVideos").child(postKey).child("comments");
        FirebaseRecyclerOptions<commentModal> options =
                new FirebaseRecyclerOptions.Builder<commentModal>()
                        .setQuery(commentRef, commentModal.class) // while using this we have to provide the query to find the database or node to fetch.
                        .build(); // this will get the

        // after creating the viewholder (commentViewHolder)
        FirebaseRecyclerAdapter<commentModal, commentViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<commentModal, commentViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull commentViewHolder holder, int position, @NonNull commentModal model) {
                        holder.cuname.setText(model.getUsername().toString()); // make cname public if you cant acceess it
                        holder.cumessage.setText(model.getUsermsg().toString());
                        holder.cudt.setText("Date: "+ model.getDate().toString()+ " Time :"+model.getTime().toString());
                        Glide.with(holder.cuimage.getContext()).load(model.getUserImage()).into(holder.cuimage);
                    }
                    @NonNull
                    @Override
                    public commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_comment_row, parent, false);
                        return new commentViewHolder(view);
                    }
                };
        comment_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        comment_recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}