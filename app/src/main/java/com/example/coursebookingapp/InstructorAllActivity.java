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
import com.google.firebase.firestore.QuerySnapshot;

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
    private RecyclerView recyclerView;

    private final String CAP_REGEX = "^[0-9]*$";
    private final String HOUR_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private  final String DAY_REGEX = "((Mon|Tue|Wed|Thu|Fri|Sat|Sun)(,|-)?)+";

    TextView teachPickBtn, teachCancelBtn, teachCourseName, teachCourseCode, viewCourseName, viewCourseCode,
            viewCourseDays, viewCourseHours, viewCourseCapacity, viewCourseDesc, viewCancel, viewInstructorName;
    EditText teachCourseDays, teachCourseHours, teachCourseDesc, teachCourseCapacity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_all);

        store = new Store();
        auth = new Auth();
        recyclerView = findViewById(R.id.mRecyclerView);
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
            searchCourses.setQuery("", false);
            searchCourses.clearFocus();
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
        dialogBuilder = new AlertDialog.Builder(this);
        final View viewCoursePopupView = getLayoutInflater().inflate(R.layout.instructor_all_view_course_popup, null);
        String id = courseModels.get(position).getDocID();
        String noData = "Unassigned";

        viewCourseName = viewCoursePopupView.findViewById(R.id.courseName) ;
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


            InstructorAllCourseRecyclerViewAdapter adapter = new InstructorAllCourseRecyclerViewAdapter(this, courseModels, InstructorAllActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}