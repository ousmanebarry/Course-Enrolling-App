package com.example.coursebookingapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.coursebookingapp.classes.Course;
import com.example.coursebookingapp.database.Auth;
import com.example.coursebookingapp.database.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StudentActivity extends AppCompatActivity implements StudentRecyclerViewInterface {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    Auth auth;
    Store store;
    TextView deleteText, deleteYesBtn, deleteCancelBtn;
    TextView viewCourseName, viewCourseCode, viewCourseDays, viewCourseHours, viewCourseCapacity, viewCourseDesc, viewCancel;
    Button logoutBtn, enrollBtn;
    ArrayList<Course> courseModels = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("user");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        auth = new Auth();
        store = new Store();
        String uuid = auth.getCurrentUser().getUid();

        logoutBtn = findViewById(R.id.logOutBtn);
        enrollBtn = findViewById(R.id.enrollCourse);
        ActivityResultLauncher<Intent> studentCourseActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onActivityResult);

        store.getUserDocument(uuid).addOnSuccessListener(documentSnapshot -> {
            String welcome = String.format("Welcome, %s! (%s)", documentSnapshot.get("name"), documentSnapshot.get("accountType"));
            Objects.requireNonNull(getSupportActionBar()).setTitle(welcome);
        });

        enrollBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), StudentAllActivity.class);
            studentCourseActivityResultLauncher.launch(intent);
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

        dialogBuilder = new AlertDialog.Builder(this);
        final View viewCoursePopupView = getLayoutInflater().inflate(R.layout.student_view_course_popup, null);
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
    public void onDeleteClick(int position){
        dialogBuilder = new AlertDialog.Builder(this);
        final View deleteCoursePopupView = getLayoutInflater().inflate(R.layout.instructor_delete_course_popup, null);
        String id = courseModels.get(position).getDocID();

        deleteText = deleteCoursePopupView.findViewById(R.id.deleteText);
        deleteYesBtn = deleteCoursePopupView.findViewById(R.id.yesBtn);
        deleteCancelBtn = deleteCoursePopupView.findViewById(R.id.cancelBtn);

        deleteText.setText(getString(R.string.course_unenroll_confirmation_student, courseModels.get(position).getCode()));

        dialogBuilder.setView(deleteCoursePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        deleteYesBtn.setOnClickListener(view -> {
            String name = courseModels.get(position).getName();
            String code = courseModels.get(position).getCode();

            dialog.dismiss();
            deleteCourse(courseModels.get(position).toString());
            loadCourses();
        });

        deleteCancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });

        loadCourses();
    }

    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            loadCourses();
        }
    }

    private void loadCourses() {
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        courseModels = new ArrayList<>();

        store.getUserDocument(auth.getCurrentUser().getUid()).addOnSuccessListener(snapshot -> {

            ArrayList<String> course  = (ArrayList<String>) snapshot.get("course");

            assert course != null;
            store.getStudentCourses(course).addOnSuccessListener(querySnapshots -> {
                for (DocumentSnapshot queryDocumentSnapshots : querySnapshots) {
                    String docID = Objects.requireNonNull(queryDocumentSnapshots.getId());
                    String name = Objects.requireNonNull(queryDocumentSnapshots.get("name")).toString();
                    String code = Objects.requireNonNull(queryDocumentSnapshots.get("code")).toString();

                    Course instructorCourseModel = new Course(name, code, docID);

                    courseModels.add(instructorCourseModel);
                }

                StudentCourseRecyclerViewAdapter adapter = new StudentCourseRecyclerViewAdapter(this, courseModels, StudentActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            });


        });

    };

    private void updateScreen() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void deleteCourse(String position){
        String uuid = auth.getCurrentUser().getUid();
        userRef.document(uuid).update("course", FieldValue.arrayRemove(position));
    }

}
