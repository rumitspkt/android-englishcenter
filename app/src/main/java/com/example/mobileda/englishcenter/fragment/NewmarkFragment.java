package com.example.mobileda.englishcenter.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.mobileda.englishcenter.dbutility.StudentUtil;
import com.example.mobileda.englishcenter.model.Course;
import com.example.mobileda.englishcenter.model.Student;
import com.example.mobileda.englishcenter.model.StudentResult;
import com.example.mobileda.englishcenter.utility.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewmarkFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "debug";

    @BindView(R.id.spn_course)
    Spinner spn_course;

    @BindView(R.id.spn_student)
    Spinner spn_student;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    @BindView(R.id.edt_midterm_mark)
    EditText edtMidtermMark;

    @BindView(R.id.edt_finalterm_mark)
    EditText edtFinaltermMark;

    @BindView(R.id.btn_update)
    Button btnUpdate;

    FirebaseFirestore db;

    ArrayList<Course> courses = new ArrayList<Course>();
    ArrayList<StudentResult> studentResults = new ArrayList<StudentResult>();

    ArrayAdapter<Course> courseAdapter;
    ArrayAdapter<StudentResult> studentAdapter;



    public NewmarkFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newmark, container, false);
        ButterKnife.bind(this, rootView);

        loadingPanel.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();



        courses = (ArrayList<Course>) CourseUtil.getInstance().getCoursesTeacher();
        courseAdapter = new ArrayAdapter<Course>(getActivity(), android.R.layout.simple_spinner_item, courses);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_course.setAdapter(courseAdapter);

        studentResults = new ArrayList<StudentResult>();
        studentAdapter = new ArrayAdapter<StudentResult>(getActivity(), android.R.layout.simple_spinner_item, studentResults);
        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_student.setAdapter(studentAdapter);


        spn_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                edtMidtermMark.setText("");
                edtFinaltermMark.setText("");

                loadingPanel.setVisibility(View.VISIBLE);

                studentResults.clear();

                final Course course = (Course) spn_course.getSelectedItem();
                List<Student> students = StudentUtil.getInstance().getStudents();
                for (final Student student: students
                        ) {
                    db.collection("students").document(student.getId()).collection("courses").document(course.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                                    String s = new SimpleDateFormat("dd/MM/yyyy").format(student.getBirthday());
                                    Object mid =  document.get("midterm_mark");
                                    Object final1 =  document.get("finalterm_mark");
                                    String description = student.getAddress() + " - " + s + " - " + student.getLiteracy();
                                    StudentResult studentResult;

                                    if(mid == null){
                                        if(final1 == null){
                                            studentResult = new StudentResult(student.getName(), description);
                                            studentResult.setStudent_id(student.getId());
                                            studentResult.setCourse_id(course.getId());
                                            studentResults.add(studentResult);
                                        }else{

                                        }
                                    }else{
                                        if(final1 == null){
                                            studentResult = new StudentResult(student.getName(), description, ((Number)mid).floatValue());
                                            studentResult.setStudent_id(student.getId());
                                            studentResult.setCourse_id(course.getId());
                                            studentResults.add(studentResult);
                                        }else{
                                            studentResult = new StudentResult(student.getName(), description,  ((Number)mid).floatValue(), ((Number)final1).floatValue());
                                            studentResult.setStudent_id(student.getId());
                                            studentResult.setCourse_id(course.getId());
                                            studentResults.add(studentResult);
                                        }
                                    }



                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        studentAdapter.notifyDataSetChanged();
                        loadingPanel.setVisibility(View.GONE);
                    }
                }, 2000);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StudentResult studentResult = (StudentResult) spn_student.getSelectedItem();
                edtMidtermMark.setText(Float.toString(studentResult.getMidtermMark()));
                edtFinaltermMark.setText(Float.toString(studentResult.getFinaltermMark()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return rootView;
    }

    @OnClick(R.id.layout_root)
    void clickBackground(View view){
        Utilities.hideSoftKeyboard(getActivity());
    }

    @OnClick(R.id.btn_update)
    void clickUpdate(View view){

        Float mid = Float.parseFloat(edtMidtermMark.getText().toString());
        Float final1 = Float.parseFloat(edtFinaltermMark.getText().toString());

        Map<String, Object> noteData = new HashMap<>();
        noteData.put("midterm_mark", mid);
        noteData.put("finalterm_mark", final1);

        Course course = (Course) spn_course.getSelectedItem();
        StudentResult studentResult = (StudentResult) spn_student.getSelectedItem();

        noteData.put("cost", course.getCost());
        noteData.put("course",course.getCourse());
        noteData.put("room",course.getRoom());
        noteData.put("state", course.getState());
        noteData.put("teacher", course.getTeacher());
        noteData.put("time", course.getTime());

        db.collection("students")
                .document(studentResult.getStudent_id())
                .collection("courses")
                .document(course.getId())
                .set(noteData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Đã cập nhật", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }


}