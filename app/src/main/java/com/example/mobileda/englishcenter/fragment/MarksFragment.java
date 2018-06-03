package com.example.mobileda.englishcenter.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.adapter.StudentResultAdapter;
import com.example.mobileda.englishcenter.dbutility.CourseUtil;
import com.example.mobileda.englishcenter.dbutility.StudentUtil;
import com.example.mobileda.englishcenter.model.Course;
import com.example.mobileda.englishcenter.model.Student;
import com.example.mobileda.englishcenter.model.StudentResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarksFragment extends android.support.v4.app.Fragment {


    private static final String TAG = "debug";

    private  boolean flag = false;

    public MarksFragment() {

    }



    StudentResultAdapter adapter;

    List<StudentResult> studentResults;

    ArrayList<Course> courses = new ArrayList<Course>();
    ArrayAdapter<Course> dataAdapter;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @BindView(R.id.rv_marks)
    RecyclerView rvItems;

    @BindView(R.id.spn)
    Spinner spn;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_marks, container, false);
        ButterKnife.bind(this, rootView);

        loadingPanel.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();

        courses = (ArrayList<Course>) CourseUtil.getInstance().getCoursesTeacher();
        dataAdapter = new ArrayAdapter<Course>(getActivity(), android.R.layout.simple_spinner_item, courses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);

        studentResults = new ArrayList<StudentResult>();

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                loadingPanel.setVisibility(View.VISIBLE);

                final Course course = (Course) spn.getSelectedItem();
                Log.e(TAG, "onItemSelected: " + course.getId() );

                studentResults.clear();

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
                                            studentResults.add(studentResult);
                                        }else{

                                        }
                                    }else{
                                        if(final1 == null){
                                            studentResult = new StudentResult(student.getName(), description, ((Number)mid).floatValue());
                                            studentResults.add(studentResult);
                                        }else{
                                            studentResult = new StudentResult(student.getName(), description,  ((Number)mid).floatValue(), ((Number)final1).floatValue());
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
                        adapter.notifyDataSetChanged();
                        loadingPanel.setVisibility(View.GONE);
                    }
                }, 2000);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        //students = StudentUtil.getInstance().getStudentResults();
        Log.e(TAG, "onCreateView: " + studentResults.size() );



        adapter = new StudentResultAdapter(getActivity(), studentResults);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setAdapter(adapter);
        rvItems.addItemDecoration(new DividerItemDecoration(rvItems.getContext(), DividerItemDecoration.VERTICAL));



        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.d("MyFragment", "Fragment is visible.");
//            courses = CourseUtil.getInstance().getCoursesTeacher();
//            if(adapter != null)
//                adapter.notifyDataSetChanged();
        }

        else {
            Log.d("MyFragment", "Fragment is not visible.");
        }
    }


}