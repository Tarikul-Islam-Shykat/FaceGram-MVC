package com.example.facegram2.Model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class commentPanel_Model {

    Context context;
    public  static String postKey;
    public  static String userId;
    public  static String comment_Text;
    public  static DatabaseReference userRef;
    public  static DatabaseReference commentRef;

    public commentPanel_Model(Context context, String postKey, String userId, String comment_Text) {
        this.context = context;
        this.postKey = postKey;
        this.userId = userId;
        this.comment_Text = comment_Text;
    }

    public void saveComment(){
        userRef = FirebaseDatabase.getInstance().getReference().child("userProfile");
        commentRef = FirebaseDatabase.getInstance().getReference().child("myVideos").child(postKey).child("comments");

        userRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // if the user Exist, we are getting the name and image from the database.
                    String userName = snapshot.child("uName").getValue().toString();
                    String uImage = snapshot.child("uImage").getValue().toString();

                    String randomPostKey = userId+""+new Random().nextInt(1000); // this random key will be used if the user wants to multiple comment.
                    // if the mainUser id was given, it would create or replace the value while adding the comment in the database.

                    //get the dates
                    Calendar dateValue = Calendar.getInstance();
                    SimpleDateFormat dateFormat  = new SimpleDateFormat("dd-mm-yy");
                    String comment_Date = dateFormat.format(dateValue.getTime());

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    String comment_Time = timeFormat.format(dateValue.getTime());

                    HashMap cmnt = new HashMap();
                    cmnt.put("uid", userId);
                    cmnt.put("username",userName);
                    cmnt.put("userImage",uImage);
                    cmnt.put("usermsg",comment_Text);
                    cmnt.put("date",comment_Date);
                    cmnt.put("time",comment_Time);

                    commentRef.child(randomPostKey).updateChildren(cmnt)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context, "comment added", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(context, "comment not addeded", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*public void showComment(RecyclerView comment_recyclerView){
        FirebaseRecyclerOptions<commentModal> options =
                new FirebaseRecyclerOptions.Builder<commentModal>()
                        .setQuery(commentRef, commentModal.class) // while using this we have to provide the query to find the database or node to fetch.
                        .build(); // this will get the data

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
        comment_recyclerView.setLayoutManager(new LinearLayoutManager(context));
        firebaseRecyclerAdapter.startListening();
        comment_recyclerView.setAdapter(firebaseRecyclerAdapter);
    }*/

}
