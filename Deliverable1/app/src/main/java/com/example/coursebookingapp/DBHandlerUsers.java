package com.example.coursebookingapp;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBHandlerUsers {

        private final DatabaseReference reference;

        public DBHandlerUsers(){
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Users");
        }

        public void addUser(User user){
            reference.child(user.getUserName()).setValue(user);
        }

        public void deleteUser(){

        }
        public User findUser(String username){
            User user = new User();
            //firebase implementation
            return user;
        }

        //findUser - return user object
        //ListUsers


}
