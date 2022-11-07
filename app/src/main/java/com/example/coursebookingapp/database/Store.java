package com.example.coursebookingapp.database;

import com.example.coursebookingapp.classes.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Store {

    private FirebaseFirestore store;

    public Store() {
        store = FirebaseFirestore.getInstance();
    }

    public void addUser(User user, String uuid) {
        DocumentReference df = store.collection("Users").document(uuid);
        df.set(user.getMap());
    }


}
