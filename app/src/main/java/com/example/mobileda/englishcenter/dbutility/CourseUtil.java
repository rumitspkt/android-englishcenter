package com.example.mobileda.englishcenter.dbutility;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mobileda.englishcenter.model.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CourseUtil {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CourseUtil instance;
    private List<Course> courses;

    public List<Course> getCoursesTeacher() {
        return coursesTeacher;
    }

    private List<Course> coursesTeacher;
    private static final String TAG = "debug";

    private CourseUtil(){
        courses = new ArrayList<Course>();
        coursesTeacher = new ArrayList<Course>();
    }

    public static synchronized CourseUtil getInstance(){
        if(instance==null){
            instance = new CourseUtil();
        }

        return instance;
    }

    public void update(){
        courses.clear();
        db.collection("courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        Log.e(TAG, "onComplete: " + document.getData().toString() );
                        Course course = (Course) document.toObject(Course.class);
                        Log.e(TAG, "onComplete: " + course.getCourse() );
                        course.setId(document.getId());
                        courses.add(course);
                    }
                    Log.e(TAG, "onComplete: " + courses.size());
                    updateCoursesByTeacher();
                }

            }
        });
    }

    public void updateCoursesByTeacher(){
        coursesTeacher.clear();
        final String id = FirebaseAuth.getInstance().getUid();
        for (final Course course: courses
             ) {
            course.getTeacher()
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    DocumentSnapshot doc = task.getResult();
                    if(doc.getId().equals(id)){
                        coursesTeacher.add(course);
                    }
                    Log.e(TAG, "getCoursesByTeacher: " + coursesTeacher.size() );
                }
            });
        }


    }

    public Course getCourse(String id){
        for (Course course: coursesTeacher
             ) {
            if(course.getId().equals(id)){
                return course;
            }

        }
        return null;
    }


}
