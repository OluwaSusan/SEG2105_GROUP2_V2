package com.example.coursebookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursebookingapp.Course;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // called by RecyclerView to display the data at the specified position
    // it updates the contents of the recycler view item to reflect the specific product
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User u = userArrayList.get(position);

        // we get the product name using our getter from Product.java
        // then we set the text in the corresponding TextView element in our layout
        // process repeated for product price and id
        holder.userName.setText(String.valueOf(u.getUserName()));
        // we display data as text using setText() but price is a double and id is an int
        // so we use valueOf() to represent the values as a string
        holder.userfullName.setText(String.valueOf(u.getFullName()));
        holder.userpassword.setText(String.valueOf(u.getPassword()));
        holder.useremail.setText(String.valueOf(u.getEmail()));
    }

    @Override
    public int getItemCount() {
        // return the size of the ArrayList
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for the TextViews
        private TextView userName, userfullName, userpassword, useremail ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initialize the TextViews
            // use findViewById to find the view in our layout with the specified id
//            userName = itemView.findViewById(R.id.idUserName);
//            userfullName = itemView.findViewById(R.id.iduserfullName);
//            userpassword = itemView.findViewById(R.id.idpassword);
//            useremail = itemView.findViewById(R.id.iduseremail;
            //missing elements
        }
    }
}

