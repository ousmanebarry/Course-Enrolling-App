package com.example.coursebookingapp.database;

import com.example.coursebookingapp.classes.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Store {

    private final String USER_PATH;
    private final String COURSE_PATH;
    private final FirebaseFirestore store;

    public Store() {
        USER_PATH = "user";
        COURSE_PATH = "course";
        store = FirebaseFirestore.getInstance();
    }

    public void addUser(User user, String uuid) {
        DocumentReference df = store.collection(USER_PATH).document(uuid);
        df.set(user.getMap());
    }

    public Task<DocumentSnapshot> getUserDocument(String uuid) {
        return store.collection(USER_PATH).document(uuid).get();
    }

    public Task<DocumentSnapshot> getCourseDocument(String uuid) {
        return store.collection(COURSE_PATH).document(uuid).get();
    }

    public Task<QuerySnapshot> getAllCourses() {
        return store.collection(COURSE_PATH).get();
    }

    public Task<QuerySnapshot> getInstructorCourses(String uuid) {
        CollectionReference col = store.collection(COURSE_PATH);
        return col.whereEqualTo("hasInstructor", true).whereEqualTo("instructorId", uuid).get();
    }

    public void deleteCourse(String docID) {
        store.collection(COURSE_PATH).document(docID).delete();
    }

    public void deleteUser(String uuid) {
        store.collection(USER_PATH).document(uuid).delete();
    }
}
