package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursebookingapp.classes.Course;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class InstructorActivity extends AppCompatActivity implements InstructorRecyclerViewInterface {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    Auth auth;
    Store store;
    TextView welcomeTxt, teachPickBtn, teachCancelBtn;
    Spinner spinner;
    Button logoutBtn, teachBtn;
    ArrayList<Course> courseModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        auth = new Auth();
        store = new Store();
        String uuid = auth.getCurrentUser().getUid();

        welcomeTxt = findViewById(R.id.welcomeTxt);
        logoutBtn = findViewById(R.id.logOutBtn);
        teachBtn = findViewById(R.id.teachCourse);

        store.getUserDocument(uuid).addOnSuccessListener(documentSnapshot -> {
            welcomeTxt.setText(String.format("Welcome, %s! (%s)", documentSnapshot.get("name"), documentSnapshot.get("accountType")));
        });

        teachBtn.setOnClickListener(view -> {
            // logic to assign a course to instructor by searching all the available courses that
            // are not already assigned to an instructor
            ArrayList<String> courses = new ArrayList<>();
            dialogBuilder = new AlertDialog.Builder(this);
            final View pickCoursePopupView = getLayoutInflater().inflate(R.layout.instructor_teach_course_popup, null);

            teachPickBtn = pickCoursePopupView.findViewById(R.id.pickBtn);
            teachCancelBtn = pickCoursePopupView.findViewById(R.id.cancelBtn);
            spinner = pickCoursePopupView.findViewById(R.id.spinner);

            store.getUnassignedCourses().addOnSuccessListener(query -> {
                for (DocumentSnapshot snapshot : query) {
                    courses.add(snapshot.get("code").toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, courses);
                spinner.setAdapter(adapter);
            });

            dialogBuilder.setView(pickCoursePopupView);
            dialog = dialogBuilder.create();
            dialog.show();

            teachPickBtn.setOnClickListener(v -> {
                String courseCode = spinner.getSelectedItem().toString();
                System.out.println();
                dialog.dismiss();
                loadCourses();
            });

            teachCancelBtn.setOnClickListener(v -> {
                dialog.dismiss();
            });

        });

        logoutBtn.setOnClickListener(view -> {
            auth.signOut();
            updateScreen();
            finish();
        });

        loadCourses();
    }

    @Override
    public void onViewClick(int position) {
        // logic to show the course information
        // day, hours, course code, course name, course description
    }

    @Override
    public void onEditClick(int position) {
        // logic to edit the course information
        // day, hours, course description, capacity
    }

    @Override
    public void onDeleteClick(int position) {
        // logic to unassign a course from the currently signed in instructor
        // remove day, hours, hasInstructor == false, description, capacity, instructorId

    }

    private void loadCourses() {
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        courseModels = new ArrayList<>();

        store.getInstructorCourses(auth.getCurrentUser().getUid()).addOnSuccessListener(query -> {
            for (DocumentSnapshot snapshot : query) {
                String docID = Objects.requireNonNull(snapshot.getId());
                String name = Objects.requireNonNull(snapshot.get("name")).toString();
                String code = Objects.requireNonNull(snapshot.get("code")).toString();
                String capacity = Objects.requireNonNull(snapshot.get("capacity")).toString();
                Course adminCourseModel = new Course(name, code, docID, capacity);
                courseModels.add(adminCourseModel);
            }

            InstructorCourseRecyclerViewAdapter adapter = new InstructorCourseRecyclerViewAdapter(this, courseModels, InstructorActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });


    }

    private void updateScreen() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


}
