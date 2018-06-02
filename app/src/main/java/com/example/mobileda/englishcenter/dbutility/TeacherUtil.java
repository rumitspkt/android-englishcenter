package com.example.mobileda.englishcenter.dbutility;

import com.example.mobileda.englishcenter.model.Teacher;
import com.example.mobileda.englishcenter.model.TeacherLogin;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TeacherUtil {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static TeacherUtil instance;
    private static final String TAG = "debug";

    private TeacherUtil(){
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
}
