package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Login extends Activity {

    TextInputLayout username_login, password_login;
    Button login_btn, register_redirect_btn;
    TextView error_login;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;
    ProgressBar progressBar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //variables
        username_login = findViewById(R.id.username_login);
        password_login = findViewById(R.id.password_login);
        login_btn = findViewById(R.id.login_btn);
        register_redirect_btn = findViewById(R.id.register_redirect_btn);
        error_login = findViewById(R.id.error_login);
        fAuth = FirebaseAuth.getInstance();
        realDatabase = FirebaseDatabase.getInstance();
        progressBar = findViewById(R.id.progressBar_login);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_login.getEditText().getText().toString();
                String password = password_login.getEditText().getText().toString();
                String email = username + "@userID.com";

                if (TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)){
                    error_login.setText("Please fill in all fields to sign in");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(Login.this, "User Signed in",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }else{
                                error_login.setText("Invalid Username or Password");
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                        }
                    });
                }
            }
        });

        register_redirect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
}
