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

    private final DatabaseReference userRefrence;


    public DBHandlerUsers() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        userRefrence = rootNode.getReference("users");
    }

    public void addUser(User user) {
        userRefrence.child(user.getUserName()).setValue(user);

        for(int i = 0; i<user.getCourses().size(); i++){
            userRefrence.child(user.getUserName()).child("courses").child(user.getCourses().get(i).getCourseName()).setValue(user.getCourses().get(i));
        }





    }

    public void findUser(String username, FirebaseCallBackUsers callBack) {


        Query query = userRefrence.orderByChild("userName").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                if (snapshot.exists()) {



                    String name = snapshot.child(username).child("fullName").getValue(String.class);
                    String password = snapshot.child(username).child("password").getValue(String.class);
                    UserType type = snapshot.child(username).child("userType").getValue(UserType.class);
                    String email = snapshot.child(username).child("email").getValue(String.class);

                    ArrayList<Course> courses = new ArrayList<Course>();

                    Log.i("test", "the children " + snapshot.child(username).child("courses").toString());

                    for (DataSnapshot dataSnapshot : snapshot.child(username).child("courses").getChildren()) {
                        Log.i("test", "reach inside the courses loop");
                        Course course = dataSnapshot.getValue(Course.class);


                        courses.add(course);

                    }

                    User dbUser = new User(username, name, password, type,email, courses);


                    callBack.onCallBackUser(dbUser);
                    Log.i("test", "reaches inside the find function ");

                }else{
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

        userRefrence
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            String name = snapshot.child("fullName").getValue(String.class);
                            String password = snapshot.child("password").getValue(String.class);
                            UserType type = snapshot.child("userType").getValue(UserType.class);
                            String email = snapshot.child("email").getValue(String.class);


                            ArrayList<Course> courses = new ArrayList<Course>();


                            for (DataSnapshot dataSnapshotCourses : snapshot.child("courses").getChildren()) {
                                Log.i("test", "reach inside the courses loop");
                                Course course = dataSnapshotCourses.getValue(Course.class);


                                courses.add(course);

                            }

                            User user = new User(snapshot.getKey(), name, password, type,email, courses);
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

        userRefrence.child(username).removeValue();


    }


}


