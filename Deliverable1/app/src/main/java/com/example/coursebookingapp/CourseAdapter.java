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

// adapter is used to get data from the table and then populate the recyclerview
// think of it as the "middle man" that connects the table with the layout view
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    // creating variables
    private ArrayList<Course> courseArrayList;
    private Context context;

    // constructor
    public CourseAdapter(ArrayList<Course> productModalArrayList, Context context) {
        this.courseArrayList = productModalArrayList;
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
        Course c = courseArrayList.get(position);

        // we get the product name using our getter from Product.java
        // then we set the text in the corresponding TextView element in our layout
        // process repeated for product price and id
        holder.courseName.setText(c.getCourseName());
        // we display data as text using setText() but price is a double and id is an int
        // so we use valueOf() to represent the values as a string
        holder.courseId.setText(String.valueOf(c.getCourseCode()));
    }

    @Override
    public int getItemCount() {
        // return the size of the ArrayList
        return courseArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for the TextViews
        private TextView courseName, courseId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initialize the TextViews
            // use findViewById to find the view in our layout with the specified id
            courseName = itemView.findViewById(R.id.idCourseName);
            courseId = itemView.findViewById(R.id.idCourseID);
        }
    }
}

