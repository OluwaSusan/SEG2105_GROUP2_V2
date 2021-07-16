package com.example.coursebookingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ViewEnrolled extends AppCompatActivity {
    Button homeBtn_userlist, homeBtn_admin, backBtn_admin;
    TextView err_mssg;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;
    UserAdapter adapter;
    DBHandlerUsers dbUsers;
    String specificCourse;
    DBHandlerCourses dbCourses;
    boolean sw = false;
    BroadcastReceiver BR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
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
        dbCourses = new DBHandlerCourses();
        err_mssg = findViewById(R.id.err_userlist);

        checkExtra(savedInstanceState);
        refresh();


        backBtn_admin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
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

    private void initRecylcerView(ArrayList<User> userList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_userlist);
        adapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void checkExtra(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                specificCourse = null;
            } else {
                specificCourse = extras.getString("Course_ID");
                sw = !sw;
            }
        } else {
            specificCourse = (String) savedInstanceState.getSerializable("Course_ID");
        }
    }

    private boolean getSw() {
        return sw;
    }

    private void refresh() {

        dbUsers.listUsers(new FirebaseCallBackUsers() {
            @Override
            public void onCallBackUsersList(ArrayList<User> userList) {
                if (getSw() == false){

                    Log.i("Refresh", "Here it is: " + getSw());
                    Toast toast = Toast.makeText(getApplicationContext(), "Now Users are enrolled in the class", Toast.LENGTH_SHORT);
                    toast.show();

                } else {

                    Log.i("Refresh", "Here it is: " + getSw());
                    Toast toast = Toast.makeText(getApplicationContext(), "Users are enrolled in the class", Toast.LENGTH_SHORT);
                    toast.show();
                    ArrayList<User> usersEnr = new ArrayList<>();

                    Log.i("Course", "Here it is: " + specificCourse);

                    for(int i =0; i < userList.size(); i++){

                        Log.i("Step 1 Users", "Here it is: " + userList.get(i).getMyCourses());

                        if (!userList.get(i).getMyCourses().isEmpty()){

                            Log.i("Step 3 Refresh", "Here it is: " + userList.get(i).getMyCourses().containsKey(specificCourse));
                            Log.i("Step 4 Course", "Here it is: " + specificCourse);

                            if (userList.get(i).getMyCourses().containsKey(specificCourse)){

                                if(userList.get(i).getUserType() == UserType.STUDENT){
                                    usersEnr.add(userList.get(i));
                                    Log.i("Lists Step 5", "Here it is: " + userList.get(i).getUserName());
                                    Log.i("Lists Step 6", "Here it is: " + userList.get(i).getMyCourses());
                                }
                                else{

                                }
                            }

                        }else{
                            continue;
                        }

                    }
                    Log.i("Refresh Step 7", "Here it is: " + usersEnr.isEmpty());
                    initRecylcerView(usersEnr);
                }
            }

            @Override
            public void onCallBackUser(User user) {

            }
        });

    }
}



