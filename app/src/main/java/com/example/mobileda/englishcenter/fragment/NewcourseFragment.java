package com.example.mobileda.englishcenter.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.dbutility.CourseUtil;
import com.example.mobileda.englishcenter.model.Course;
import com.example.mobileda.englishcenter.utility.Utilities;
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

     @BindView(R.id.spn_state)
     Spinner spnState;

    @BindView(R.id.spn_time)
    Spinner spnTime;

     @BindView(R.id.btn_send)
     Button btnSend;

     @BindView(R.id.loadingPanel)
     RelativeLayout loadingPanel;



    private String TAG = "debug";

    String times[] = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"};
    ArrayAdapter<String> timeAdapter;

    String states[] = {"Còn chỗ", "Hot!", "Khuyến mãi", "Cấp tốc", "Oh yeah!!"};
    ArrayAdapter<String> stateAdapter;

    public NewcourseFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newcourse, container, false);
        ButterKnife.bind(this, rootView);

        //make color :))))))))))
        loadingPanel.setVisibility(View.INVISIBLE);

        timeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTime.setAdapter(timeAdapter);

        stateAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnState.setAdapter(stateAdapter);

        spnTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.e(TAG, "onItemSelected: spnTime" );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.e(TAG, "onItemSelected: spnState" );
                Utilities.hideSoftKeyboard(getActivity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        return rootView;
    }

    @OnClick(R.id.layout_root)
    void clickBackground(View view){
        Utilities.hideSoftKeyboard(getActivity());
        View v = getActivity().getCurrentFocus();
        if(v != null)
            v.clearFocus();
    }



    @OnClick(R.id.btn_send)
    void send(View view){

        if(Utilities.haveBlank(getActivity(), new EditText[] {edtRoom, edtCourse, edtCost})) {
            return;
        }

        btnSend.setEnabled(false);
        loadingPanel.setVisibility(View.VISIBLE);

        db = FirebaseFirestore.getInstance();

        DocumentReference teacherRef = db.collection("teachers").document(FirebaseAuth.getInstance().getUid());

        Course course = new Course(edtCost.getText().toString() + " VNĐ", edtCourse.getText().toString(), spnTime.getSelectedItem().toString(), edtRoom.getText().toString(), spnState.getSelectedItem().toString(), teacherRef);


        db.collection("courses")
                .add(course.toMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {


                        loadingPanel.setVisibility(View.INVISIBLE);
                        btnSend.setEnabled(true);

                        Toast.makeText(getActivity(), "Đã tạo", Toast.LENGTH_SHORT).show();
                        edtCost.setText("");;
                        edtCourse.setText("");
                        edtRoom.setText("");
//                        edtState.setText("");
//                        edtTime.setText("");
                        CourseUtil.getInstance().update();
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        loadingPanel.setVisibility(View.INVISIBLE);
                        btnSend.setEnabled(true);

                        Toast.makeText(getActivity(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}