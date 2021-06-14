package com.example.coursebookingapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.coursebookingapp.FirebaseCallBack;
import com.example.coursebookingapp.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


import java.util.ArrayList;


public class DBHandlerUsers {

    private final DatabaseReference userRefrence;


    public DBHandlerUsers() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        userRefrence = rootNode.getReference("users");
    }

    public void addUser(User user) {
        userRefrence.child(user.getUserName()).setValue(user);
    }

    public void findUser(String username, FirebaseCallBack callBack) {


        Query query = userRefrence.orderByChild("userName").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists()) {


                    User dbUser = snapshot.child(username).getValue(User.class);
                    callBack.onCallBackUser(dbUser);
                    Log.i("test", "reaches inside the find function ");

                }else {
                    callBack.onCallBackUser(new User());
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                Log.i("test", "reaches here");

            }


        });


    }

    public void listUsers(FirebaseCallBack callBack) {
        ArrayList<User> userList = new ArrayList<User>();

        userRefrence
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);

                            Log.i("test", "user inside loop " + user);
                            userList.add(user);

                        }

                        callBack.onCallBackList(userList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }

    public void deleteUser(String username) {

        userRefrence.child(username).removeValue();


    }


}


