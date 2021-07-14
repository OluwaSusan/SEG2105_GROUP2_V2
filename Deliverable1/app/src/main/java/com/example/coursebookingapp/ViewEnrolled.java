package com.example.coursebookingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewEnrolled extends AppCompatActivity {
    Button homeBtn_userlist, homeBtn_admin, backBtn_admin;
    com.google.android.material.floatingactionbutton.FloatingActionButton coursebtn_add;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;
    UserAdapter adapter;
    DBHandlerUsers dbUsers;

    BroadcastReceiver BR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dbUsers.listUsers(new FirebaseCallBackUsers() {
                @Override
                public void onCallBackUsersList(ArrayList<User> userList) {


                    initRecylcerView(userList);

                }

                @Override
                public void onCallBackUser(User user) {

                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //refresh the recyclerview to see the new courses
        registerReceiver(BR, new IntentFilter("Refresh Users"));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(BR);
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        backBtn_admin = findViewById(R.id.backBtn_admin);
        homeBtn_userlist = findViewById(R.id.homeBtn_userlist);
        dbUsers = new DBHandlerUsers();

        backBtn_admin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(), Administrator.class));

             }
         }

        );
        homeBtn_userlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MainActivityWelcome.class));
                }
            }
        );

        BR.onReceive(this, null);

    }

    private void initRecylcerView(ArrayList<User> userList){
        RecyclerView recyclerView = findViewById(R.id.recyclerView_userlist);
        adapter = new UserAdapter( userList,this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
