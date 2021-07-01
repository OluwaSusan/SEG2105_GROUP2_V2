package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivityWelcome extends AppCompatActivity {

    TextView name_loggedin_textview, role_loggedin_textview;
    ImageButton imageButton_login;
    Button btnSignOut;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        name_loggedin_textview = findViewById(R.id.name_loggedin_textview);
        role_loggedin_textview = findViewById(R.id.role_loggedin_textview);
        imageButton_login = findViewById(R.id.imageButton_login);
        btnSignOut = findViewById(R.id.btnSignOut);
        progressBar = findViewById(R.id.progressBar_signOut);
        currentUser();

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

        });

        imageButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHandlerUsers dbUsers = new DBHandlerUsers();

                dbUsers.findUser(fAuth.getCurrentUser().getEmail().split("@")[0], new FirebaseCallBackUsers() {
                    @Override
                    public void onCallBackUsersList(ArrayList<User> userList) {

                    }

                    @Override
                    public void onCallBackUser(User user) {

                        if (user.getUserType() == UserType.ADMIN){

                            startActivity(new Intent(MainActivityWelcome.this, Administrator.class));
                        }
                        else if (user.getUserType() == UserType.INSTRUCTOR){
                            startActivity(new Intent(MainActivityWelcome.this, Instructor.class));
                        }
                        else{
                            Toast.makeText(MainActivityWelcome.this, "Student currently has no permissions",Toast.LENGTH_SHORT).show();
                        }

                    }
                });



            }
        });


    }

    public void currentUser(){

            fAuth = FirebaseAuth.getInstance();
            realDatabase = FirebaseDatabase.getInstance();

            String email = fAuth.getCurrentUser().getEmail();
            String[] parts = email.split("@");
            String username = parts[0];
            Log.i("test" , "username " + username);


            DBHandlerUsers db = new DBHandlerUsers();

            db.findUser(username, new FirebaseCallBackUsers() {
                @Override
                public void onCallBackUsersList(ArrayList<User> userList) {

                }

                @Override
                public void onCallBackUser(User user) {
                    name_loggedin_textview.setText(user.getFullName());
                    role_loggedin_textview.setText(user.getUserType().toString());                }
            });


        }

}
