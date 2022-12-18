package com.example.facegram2.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.facegram2.Controller.commentPanel_Controller;
import com.example.facegram2.Modal.videoFileModal;
import com.example.facegram2.Model.ViewHolder.dashBoardViewHolder;
import com.example.facegram2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard_Model {

    Context context;

    DatabaseReference likeReference = FirebaseDatabase.getInstance().getReference("likes");
    Boolean testClick = false;

    public DashBoard_Model(Context context) {
        this.context = context;
    }

    public FirebaseRecyclerAdapter fetch_Value_From_FireBase_Adapter(){

        DatabaseReference query = FirebaseDatabase.getInstance().getReference().child("myVideos");
        FirebaseRecyclerOptions<videoFileModal> options =
                new FirebaseRecyclerOptions.Builder<videoFileModal>()
                        .setQuery(query, videoFileModal.class)// query is the path, which indicates from which node you are going to fetch the data.
                        .build();

        FirebaseRecyclerAdapter<videoFileModal, dashBoardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<videoFileModal, dashBoardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull dashBoardViewHolder holder, int position, @NonNull videoFileModal model) {
                holder.prepareExoplayer(context, model.getTitle(), model.getVurl());

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = firebaseUser.getUid();
                // get the video Id
                String postKey_videoId = getRef(position).getKey();

                holder.getLikeButtonStatus(postKey_videoId, userId);

                holder.like_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        testClick = true;
                        likeReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(testClick == true){
                                    // check first if the following video already liked by the user
                                    if(snapshot.child(postKey_videoId).hasChild(userId)){

                                         /*this portion of the code is for removing the like if pressed again.
                                         f the userId is already inside the videoId and userPress the button then the
                                         the userId will be gone from there.*/
                                        Toast.makeText(context, "removed like", Toast.LENGTH_SHORT).show();
                                        likeReference.child(postKey_videoId).removeValue();
                                        testClick = false;
                                    }
                                    else
                                    {
                                         /* if not then
                                          push the userId under the videoId,*/
                                        Toast.makeText(context, "added like", Toast.LENGTH_SHORT).show();
                                        likeReference.child(postKey_videoId).child(userId).setValue(true);
                                        testClick = false;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                holder.comment_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, commentPanel_Controller.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // when you want to start an activity but not from the activity but from the model class
                        intent.putExtra("postKey", postKey_videoId);
                        context.startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public dashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_video_row_dashboard, parent, false);
                return new dashBoardViewHolder(view);
            }
        };

        return firebaseRecyclerAdapter;
    }




}
