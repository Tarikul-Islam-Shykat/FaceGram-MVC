package com.example.facegram2.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.facegram2.Model.DashBoard_Model;
import com.example.facegram2.R;
import com.example.facegram2.login.login;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class DashBoard_Controller extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    FloatingActionButton floatingActionButton2_;
    FloatingActionButton getFloatingActionButton3_;

    DashBoard_Model dashBoard_model;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_controller);
        getSupportActionBar().setTitle("FaceGram");

        floatingActionButton = findViewById(R.id.dashboard_floating_actionButton);
        recyclerView = findViewById(R.id.dashBoard_recView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addVideo_Controller.class));
                finish();
            }
        });

        floatingActionButton2_ = findViewById(R.id.to_userProfile);
        floatingActionButton2_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard_Controller.this, userProfile_Controller.class));
                finish();
            }
        });

        getFloatingActionButton3_ = findViewById(R.id.to_explore);
        getFloatingActionButton3_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard_Controller.this, explore_Controller.class));
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dashBoard_model = new DashBoard_Model(getApplicationContext());
        FirebaseRecyclerAdapter firebaseRecyclerAdapter = dashBoard_model.fetch_Value_From_FireBase_Adapter(); // getting all the videos value from databse
        firebaseRecyclerAdapter.startListening(); // setting the adapter
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    // for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // for menu item selected=
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashBoard_Controller.this, login.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}