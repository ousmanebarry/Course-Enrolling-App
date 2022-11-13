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

public class InstructorCourseRecyclerViewAdapter extends RecyclerView.Adapter<InstructorCourseRecyclerViewAdapter.MyViewHolder> {
    private final InstructorRecyclerViewInterface instructorRecyclerViewInterface;

    Context context;
    ArrayList<Course> courseModel;

    public InstructorCourseRecyclerViewAdapter(Context context, ArrayList<Course> courseModel, InstructorRecyclerViewInterface instructorRecyclerViewInterface) {
        this.context = context;
        this.courseModel = courseModel;
        this.instructorRecyclerViewInterface = instructorRecyclerViewInterface;
    }

    @NonNull
    @Override
    public InstructorCourseRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_instructor_view_row, parent, false);
        return new InstructorCourseRecyclerViewAdapter.MyViewHolder(view, courseModel, instructorRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorCourseRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.courseCode.setText(courseModel.get(position).getCode());
        holder.courseName.setText(courseModel.get(position).getName());
        holder.courseCapacity.setText(courseModel.get(position).getCapacity());
    }

    @Override
    public int getItemCount() { return courseModel.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseCode, courseName, courseCapacity;
        Button viewButton, editButton, deleteButton;

        public MyViewHolder(@NonNull View itemView, ArrayList<Course> courseModel, InstructorRecyclerViewInterface instructorRecyclerViewInterface) {
            super(itemView);

            courseCode = itemView.findViewById(R.id.courseCode);
            courseName  = itemView.findViewById(R.id.courseName);
            courseCapacity = itemView.findViewById(R.id.courseCapacity);
            viewButton = itemView.findViewById(R.id.viewCourse);
            editButton = itemView.findViewById(R.id.editCourse);
            deleteButton = itemView.findViewById(R.id.deleteCourse);

            viewButton.setOnClickListener(view -> {
                if (instructorRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        instructorRecyclerViewInterface.onViewClick(pos);
                    }
                }

            });

            editButton.setOnClickListener(view -> {
                if (instructorRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        instructorRecyclerViewInterface.onEditClick(pos);
                    }
                }

            });

            deleteButton.setOnClickListener(view -> {
                if (instructorRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        instructorRecyclerViewInterface.onDeleteClick(pos);
                    }
                }

            });
        }
    }
}
