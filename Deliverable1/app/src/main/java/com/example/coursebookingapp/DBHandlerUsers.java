package com.example.coursebookingapp;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;


public class DBHandlerUsers {

    private final DatabaseReference userReference;
    User userDel;



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

       /* String email = username + "@userID.com";

        //1-Firebase instance and sign out of admin
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();

        FirebaseAuth dAuth = FirebaseAuth.getInstance();

        //2-Find user to delete email and password
        findUser(username, new FirebaseCallBackUsers() {
            @Override
            public void onCallBackUsersList(ArrayList<User> userList) {

            }

            @Override
            public void onCallBackUser(User user) {

                //userDel = user;
                FirebaseUser currentUser = dAuth.getCurrentUser();
                dAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword());
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), user.getPassword());
                currentUser.reauthenticate(credential);

                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(UserList.this, "User Account Deleted Successfully",Toast.LENGTH_SHORT).show();
                            Log.i("test", "deletion successful");
                        } else {
                            //Toast.makeText(DBHandlerUsers.this, "User Account Deletion Unsuccessful", Toast.LENGTH_SHORT).show();
                            Log.i("test", "deletion failed");
                        }
                    }
                });

                //5-Signout by getting instance
                dAuth.signOut();
            }
        });

        //3-Get user to delete credentials
        //FirebaseAuth dAuth = FirebaseAuth.getInstance();
        //dAuth.signInWithEmailAndPassword(email, userDel.getPassword());
        //FirebaseUser currentUser = dAuth.getCurrentUser();
        //AuthCredential credential = EmailAuthProvider.getCredential(email, userDel.getPassword());
        //currentUser.reauthenticate(credential);

       //4-Delete User
        *//**
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(UserList.this, "User Account Deleted Successfully",Toast.LENGTH_SHORT).show();
                    Log.i("test", "deletion successful");
                } else {
                    //Toast.makeText(DBHandlerUsers.this, "User Account Deletion Unsuccessful", Toast.LENGTH_SHORT).show();
                    Log.i("test", "deletion failed");
                }
            }
        });

        //5-Signout by getting instance
        dAuth.signOut();
         **//*

        //Sign back in to admin
        FirebaseAuth aAuth;
        aAuth = FirebaseAuth.getInstance();
        aAuth.signInWithEmailAndPassword("admin@userID.com", "admin123");
        //String test = aAuth.getCurrentUser().getEmail().toString();
        //Log.i("test", "who is logged in" + test);*/


        userReference.child(username).removeValue();

    }


}


