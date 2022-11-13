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

public class AdminCourseRecyclerViewAdapter extends RecyclerView.Adapter<AdminCourseRecyclerViewAdapter.MyViewHolder> {
    private final AdminRecyclerViewInterface adminRecyclerViewInterface;

    Context context;
    ArrayList<Course> courseModel;

    public AdminCourseRecyclerViewAdapter(Context context, ArrayList<Course> courseModel, AdminRecyclerViewInterface adminRecyclerViewInterface) {
        this.context = context;
        this.courseModel = courseModel;
        this.adminRecyclerViewInterface = adminRecyclerViewInterface;
    }

    @NonNull
    @Override
    public AdminCourseRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_view_row, parent, false);
        return new AdminCourseRecyclerViewAdapter.MyViewHolder(view, courseModel, adminRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCourseRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.courseCode.setText(courseModel.get(position).getCode());
        holder.courseName.setText(courseModel.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return courseModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseCode, courseName;
        Button editButton, deleteButton;

        public MyViewHolder(@NonNull View itemView, ArrayList<Course> courseModel, AdminRecyclerViewInterface adminRecyclerViewInterface) {
            super(itemView);
            courseCode = itemView.findViewById(R.id.courseCode);
            courseName  = itemView.findViewById(R.id.courseName);
            editButton = itemView.findViewById(R.id.editCourse);
            deleteButton = itemView.findViewById(R.id.deleteCourse);

            editButton.setOnClickListener(view -> {
                if (adminRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        adminRecyclerViewInterface.onEditClick(pos);
                    }
                }

            });

            deleteButton.setOnClickListener(view -> {
                if (adminRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        adminRecyclerViewInterface.onDeleteClick(pos);
                    }
                }

            });


        }
    }
}
