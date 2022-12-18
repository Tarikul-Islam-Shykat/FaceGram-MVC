package com.example.facegram2.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.facegram2.Controller.DashBoard_Controller;
import com.example.facegram2.R;
import com.example.facegram2.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    ActivityLoginBinding bind_log;

    String login_email, login_password;
    private FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind_log = ActivityLoginBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(bind_log.getRoot());
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait....");
        progressDialog.setMessage("Login in soon ...");

        firebaseAuth  = FirebaseAuth.getInstance();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), DashBoard_Controller.class));
            finish();
        }
        bind_log.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                login_email = bind_log.edtLoginEmail.getText().toString();
                login_password = bind_log.edtLoginPassword.getText().toString();
                loginUsingFirebase(login_email, login_password);
            }
        });


    }

    private void loginUsingFirebase(String login_email, String login_password) {

        firebaseAuth.signInWithEmailAndPassword(login_email, login_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        Toast.makeText(login.this, "login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DashBoard_Controller.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}