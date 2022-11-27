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
import android.widget.TextView;

import com.example.coursebookingapp.classes.Course;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class StudentAllActivity extends AppCompatActivity implements StudentAllCourseRecyclerViewAdapter{

    Button backBtn, viewAllBtn;
    ArrayList<Course> courseModels;
    SearchView searchCourses;
    Store store;
    Auth auth;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private RecyclerView recyclerView;

    TextView studentPick, studentCancel,viewCourseName, viewCourseCode,
            viewCourseDays, viewCourseHours, viewCourseCapacity, viewCourseDesc, viewCancel, viewInstructorName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_all);

        store = new Store();
        auth = new Auth();
        recyclerView = findViewById(R.id.mRecyclerView);
        backBtn = findViewById(R.id.backBtn);
        viewAllBtn = findViewById((R.id.viewAllBtn));
        searchCourses = findViewById(R.id.searchCourses);
        searchCourses.clearFocus();

        Objects.requireNonNull(getSupportActionBar()).setTitle("All Courses");

        searchCourses.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadCourses(query);
                Objects.requireNonNull(getSupportActionBar()).setTitle("Custom Courses");
                return false;
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
    public void onViewClick(int position){
        dialogBuilder = new AlertDialog.Builder(this);
        final View viewCoursePopupView = getLayoutInflater().inflate(R.layout.student_all_view_course_popup, null);
        String id = courseModels.get(position).getDocID();
        String noData = "Unassigned";

        viewCourseName = viewCoursePopupView.findViewById(R.id.courseName);
        viewCourseCode = viewCoursePopupView.findViewById(R.id.courseCode);
        viewInstructorName = viewCoursePopupView.findViewById(R.id.instructorName);
        viewCourseDays = viewCoursePopupView.findViewById(R.id.courseDays);
        viewCourseHours = viewCoursePopupView.findViewById(R.id.courseHours);
        viewCourseCapacity = viewCoursePopupView.findViewById(R.id.courseCapacity);
        viewCourseDesc = viewCoursePopupView.findViewById(R.id.courseDesc);
        viewCancel = viewCoursePopupView.findViewById(R.id.viewCancel);

        store.getCourseDocument(id).addOnSuccessListener(snapshot -> {
            viewCourseName.setText(snapshot.get("name") == null ? noData : snapshot.get("name").toString());
            viewCourseCode.setText(snapshot.get("code") == null ? noData : snapshot.get("code").toString());
            viewCourseDays.setText(snapshot.get("days") == null ? noData : snapshot.get("days").toString());
            viewCourseHours.setText(snapshot.get("hours") == null ? noData : snapshot.get("hours").toString());
            viewCourseCapacity.setText(snapshot.get("capacity") == null ? noData : snapshot.get("capacity").toString());
            viewCourseDesc.setText(snapshot.get("description") == null ? noData : snapshot.get("description").toString());

            Boolean hasInstructor = snapshot.get("hasInstructor", Boolean.class);

            if (Boolean.TRUE.equals(hasInstructor)) {
                store.getUserDocument(snapshot.get("instructorId").toString()).addOnSuccessListener(snapshotTwo -> {
                    viewInstructorName.setText(snapshotTwo.get("name").toString());
                });
            } else {
                viewInstructorName.setText(noData);

            }
        });

        dialogBuilder.setView(viewCoursePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        viewCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void loadCourses() {
        courseModels = new ArrayList<>();

        store.getAllCourses().addOnSuccessListener(query -> {
            for (DocumentSnapshot snapshot : query) {
                String docID = Objects.requireNonNull(snapshot.getId());
                String name = Objects.requireNonNull(snapshot.get("name")).toString();
                String code = Objects.requireNonNull(snapshot.get("code")).toString();

                Course instructorCourseModel = new Course(name, code, docID);
                courseModels.add(instructorCourseModel);
            }

            InstructorAllCourseRecyclerViewAdapter adapter = new StudentAllCourseRecyclerViewAdapter(this, courseModels, StudentAllActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });
    }

    //Missing OnTeachClick Section


    private void loadCourses(String courseNameCode) {
        courseModels = new ArrayList<>();

        store.getCoursesByNameOrCode(courseNameCode).addOnSuccessListener(querySnapshots -> {
            for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    String docID = Objects.requireNonNull(snapshot.getId());
                    String name = Objects.requireNonNull(snapshot.get("name")).toString();
                    String code = Objects.requireNonNull(snapshot.get("code")).toString();

                    Course instructorCourseModel = new Course(name, code, docID);

                    courseModels.add(instructorCourseModel);
                }
            }


            StudentAllCourseRecyclerViewAdapter adapter = new InstructorAllCourseRecyclerViewAdapter(this, courseModels, StudentAllActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}