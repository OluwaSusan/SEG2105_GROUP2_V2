package com.example.coursebookingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

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

    private void refresh(String courseID){

        int index = 0;
        while(courses.iterator().hasNext()){
            if(courses.get(index).getCourseCode().equals(courseID)){
                courses.remove(index);
                break;
            }
            index++;
        }
        this.notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return courses.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView courseID;
        TextView courseName;
        CardView parentLayout;
        Button deleteButton;
        Button editButton;
        Button expandCourse;
        FirebaseAuth fAuth;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            courseID =  itemView.findViewById(R.id.idCourseID);
            courseName = itemView.findViewById(R.id.idCourseName);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            expandCourse = itemView.findViewById(R.id.openCourse_btn);

       /*     DBHandlerUsers dbUsers = new DBHandlerUsers();
            dbUsers.findUser(fAuth.getCurrentUser().getEmail().split("@")[0], new FirebaseCallBackUsers() {
                @Override
                public void onCallBackUsersList(ArrayList<User> userList) { }

                @Override
                public void onCallBackUser(User user) {

                    if (user.getUserType() == UserType.INSTRUCTOR){
                        deleteButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.INVISIBLE);
                    }

                }
            });*/

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    builder.setTitle("Are you sure you want to delete this course?").setMessage("This will permanently delete the course");
                    builder.setPositiveButton("Yes", (dialog, which) -> {

                        DBHandlerCourses dbCourses = new DBHandlerCourses();
                        dbCourses.deleteCourse(courseID.getText().toString());
                        refresh(courseID.getText().toString());

                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                }
            });
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context.getApplicationContext(), CourseActivity.class);
                    i.putExtra("Course_ID", courseID.getText().toString());
                    context.startActivity(i);
                }
            });

            expandCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), CoursePage.class);
                    i.putExtra("Course_ID", courseID.getText().toString());
                    context.startActivity(i);
                }
            });
        }

    }
}