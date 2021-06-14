package com.example.coursebookingapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivityWelcome extends AppCompatActivity {

    TextView name_loggedin_textview, role_loggedin_textview;
    ImageView login_imageview;
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
        login_imageview = findViewById(R.id.login_imageview);
        btnSignOut = findViewById(R.id.btnSignOut);
        progressBar = findViewById(R.id.progressBar_signOut);
        fAuth = FirebaseAuth.getInstance();
        realDatabase = FirebaseDatabase.getInstance();

        btnSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

        });

        login_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = fAuth.getCurrentUser().getEmail();
                String[] parts = email.split("@");
                String username = parts[0];

                DBHandlerUsers db = new DBHandlerUsers();
                currentUser = null;

                db.findUser(username, new FirebaseCallBack() {
                    @Override
                    public void onCallBackList(ArrayList<User> userList) {

                    }

                    @Override
                    public void onCallBackUser(User user) {
                        currentUser= user;
                    }
                });

                name_loggedin_textview.setText(currentUser.getFullName());
                role_loggedin_textview.setText(currentUser.getUserType().toString());


            }
        });



    }



}
