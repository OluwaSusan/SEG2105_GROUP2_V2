package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserList extends Activity {
    Button homeBtn_userlist, homeBtn_admin, backBtn_admin;
    com.google.android.material.floatingactionbutton.FloatingActionButton coursebtn_add;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        backBtn_admin = findViewById(R.id.backBtn_admin);
        homeBtn_userlist = findViewById(R.id.homeBtn_userlist);

        backBtn_admin.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 startActivity(new Intent(getApplicationContext(), Administrator.class));

                                             }
                                         }
        );
        backBtn_admin.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 startActivity(new Intent(getApplicationContext(), MainActivityWelcome.class));

                                             }
                                         }
        );

    }
}
