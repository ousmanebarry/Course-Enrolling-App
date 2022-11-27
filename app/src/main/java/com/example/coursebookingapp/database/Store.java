package com.example.coursebookingapp.database;

import com.example.coursebookingapp.classes.Course;
import com.example.coursebookingapp.classes.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

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

    public void addCourse(Course course) {
        CollectionReference df = store.collection(COURSE_PATH);
        df.add(course.getMap());
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

    public Task<QuerySnapshot> getUnassignedCourses() {
        CollectionReference col = store.collection(COURSE_PATH);
        return col.whereEqualTo("hasInstructor", false).get();
    }

    public void assignTeacher(String docId, String capacity, String desc, String hours, String days, String uuid) {
        CollectionReference col = store.collection(COURSE_PATH);
        col.document(docId).update("hasInstructor", true);

        HashMap<String, Object> courseData = new HashMap<>();

        courseData.put("instructorId", uuid);
        courseData.put("capacity", capacity);
        courseData.put("description", desc);
        courseData.put("hours", hours);
        courseData.put("days", days);

        col.document(docId).update(courseData);
    }

    public Task<List<QuerySnapshot>> getCoursesByNameOrCode(String nameOrCode) {

        Task<QuerySnapshot> taskOne = store.collection(COURSE_PATH).whereEqualTo("name", nameOrCode).get();
        Task<QuerySnapshot> taskTwo = store.collection(COURSE_PATH).whereEqualTo("code", nameOrCode).get();

        return Tasks.whenAllSuccess(taskOne, taskTwo);

    }

    public Task<QuerySnapshot> getInstructorCourses(String uuid) {
        CollectionReference col = store.collection(COURSE_PATH);
        return col.whereEqualTo("hasInstructor", true).whereEqualTo("instructorId", uuid).get();
    }

    public Task<QuerySnapshot> getStudentCourses(String uuid) {
        CollectionReference col = store.collection(COURSE_PATH);
        return col.whereEqualTo("hasStudent", true).whereEqualTo("studentId", uuid).get();
    }

    public void unassignCourse(String docID, Course course){
        DocumentReference df = store.collection(COURSE_PATH).document(docID);
        df.set(course.getMap());
    }

    public void editCourse(String docId, String name, String code) {
        CollectionReference col = store.collection(COURSE_PATH);
        col.document(docId).update("name", name, "code", code);
    }

    public void deleteCourse(String docID) {
        store.collection(COURSE_PATH).document(docID).delete();
    }

    public void updateCourse(String ID, HashMap data){
        CollectionReference col = store.collection(COURSE_PATH);
        col.document(ID).update(data);
    }

    public void deleteUser(String uuid) {
        store.collection(USER_PATH).document(uuid).delete();
    }
}
