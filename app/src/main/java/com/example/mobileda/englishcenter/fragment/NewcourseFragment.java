package com.example.mobileda.englishcenter.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.dbutility.CourseUtil;
import com.example.mobileda.englishcenter.model.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewcourseFragment extends android.support.v4.app.Fragment {

     FirebaseFirestore db;

     @BindView(R.id.edt_cost)
     EditText edtCost;

     @BindView(R.id.edt_course)
     EditText edtCourse;

     @BindView(R.id.edt_room)
     EditText edtRoom;

     @BindView(R.id.edt_state)
     EditText edtState;

     @BindView(R.id.edt_time)
     EditText edtTime;
    private String TAG = "debug";

    public NewcourseFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newcourse, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: fragment new course" );
    }

    @OnClick(R.id.btn_send)
    void send(View view){
        db = FirebaseFirestore.getInstance();

        DocumentReference teacherRef = db.collection("teachers").document(FirebaseAuth.getInstance().getUid());

        Course course = new Course(edtCost.getText().toString(), edtCourse.getText().toString(), edtTime.getText().toString(), edtRoom.getText().toString(), edtState.getText().toString(), teacherRef);


        db.collection("courses")
                .add(course.toMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Đã tạo", Toast.LENGTH_SHORT).show();
                        edtCost.setText("");;
                        edtCourse.setText("");
                        edtRoom.setText("");
                        edtState.setText("");
                        edtTime.setText("");
                            CourseUtil.getInstance().update();
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}