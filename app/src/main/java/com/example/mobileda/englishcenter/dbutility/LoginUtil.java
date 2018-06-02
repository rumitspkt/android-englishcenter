package com.example.mobileda.englishcenter.dbutility;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mobileda.englishcenter.model.TeacherLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class LoginUtil {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<TeacherLogin> userList;
    private static LoginUtil instance;
    private static final String TAG = "debug";

    private LoginUtil(){
        userList = new ArrayList<TeacherLogin>();
    }
    public static synchronized LoginUtil getInstance(){
        if(instance==null){
            instance = new LoginUtil();
        }

        return instance;
    }

    public void update(){
        userList.clear();
        final TeacherLogin user = null;
        db.collection("teacher_logins").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        Log.e(TAG, "onComplete: " + document.getData().toString() );
                        TeacherLogin user = (TeacherLogin) document.toObject(TeacherLogin.class);
                        Log.e(TAG, "onComplete: " + user.getUname() );
                        userList.add(user);
                        Log.e(TAG, "onComplete: " + userList.size());
                    }
                }
            }
        });
    }

    public TeacherLogin getTeacherLogin(String uname, String pwd){
        Log.e(TAG, "getTeacherLogin: " + userList.size() );
        for (TeacherLogin user: userList
             ) {
            if(user.getUname().equals(uname) && user.getPwd().equals(pwd)){
                return user;
            }
        }
        return null;
    }


}
