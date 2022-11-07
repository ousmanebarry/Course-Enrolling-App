package com.example.coursebookingapp.database;

import com.example.coursebookingapp.classes.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Store {

    private final String USER_PATH;
    private final String COURSE_PATH;

    private FirebaseFirestore store;

    public Store() {
        store = FirebaseFirestore.getInstance();
        USER_PATH = "user";
        COURSE_PATH = "course";
    }

    public void addUser(User user, String uuid) {
        DocumentReference df = store.collection("user").document(uuid);
        df.set(user.getMap());
    }

    public Task<DocumentSnapshot> getUserDocument(String uuid) {
        return store.collection("user").document(uuid).get();
    }


}
