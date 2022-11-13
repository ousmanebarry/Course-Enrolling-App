package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AdminActivity extends AppCompatActivity implements RecyclerViewInterface {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    Auth auth;
    Store store;
    TextView welcomeTxt, addSaveBtn, addCancelBtn;
    EditText addCourseName, addCourseCode, editCourseName, editCourseCode, deleteEmail;
    Button logoutBtn, addCourseBtn, deleteUserBtn;
    ArrayList<Course> courseModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        auth = new Auth();
        store = new Store();
        String uuid = auth.getCurrentUser().getUid();

        welcomeTxt = findViewById(R.id.welcomeTxt);
        logoutBtn = findViewById(R.id.logOutBtn);
        addCourseBtn = findViewById(R.id.addCourse);
        deleteUserBtn = findViewById(R.id.deleteUser);

        store.getUserDocument(uuid).addOnSuccessListener(documentSnapshot -> {
            welcomeTxt.setText(String.format("Welcome, %s! (%s)", documentSnapshot.get("name"), documentSnapshot.get("accountType")));
        });

        addCourseBtn.setOnClickListener(view -> {
            createAddCourseDialog();
        });

        deleteUserBtn.setOnClickListener(view -> {

        });

        logoutBtn.setOnClickListener(view -> {
            auth.signOut();
            updateScreen(LoginActivity.class);
            finish();
        });

        loadCourses();
    }

    public void createAddCourseDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View addCoursePopupView = getLayoutInflater().inflate(R.layout.admin_add_course_popup, null);

        addCourseName = addCoursePopupView.findViewById(R.id.courseName);
        addCourseCode = addCoursePopupView.findViewById(R.id.courseCode);
        addSaveBtn = addCoursePopupView.findViewById(R.id.addBtn);
        addCancelBtn = addCoursePopupView.findViewById(R.id.cancelBtn);

        dialogBuilder.setView(addCoursePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        addSaveBtn.setOnClickListener(view -> {});

        addCancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });
    }

    @Override
    public void onEditClick(int position) {
        String code = courseModels.get(position).getCode();
        String name = courseModels.get(position).getName();
        String docID = courseModels.get(position).getDocID();
        System.out.println(code);
        System.out.println(name);
        System.out.println(docID);
    }

    @Override
    public void onDeleteClick(int position) {
        String docID = courseModels.get(position).getDocID();
        store.deleteCourse(docID);
        loadCourses();
   }

    private void loadCourses() {
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        courseModels = new ArrayList<>();

        store.getAllCourses().addOnSuccessListener(query -> {

            for (DocumentSnapshot snapshot : query) {
                String docID = Objects.requireNonNull(snapshot.getId());
                String name = Objects.requireNonNull(snapshot.get("name")).toString();
                String code = Objects.requireNonNull(snapshot.get("code")).toString();
                Course adminCourseModel = new Course(name, code, docID);
                courseModels.add(adminCourseModel);
            }

            CourseRecyclerViewAdapter adapter = new CourseRecyclerViewAdapter(this, courseModels, AdminActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        });
    }

    private void updateScreen(Class<?> next) {
        Intent intent = new Intent(getApplicationContext(), next);
        startActivity(intent);
    }
}