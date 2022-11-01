package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        Button deleteUserBtn = findViewById(R.id.deleteUserBtn);
        Button deleteUserBackBtn = findViewById(R.id.deleteUserBackBtn);
        EditText emailAddress = findViewById(R.id.emailToDelete);
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailAddress.getText().toString();

                fstore.collection("Users").
                        whereEqualTo("Email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    // Delete user
                                    Toast.makeText(DeleteUserActivity.this,"Deleted user",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DeleteUserActivity.this,"Could not delete user",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

        deleteUserBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}