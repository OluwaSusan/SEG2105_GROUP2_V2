package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coursebookingapp.DBHandlerUsers;
import com.example.coursebookingapp.User;
import com.example.coursebookingapp.UserType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Variables
    TextInputLayout fullName_reg, username_reg, password_reg;
    Button register_btn, login_redirect_btn;
    TextView error_register;
    FirebaseAuth fAuth;
    FirebaseDatabase realDatabase;
    ProgressBar progressBar;
    String userID;
    //RadioGroup userType_group;
    //RadioButton radio_student, radio_instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        fullName_reg = findViewById(R.id.fullName_reg);
        username_reg = findViewById(R.id.username_reg);
        password_reg = findViewById(R.id.password_reg);
        register_btn = findViewById(R.id.register_btn);
        login_redirect_btn = findViewById(R.id.login_redirect_btn);
        error_register = findViewById(R.id.error_register);
        //userType_group = findViewById(R.id.userType_group);
        //radio_student = findViewById(R.id.student_radio);
        //radio_instructor = findViewById(R.id.instructor_radio);
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
                    error_register.setText("Password must 5-12 characters long");
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
                                userID = fAuth.getCurrentUser().getEmail();

                                //Create reference to database
                                DatabaseReference storeUser = realDatabase.getReference("Users");

                                //Create a User object
                                User user = new User(username, fullName, password, onRadioButtonClicked(v));

                                //Store User object in real-time database
                                storeUser.child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(MainActivity.this, "User Created Successfully" + userID,Toast.LENGTH_SHORT).show();
                                    }
                                });
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                //if user creation fails
                            }else{
                                Toast.makeText(MainActivity.this, "User Creation Unsuccessfull",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });

        login_redirect_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), Login.class));
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
        User user = db.findUser(username);

        if(user == null){
            return false;
        }
        else{
            return true;
        }

    }

    public UserType onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.student_radio:
                if (checked)

                    break;
                return UserType.STUDENT;

            case R.id.instructor_radio:
                if (checked)

                    break;
                return UserType.INSTRUCTOR;
        }
        return null;
    }

}