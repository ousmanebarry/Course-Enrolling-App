package com.example.coursebookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursebookingapp.classes.Course;

import java.util.ArrayList;

public class StudentAllCourseRecyclerViewAdapter extends RecyclerView.Adapter<StudentAllCourseRecyclerViewAdapter.MyViewHolder>{
    private final StudentAllRecyclerViewInterface studentAllRecyclerViewInterface;

    Context context;
    ArrayList<Course> courseModel;

    public StudentAllCourseRecyclerViewAdapter(Context context, ArrayList<Course> courseModel, StudentAllRecyclerViewInterface studentAllRecyclerViewInterface) {
        this.context = context;
        this.courseModel = courseModel;
        this.studentAllRecyclerViewInterface = studentAllRecyclerViewInterface;
    }

    @NonNull
    @Override
    public StudentAllCourseRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_instructor_all_view_row, parent, false);
        return new StudentAllCourseRecyclerViewAdapter.MyViewHolder(view, courseModel, studentAllRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAllCourseRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.courseCode.setText(courseModel.get(position).getCode());
        holder.courseName.setText(courseModel.get(position).getName());
    }

    @Override
    public int getItemCount() { return courseModel.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseCode, courseName;
        Button viewButton, enrollButton;

        public MyViewHolder(@NonNull View itemView, ArrayList<Course> courseModel, StudentAllRecyclerViewInterface studentAllRecyclerViewInterface) {
            super(itemView);

            courseCode = itemView.findViewById(R.id.courseCode);
            courseName  = itemView.findViewById(R.id.courseName);

            viewButton = itemView.findViewById(R.id.viewCourse);
            enrollButton = itemView.findViewById(R.id.teachCourse);


            viewButton.setOnClickListener(view -> {
                if (studentAllRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        studentAllRecyclerViewInterface.onViewClick(pos);
                    }
                }

            });

            enrollButton.setOnClickListener(view -> {
                if (studentAllRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        studentAllRecyclerViewInterface.onEnrollClick(pos);
                    }
                }

            });
        }
    }
}
