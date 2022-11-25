package com.example.coursebookingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursebookingapp.classes.Course;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class InstructorAllActivity extends AppCompatActivity implements InstructorAllRecyclerViewInterface {

    Button backBtn, viewAllBtn;
    ArrayList<Course> courseModels;
    SearchView searchCourses;
    Store store;
    Auth auth;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private final String CAP_REGEX = "^[0-9]*$";
    private final String HOUR_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private  final String DAY_REGEX = "((Mon|Tue|Wed|Thu|Fri|Sat|Sun)(,|-)?)+";

    TextView teachPickBtn, teachCancelBtn, teachCourseName, teachCourseCode;
    EditText teachCourseDays, teachCourseHours, teachCourseDesc, teachCourseCapacity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_all);

        store = new Store();
        auth = new Auth();
        backBtn = findViewById(R.id.backBtn);
        viewAllBtn = findViewById(R.id.viewAllBtn);
        searchCourses = findViewById(R.id.searchCourses);
        searchCourses.clearFocus();

        Objects.requireNonNull(getSupportActionBar()).setTitle("All Courses");

        searchCourses.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadCourses(query);
                Objects.requireNonNull(getSupportActionBar()).setTitle("Custom Courses");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewAllBtn.setOnClickListener(view -> {
            loadCourses();
            Objects.requireNonNull(getSupportActionBar()).setTitle("All Courses");
        });

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });

        loadCourses();
    }

    @Override
    public void onViewClick(int position) {

    }

    @Override
    public void onTeachClick(int position) {

        store.getCourseDocument(courseModels.get(position).getDocID()).addOnSuccessListener(snapshot -> {
            Boolean hasInstructor = snapshot.get("hasInstructor", Boolean.class);

           if (Boolean.TRUE.equals(hasInstructor)) {
               Toast.makeText(InstructorAllActivity.this,"Course already has an instructor",Toast.LENGTH_SHORT).show();
               return;
           }

            dialogBuilder = new AlertDialog.Builder(this);
            final View pickCoursePopupView = getLayoutInflater().inflate(R.layout.instructor_teach_course_popup, null);

            teachCourseDays = pickCoursePopupView.findViewById(R.id.courseDays);
            teachCourseHours = pickCoursePopupView.findViewById(R.id.courseHours);
            teachCourseDesc = pickCoursePopupView.findViewById(R.id.courseDesc);
            teachCourseCapacity = pickCoursePopupView.findViewById(R.id.courseCapacity);
            teachCourseName = pickCoursePopupView.findViewById(R.id.instr_edit_name_title);
            teachCourseCode = pickCoursePopupView.findViewById(R.id.instr_edit_code_title);
            teachPickBtn = pickCoursePopupView.findViewById(R.id.pickBtn);
            teachCancelBtn = pickCoursePopupView.findViewById(R.id.cancelBtn);

            teachCourseCode.setText(courseModels.get(position).getCode());
            teachCourseName.setText(courseModels.get(position).getName());

            dialogBuilder.setView(pickCoursePopupView);
            dialog = dialogBuilder.create();
            dialog.show();

            teachPickBtn.setOnClickListener(v -> {

                String instructorId = auth.getCurrentUser().getUid();
                String docId = courseModels.get(position).getDocID();
                String capacity = teachCourseCapacity.getText().toString();
                String desc = teachCourseDesc.getText().toString();
                String hours = teachCourseHours.getText().toString();
                String days = teachCourseDays.getText().toString();

                if(!days.matches(DAY_REGEX)){
                    Toast.makeText(InstructorAllActivity.this,"Invalid Days, enter days in three-letter format)",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!hours.matches(HOUR_REGEX)){
                    Toast.makeText(InstructorAllActivity.this,"Invalid Hours, enter range (HH:mm-HH:mm)",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!capacity.matches(CAP_REGEX)){
                    Toast.makeText(InstructorAllActivity.this,"Invalid Capacity Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                store.assignTeacher(docId, capacity, desc, hours, days, instructorId);

                dialog.dismiss();

                String code = courseModels.get(position).getCode();

                Toast.makeText(InstructorAllActivity.this,code + " has been added",Toast.LENGTH_SHORT).show();
            });

            teachCancelBtn.setOnClickListener(v -> {
                dialog.dismiss();
            });
        });

    }

    private void loadCourses() {
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        courseModels = new ArrayList<>();

        store.getAllCourses().addOnSuccessListener(query -> {
            for (DocumentSnapshot snapshot : query) {
                String docID = Objects.requireNonNull(snapshot.getId());
                String name = Objects.requireNonNull(snapshot.get("name")).toString();
                String code = Objects.requireNonNull(snapshot.get("code")).toString();

                Course instructorCourseModel = new Course(name, code, docID);
                courseModels.add(instructorCourseModel);
            }

            InstructorAllCourseRecyclerViewAdapter adapter = new InstructorAllCourseRecyclerViewAdapter(this, courseModels, InstructorAllActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });
    }

    private void loadCourses(String courseNameCode) {
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        courseModels = new ArrayList<>();

        store.getCoursesByNameOrCode(courseNameCode).addOnSuccessListener(query -> {
            for (DocumentSnapshot snapshot : query) {
                String docID = Objects.requireNonNull(snapshot.getId());
                String name = Objects.requireNonNull(snapshot.get("name")).toString();
                String code = Objects.requireNonNull(snapshot.get("code")).toString();

                Course instructorCourseModel = new Course(name, code, docID);
                courseModels.add(instructorCourseModel);
            }

            InstructorAllCourseRecyclerViewAdapter adapter = new InstructorAllCourseRecyclerViewAdapter(this, courseModels, InstructorAllActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}