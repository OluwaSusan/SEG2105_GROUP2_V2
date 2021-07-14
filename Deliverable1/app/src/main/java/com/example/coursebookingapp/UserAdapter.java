package com.example.coursebookingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursebookingapp.Course;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    // creating variables
    private ArrayList<User> userArrayList;
    private Context context;

    // constructor
    public UserAdapter(ArrayList<User> userModalArrayList, Context context) {
        this.userArrayList = userModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    // this is called when the recyclerview needs to represent an item
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file for our recycler view items
        // layout inflater is used to create a new product item for our layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    // called by RecyclerView to display the data at the specified position
    // it updates the contents of the recycler view item to reflect the specific product
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User u = userArrayList.get(position);


        holder.userName.setText(String.valueOf(userArrayList.get(position).getUserName()));
        holder.usertype.setText(String.valueOf(u.getUserType()));
        holder.userfullName.setText(String.valueOf(userArrayList.get(position).getFullName()));
        // we display data as text using setText() but price is a double and id is an int
        // so we use valueOf() to represent the values as a string
        //holder.userfullName.setText(String.valueOf(u.getFullName()));
        //holder.userpassword.setText(String.valueOf(u.getPassword()));
        //holder.useremail.setText(String.valueOf(u.getEmail()));
    }

    private void refresh(String username){

        int index = 0;
        while(userArrayList.iterator().hasNext()){
            if(userArrayList.get(index).getUserName().equals(username)){
                userArrayList.remove(index);
                break;
            }
            index++;
        }
        this.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        // return the size of the ArrayList
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for the TextViews
        TextView userName, userfullName, userpassword, useremail, usertype ;
        Button deleteButton;
        CardView parentLayout;
        FirebaseAuth fAuth;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initialize the TextViews
            usertype = itemView.findViewById(R.id.usertype_useritem);
            userName = itemView.findViewById(R.id.username_useritem);
            userfullName = itemView.findViewById(R.id.usertype_fullname);
            parentLayout = itemView.findViewById(R.id.cardView_userlist);
            deleteButton = itemView.findViewById(R.id.deleteBtn_userlist);
            fAuth = FirebaseAuth.getInstance();

            DBHandlerUsers dbUsers = new DBHandlerUsers();
            Log.i("test", "user course adapter " + fAuth.getCurrentUser().getEmail().split("@")[0]);

            dbUsers.findUser(fAuth.getCurrentUser().getEmail().split("@")[0], new FirebaseCallBackUsers() {
                @Override
                public void onCallBackUsersList(ArrayList<User> userList) { }

                @Override
                public void onCallBackUser(User user) {

                    if (user.getUserType() == UserType.INSTRUCTOR){
                        deleteButton.setVisibility(View.GONE);
                    }
                    if (user.getUserType() == UserType.STUDENT){
                        deleteButton.setVisibility(View.GONE);
                    }

                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    builder.setTitle("Are you sure you want to delete this user?").setMessage("This will permanently delete the user");
                    builder.setPositiveButton("Yes", (dialog, which) -> {

                        DBHandlerUsers dbUsers = new DBHandlerUsers();
                        dbUsers.deleteUser(userName.getText().toString());
                        refresh(userName.getText().toString());

                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();


                }
            });


        }
    }
}

