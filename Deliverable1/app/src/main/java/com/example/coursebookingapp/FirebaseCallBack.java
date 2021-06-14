package com.example.coursebookingapp;

import java.util.ArrayList;

public interface FirebaseCallBack {
    void onCallBackList(ArrayList<User> userList);
    void onCallBackUser(User user);
}
