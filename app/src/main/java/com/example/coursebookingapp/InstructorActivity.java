package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursebookingapp.classes.Course;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InstructorActivity extends AppCompatActivity implements InstructorRecyclerViewInterface {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private final String CAP_REGEX = "^[0-9]*$";
    private final String HOUR_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private  final String DAY_REGEX = "((Mon|Tue|Wed|Thu|Fri|Sat|Sun)(,|-)?)+";

    Auth auth;
    Store store;
    TextView welcomeTxt, teachPickBtn, teachCancelBtn,teachEditBtn, deleteText, deleteYesBtn, deleteCancelBtn;
    TextView viewCourseName, viewCourseCode, viewCourseDays, viewCourseHours, viewCourseCapacity, viewCourseDesc, viewCancel;
    EditText teachCourseDays, teachCourseHours, teachCourseDesc, teachCourseCapacity;
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
            ArrayList<String> docIds = new ArrayList<>();

            dialogBuilder = new AlertDialog.Builder(this);
            final View pickCoursePopupView = getLayoutInflater().inflate(R.layout.instructor_teach_course_popup, null);

            teachCourseDays = pickCoursePopupView.findViewById(R.id.courseDays);
            teachCourseHours = pickCoursePopupView.findViewById(R.id.courseHours);
            teachCourseDesc = pickCoursePopupView.findViewById(R.id.courseDesc);
            teachCourseCapacity = pickCoursePopupView.findViewById(R.id.courseCapacity);
            teachPickBtn = pickCoursePopupView.findViewById(R.id.pickBtn);
            teachCancelBtn = pickCoursePopupView.findViewById(R.id.cancelBtn);
            spinner = pickCoursePopupView.findViewById(R.id.spinner);

            store.getUnassignedCourses().addOnSuccessListener(query -> {
                for (DocumentSnapshot snapshot : query) {
                    courses.add(Objects.requireNonNull(snapshot.get("code")).toString());
                    docIds.add(snapshot.getId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, courses);
                spinner.setAdapter(adapter);
            });

            dialogBuilder.setView(pickCoursePopupView);
            dialog = dialogBuilder.create();
            dialog.show();

            teachPickBtn.setOnClickListener(v -> {
                // change hasInstructor to true
                // set instructorId

                String instructorId = auth.getCurrentUser().getUid();
                String docId = docIds.get(spinner.getSelectedItemPosition());
                String capacity = teachCourseCapacity.getText().toString();
                String desc = teachCourseDesc.getText().toString();
                String hours = teachCourseHours.getText().toString();
                String days = teachCourseDays.getText().toString();

                if(!days.matches(DAY_REGEX)){
                    Toast.makeText(InstructorActivity.this,"Invalid Days, enter days in three-letter format)",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!hours.matches(HOUR_REGEX)){
                    Toast.makeText(InstructorActivity.this,"Invalid Hours, enter range (HH:mm-HH:mm)",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!capacity.matches(CAP_REGEX)){
                    Toast.makeText(InstructorActivity.this,"Invalid Capacity Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                store.assignTeacher(docId, capacity, desc, hours, days, instructorId);

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
        dialogBuilder = new AlertDialog.Builder(this);
        final View viewCoursePopupView = getLayoutInflater().inflate(R.layout.instructor_view_course_popup, null);
        String id = courseModels.get(position).getDocID();

        viewCourseName = viewCoursePopupView.findViewById(R.id.courseName) ;
        viewCourseCode = viewCoursePopupView.findViewById(R.id.courseCode);
        viewCourseDays = viewCoursePopupView.findViewById(R.id.courseDays);
        viewCourseHours = viewCoursePopupView.findViewById(R.id.courseHours);
        viewCourseCapacity = viewCoursePopupView.findViewById(R.id.courseCapacity);
        viewCourseDesc = viewCoursePopupView.findViewById(R.id.courseDesc);
        viewCancel = viewCoursePopupView.findViewById(R.id.viewCancel);

        store.getCourseDocument(id).addOnSuccessListener(snapshot -> {
           viewCourseName.setText(Objects.requireNonNull(snapshot.get("name")).toString());
           viewCourseCode.setText(Objects.requireNonNull(snapshot.get("code")).toString());
           viewCourseDays.setText(Objects.requireNonNull(snapshot.get("days")).toString());
           viewCourseHours.setText(Objects.requireNonNull(snapshot.get("hours")).toString());
           viewCourseCapacity.setText(Objects.requireNonNull(snapshot.get("capacity")).toString());
           viewCourseDesc.setText(Objects.requireNonNull(snapshot.get("description")).toString());
        });


        dialogBuilder.setView(viewCoursePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        viewCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }

    @Override
    public void onEditClick(int position) {
        try {
            // logic to edit the course information
            // day, hours, course description, capacity
            Course c = courseModels.get(position);
            store.getCourseDocument(c.getDocID()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot doc) {
                    dialogBuilder = new AlertDialog.Builder(InstructorActivity.this);
                    final View pickCoursePopupView = getLayoutInflater().inflate(R.layout.instructor_edit_course_popup, null);



                    ((TextView) pickCoursePopupView.findViewById(R.id.instr_edit_code_title)).setText(c.getCode());
                    ((TextView) pickCoursePopupView.findViewById(R.id.instr_edit_name_title)).setText(c.getName());

                    teachCourseDays = pickCoursePopupView.findViewById(R.id.instr_edit_days);
                    teachCourseHours = pickCoursePopupView.findViewById(R.id.instr_edit_hours);
                    teachCourseDesc = pickCoursePopupView.findViewById(R.id.instr_edit_description);
                    teachCourseCapacity = pickCoursePopupView.findViewById(R.id.instr_edit_capacity);
                    teachEditBtn = pickCoursePopupView.findViewById(R.id.instr_edit_EDIT_btn);
                    teachCancelBtn = pickCoursePopupView.findViewById(R.id.instr_edit_cancel_btn);

                    teachCourseDays.setText(doc.get("days").toString());
                    teachCourseHours.setText(doc.get("hours").toString());
                    teachCourseDesc.setText(doc.get("description").toString());
                    teachCourseCapacity.setText(doc.get("capacity").toString());

                    dialogBuilder.setView(pickCoursePopupView);
                    dialog = dialogBuilder.create();
                    dialog.show();

                    teachEditBtn.setOnClickListener(view -> {
                        HashMap<String, Object> data = new HashMap<>();
                        //sure only days are entered
                        if(!teachCourseDays.getText().toString().matches(DAY_REGEX)){
                            Toast.makeText(InstructorActivity.this,"Invalid Days, enter days in three-letter format)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        data.put("days", teachCourseDays.getText().toString());

                        //ensure only time is entered
                        if(!teachCourseHours.getText().toString().matches(HOUR_REGEX)){
                            Toast.makeText(InstructorActivity.this,"Invalid Hours, enter range (HH:mm-HH:mm)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        data.put("hours", teachCourseHours.getText().toString());


                        data.put("description", teachCourseDesc.getText().toString());

                        //check capacity
                        if(!teachCourseCapacity.getText().toString().matches(CAP_REGEX)){
                            Toast.makeText(InstructorActivity.this,"Invalid Capacity Number",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        data.put("capacity", teachCourseCapacity.getText().toString());
                        store.updateCourse(c.getDocID(), data);

                        dialog.dismiss();
                        loadCourses();
                    });

                    teachCancelBtn.setOnClickListener(view -> {
                        dialog.dismiss();
                        loadCourses();
                    });
                }
            });


        }catch(Exception e){

        }
    }

    @Override
    public void onDeleteClick(int position) {
        // logic to unassign a course from the currently signed in instructor
        // remove day, hours, set hasInstructor back to false, description, capacity, instructorId

        dialogBuilder = new AlertDialog.Builder(this);
        final View deleteCoursePopupView = getLayoutInflater().inflate(R.layout.instructor_delete_course_popup, null);
        String id = courseModels.get(position).getDocID();

        deleteText = deleteCoursePopupView.findViewById(R.id.deleteText);
        deleteYesBtn = deleteCoursePopupView.findViewById(R.id.yesBtn);
        deleteCancelBtn = deleteCoursePopupView.findViewById(R.id.cancelBtn);

        deleteText.setText(getString(R.string.course_unassign_confirmation_instructor, courseModels.get(position).getCode()));

        dialogBuilder.setView(deleteCoursePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        deleteYesBtn.setOnClickListener(view -> {
            String name = courseModels.get(position).getName();
            String code = courseModels.get(position).getCode();
            Course course = new Course(name, code);

            store.unassignCourse(id, course);
            dialog.dismiss();
            loadCourses();
        });

        deleteCancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });

        loadCourses();

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
