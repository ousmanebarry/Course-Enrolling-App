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

public class InstructorAllCourseRecyclerViewAdapter extends RecyclerView.Adapter<InstructorAllCourseRecyclerViewAdapter.MyViewHolder>{
    private final InstructorAllRecyclerViewInterface instructorAllRecyclerViewInterface;

    Context context;
    ArrayList<Course> courseModel;

    public InstructorAllCourseRecyclerViewAdapter(Context context, ArrayList<Course> courseModel, InstructorAllRecyclerViewInterface instructorAllRecyclerViewInterface) {
        this.context = context;
        this.courseModel = courseModel;
        this.instructorAllRecyclerViewInterface = instructorAllRecyclerViewInterface;
    }

    @NonNull
    @Override
    public InstructorAllCourseRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_instructor_all_view_row, parent, false);
        return new InstructorAllCourseRecyclerViewAdapter.MyViewHolder(view, courseModel, instructorAllRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorAllCourseRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.courseCode.setText(courseModel.get(position).getCode());
        holder.courseName.setText(courseModel.get(position).getName());
    }

    @Override
    public int getItemCount() { return courseModel.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseCode, courseName;
        Button viewButton, teachButton;

        public MyViewHolder(@NonNull View itemView, ArrayList<Course> courseModel, InstructorAllRecyclerViewInterface instructorAllRecyclerViewInterface) {
            super(itemView);

            courseCode = itemView.findViewById(R.id.courseCode);
            courseName  = itemView.findViewById(R.id.courseName);

            viewButton = itemView.findViewById(R.id.viewCourse);
            teachButton = itemView.findViewById(R.id.teachCourse);


            viewButton.setOnClickListener(view -> {
                if (instructorAllRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        instructorAllRecyclerViewInterface.onViewClick(pos);
                    }
                }

            });

            teachButton.setOnClickListener(view -> {
                if (instructorAllRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        instructorAllRecyclerViewInterface.onTeachClick(pos);
                    }
                }

            });
        }
    }
}
