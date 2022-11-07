package com.example.coursebookingapp.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class Store {

    private FirebaseFirestore fstore;

    public Store() {
        fstore = FirebaseFirestore.getInstance();
    }


}
