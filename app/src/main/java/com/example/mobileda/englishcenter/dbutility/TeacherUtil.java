package com.example.mobileda.englishcenter.dbutility;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mobileda.englishcenter.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherUtil {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static TeacherUtil instance;
    private static final String TAG = "debug";

    private Teacher teacher;

    private TeacherUtil(){
        teacher = new Teacher();
    }

    public static synchronized TeacherUtil getInstance(){
        if(instance==null){
            instance = new TeacherUtil();
        }

        return instance;
    }

    public Teacher getTeacherByID(String id){
        return null;
    }

    public void update(){
        db.collection("teachers").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    teacher = document.toObject(Teacher.class);
                    Log.e(TAG, "onComplete: 1" + teacher.getIncome() + " " + teacher.getName() );
                }
            }
        });
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
