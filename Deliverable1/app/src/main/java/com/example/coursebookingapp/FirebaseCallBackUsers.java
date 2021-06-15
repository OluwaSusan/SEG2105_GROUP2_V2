package com.example.coursebookingapp;

import java.util.ArrayList;

public interface FirebaseCallBackUsers {
    void onCallBackUsersList(ArrayList<User> userList);
    void onCallBackUser(User user);
}
