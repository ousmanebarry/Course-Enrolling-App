package com.example.coursebookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursebookingapp.classes.Course;

import java.util.ArrayList;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Course> adminCourseModels;

    public CourseRecyclerViewAdapter(Context context, ArrayList<Course> adminCourseModels) {
        this.context = context;
        this.adminCourseModels = adminCourseModels;
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_view_row, parent, false);
        return new CourseRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.courseCode.setText(adminCourseModels.get(position).getCode());
        holder.courseName.setText(adminCourseModels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return adminCourseModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseCode, courseName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            courseCode = itemView.findViewById(R.id.courseCode);
            courseName  = itemView.findViewById(R.id.courseName);
        }
    }
}