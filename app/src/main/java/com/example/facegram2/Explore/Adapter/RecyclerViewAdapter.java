package com.example.facegram2.Explore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.facegram2.Explore.Api.articleArrayModel;
import com.example.facegram2.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<articleArrayModel> modelClassArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<articleArrayModel> modelClassArrayList) {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_news_res, null , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        // this is when you clicked and go to the webview for showing the
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent  = new Intent(context, webView.class);
                intent.putExtra("url",modelClassArrayList.get(position).getUrl());
                context.startActivity(intent);*/
            }
        });
        // for other parts
        holder.mTIme.setText("Published At "+modelClassArrayList.get(position).getPublishedAt());

        if(modelClassArrayList.get(position).getAuthor() == null) // if there is no author related
        {holder.mAuthor.setText("By Unknown Reporter");}
        else {holder.mAuthor.setText("By  "+modelClassArrayList.get(position).getAuthor());}

        holder.mHeading.setText(modelClassArrayList.get(position).getTitle());
        holder.mContent.setText(modelClassArrayList.get(position).getDescription());

        if(modelClassArrayList.get(position).getUrlToImage() == null) // checking if image available
        {holder.imageView.setImageResource(R.mipmap.ic_launcher);}
        else {
            Glide.with(context).load(modelClassArrayList.get(position).getUrlToImage()).into(holder.imageView);}
    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mHeading, mContent, mAuthor, mCategory,mTIme;
        CardView cardView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // news content
            mHeading = itemView.findViewById(R.id.sample_row_newsHeading);
            mContent = itemView.findViewById(R.id.sample_row_newsContent);
            mAuthor = itemView.findViewById(R.id.sample_row_newsAuthorName);
            mTIme = itemView.findViewById(R.id.sample_row_newsDate);
            //news row
            cardView = itemView.findViewById(R.id.smaple_cardView);
            //news image
            imageView = itemView.findViewById(R.id.sample_row_newsImage);
        }
    }
}

