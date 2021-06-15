package com.example.coursebookingapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


import java.util.ArrayList;


public class DBHandlerUsers {

    private final DatabaseReference userReference;


    public DBHandlerUsers() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        userReference = rootNode.getReference("users");
    }

    public void addUser(User user) {
        userReference.child(user.getUserName()).setValue(user);
    }

    public void findUser(String username, FirebaseCallBackUsers callBack) {


        Query query = userReference.orderByChild("userName").equalTo(username);
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

    public void listUsers(FirebaseCallBackUsers callBack) {
        ArrayList<User> userList = new ArrayList<User>();

        userReference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);

                            Log.i("test", "user inside loop " + user);
                            userList.add(user);

                        }

                        callBack.onCallBackUsersList(userList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }

    public void deleteUser(String username) {

        userReference.child(username).removeValue();


    }


}


