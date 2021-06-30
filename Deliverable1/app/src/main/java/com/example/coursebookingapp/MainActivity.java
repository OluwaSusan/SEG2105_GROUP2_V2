package com.example.coursebookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends Activity {

    //Variables
    TextInputLayout fullName_reg, username_reg, password_reg;
    Button register_btn, login_redirect_btn;
    TextView error_register;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;
    ProgressBar progressBar;
    DBHandlerUsers db;
    User user;
    String userID;
    FirebaseCallBackUsers callBack;
    User userfound;
    UserType userType = UserType.STUDENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        db = new DBHandlerUsers();
        fullName_reg = findViewById(R.id.course_name);
        username_reg = findViewById(R.id.username_reg);
        password_reg = findViewById(R.id.course_description);
        register_btn = findViewById(R.id.save_btn);
        login_redirect_btn = findViewById(R.id.login_redirect_btn);
        error_register = findViewById(R.id.error_register);
        fAuth = FirebaseAuth.getInstance();
        realDatabase = FirebaseDatabase.getInstance();
        progressBar = findViewById(R.id.progressBar);

        register_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //instance of database

                //fields of User
                String username = username_reg.getEditText().getText().toString();
                String fullName = fullName_reg.getEditText().getText().toString();
                String password = password_reg.getEditText().getText().toString();
                String email = username + "@userID.com";


                //Validate Fields: blank fields, username exists, no numbers in fullName
                if (TextUtils.isEmpty(username)||TextUtils.isEmpty(fullName)||TextUtils.isEmpty(password)){
                    error_register.setText("Please fill in all fields to create an account");
                }
                else if(!validFullName(fullName)){
                    error_register.setText("Invalid full name, remove digits from name");
                }
                else if (userNameExists(username)){
                    error_register.setText("Username already exists, please choose a username or sign in");
                }
                else if(password.length() < 6 || password.length() > 12){
                    error_register.setText("Password must 6-12 characters long");
                }
                else{
                    //add User to database - put in a if condition with valid method as argument*
                    progressBar.setVisibility(View.VISIBLE);
                    //db.addUser(new User(username, fullName, password, onRadioButtonClicked(v)));

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(MainActivity.this, "User Created Successfully",Toast.LENGTH_SHORT).show();

                                //userID is set to dummy email
                                userID = fAuth.getCurrentUser().getUid();
                                //Create reference to database
                                //DatabaseReference storeUser = realDatabase.getReference("Users");

                                //Create a User object
                                User user = new User(username, fullName, password, userType, email);
                                db.addUser(user);
                                Log.i("test", "user created successfully toast + ");


                                Toast.makeText(MainActivity.this, "User Profile Created" + userID,Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(getApplicationContext(), MainActivityWelcome.class));


                            }else{
                                Toast.makeText(MainActivity.this, "User Creation Unsuccessfull",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                    });
                }

            }
        });

        login_redirect_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

    public boolean validFullName(String fullName){

        char[] chars = fullName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    //Check if user exists, if true error message is shown and new user cannot be added
    public boolean userNameExists(String username){
        DBHandlerUsers db = new DBHandlerUsers();
        userfound = null;

        db.findUser(username, new FirebaseCallBackUsers() {
            @Override
            public void onCallBackUsersList(ArrayList<User> userList) {

            }

            @Override
            public void onCallBackUser(User user) {
                userfound = user;
            }
        });

        if(user == null){
            return false;
        }
        else{
            return true;
        }

    }

    public void onRadioButtonClicked(View view) {
        Log.i("test", "reaches inside onCLick Radio");
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.student_radio:
                if (checked)
                userType =  UserType.STUDENT;
                break;

            case R.id.instructor_radio:
                if (checked)
                userType = UserType.INSTRUCTOR;
                break;
        }

    }



}