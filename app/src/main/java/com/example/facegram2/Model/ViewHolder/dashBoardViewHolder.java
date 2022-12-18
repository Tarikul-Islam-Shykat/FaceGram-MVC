package com.example.facegram2.Model.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facegram2.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dashBoardViewHolder extends RecyclerView.ViewHolder {

    public StyledPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    public TextView status;

    DatabaseReference likeReference;

    public TextView like_txt;
    public ImageButton like_btn; // make them public for other apps to user.

    public  ImageButton comment_btn;

    public dashBoardViewHolder(@NonNull View itemView) {
        super(itemView);

        // video showing and text showing
        status = itemView.findViewById(R.id.sample_row_Status);
        simpleExoPlayerView = itemView.findViewById(R.id.sample_row_exoPlayer_view);

        //like countText and likeButton
        like_btn = itemView.findViewById(R.id.sample_row_love);
        like_txt = itemView.findViewById(R.id.sample_row_Liketext);

        comment_btn = itemView.findViewById(R.id.sample_row_comment);
    }

    // preparing the exoPlayer and setting the text and video.
    public void prepareExoplayer(Context context, String title, String vurl){
        try {
            simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
            MediaItem mediaItem = MediaItem.fromUri(vurl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.addMediaItem(mediaItem);
            simpleExoPlayer.prepare();
            simpleExoPlayer.play();
            status.setText(title);
        }
        catch (Exception e )
        {
            Log.d("Exoplayer Crashed", e.getMessage().toString());
        }
    }

    public void getLikeButtonStatus(String postKey_videoId, String userId) {
        // create the root
        likeReference = FirebaseDatabase.getInstance().getReference("likes");
        // now push the value in the databse
        likeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(postKey_videoId).hasChild(userId)) // so what this means? this means that if the following video has the userId, we show the like button
                {
                    int likeCount  = (int) snapshot.child(postKey_videoId).getChildrenCount();// this gives the total number of likes on that video
                    like_txt.setText(likeCount+" likes"); // showes the number of likes in the textView
                    like_btn.setImageResource(R.drawable.sample_row_ove);
                }
                else
                { // if the user didn't like any video
                    int likeCount  = (int) snapshot.child(postKey_videoId).getChildrenCount();// this gives the total number of likes on that video
                    like_txt.setText(likeCount+" likes"); // showes the number of likes in the textView
                    like_btn.setImageResource(R.drawable.sample_row_unlove);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
