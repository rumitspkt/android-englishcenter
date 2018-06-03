package com.example.mobileda.englishcenter.dbutility;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mobileda.englishcenter.model.Student;
import com.example.mobileda.englishcenter.model.StudentResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentUtil {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static StudentUtil instance;
    private List<StudentResult> studentResults;

    public List<Student> getStudents() {
        return students;
    }

    private  List<Student> students;

    private StudentUtil(){
        students = new ArrayList<Student>();
    }

//    public List<StudentResult> getStudentResults() {
//        studentResults = new ArrayList<StudentResult>();
//        studentResults.add(new StudentResult("Nguyễn Văn Rum", "Bình Định - 26/11/1998 - Kỹ sư", 8.5, 10));
//        studentResults.add(new StudentResult("Nguyễn Văn Tèo", "Bình Định - 26/11/1998 - Kỹ sư", 6.5, 5));
//        studentResults.add(new StudentResult("Nguyễn Văn Teo", "Bình Định - 26/11/1998 - Kỹ sư", 1.5, 2));
//        studentResults.add(new StudentResult("Nguyễn Văn Tẹo", "Bình Định - 26/11/1998 - Kỹ sư", 2.5, 4.5));
//        studentResults.add(new StudentResult("Nguyễn Văn Téo", "Bình Định - 26/11/1998 - Kỹ sư", 5.5, 7));
//        studentResults.add(new StudentResult("Nguyễn Văn Tẽo", "Bình Định - 26/11/1998 - Kỹ sư", 6.5, 8));
//        studentResults.add(new StudentResult("Nguyễn Văn Tẻo", "Bình Định - 26/11/1998 - Kỹ sư", 2.5, 10));
//
//        return studentResults;
//    }

    private static final String TAG = "debug";

    public static synchronized StudentUtil getInstance(){
        if(instance==null){
            instance = new StudentUtil();
        }

        return instance;
    }

    public void update(){
        students.clear();
        db.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        Log.e(TAG, "Student onComplete: " + document.getData().toString() );
                        Student student = (Student) document.toObject(Student.class);
                        Log.e(TAG, "onComplete: " + student.getName() );
                        student.setId(document.getId());
                        students.add(student);
                    }
                    Log.e(TAG, "onComplete: " + students.size());
                }

            }
        });
    }
}
