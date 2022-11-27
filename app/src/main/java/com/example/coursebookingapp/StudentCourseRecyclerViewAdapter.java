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

public class StudentCourseRecyclerViewAdapter extends RecyclerView.Adapter<StudentCourseRecyclerViewAdapter.MyViewHolder> {
    private final StudentRecyclerViewInterface studentRecyclerViewInterface;

    Context context;
    ArrayList<Course> courseModel;

    public StudentCourseRecyclerViewAdapter(Context context, ArrayList<Course> courseModel, StudentRecyclerViewInterface studentRecyclerViewInterface) {
        this.context = context;
        this.courseModel = courseModel;
        this.studentRecyclerViewInterface = studentRecyclerViewInterface;
    }

    @NonNull
    @Override
    public StudentCourseRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_instructor_view_row, parent, false);
        return new StudentCourseRecyclerViewAdapter.MyViewHolder(view, courseModel, studentRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCourseRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.courseCode.setText(courseModel.get(position).getCode());
        holder.courseName.setText(courseModel.get(position).getName());
        holder.courseCapacity.setText(courseModel.get(position).getCapacity());
    }

    @Override
    public int getItemCount() { return courseModel.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseCode, courseName, courseCapacity;
        Button viewButton, deleteButton;

        public MyViewHolder(@NonNull View itemView, ArrayList<Course> courseModel, StudentRecyclerViewInterface studentRecyclerViewInterface) {
            super(itemView);

            courseCode = itemView.findViewById(R.id.courseCode);
            courseName  = itemView.findViewById(R.id.courseName);
            courseCapacity = itemView.findViewById(R.id.courseCapacity);

            viewButton = itemView.findViewById(R.id.viewCourse);
            deleteButton = itemView.findViewById(R.id.deleteCourse);

            viewButton.setOnClickListener(view -> {
                if (studentRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        studentRecyclerViewInterface.onViewClick(pos);
                    }
                }

            });

            deleteButton.setOnClickListener(view -> {
                if (studentRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        studentRecyclerViewInterface.onDeleteClick(pos);
                    }
                }

            });
        }
    }
}
