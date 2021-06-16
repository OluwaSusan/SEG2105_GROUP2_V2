package com.example.coursebookingapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Course> courses;


    public CourseAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @NotNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CourseAdapter.ViewHolder holder, int position) {

        holder.courseName.setText(courses.get(position).getCourseName());
        holder.courseID.setText(courses.get(position).getCourseCode());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView courseID;
        TextView courseName;
        CardView parentLayout;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            courseID =  itemView.findViewById(R.id.idCourseID);
            courseName = itemView.findViewById(R.id.idCourseName);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }

    }
}