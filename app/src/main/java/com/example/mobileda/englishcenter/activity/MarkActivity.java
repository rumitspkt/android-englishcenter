package com.example.mobileda.englishcenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.dbutility.CourseUtil;
import com.example.mobileda.englishcenter.model.Course;
import com.example.mobileda.englishcenter.utility.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkActivity extends AppCompatActivity {

    @BindView(R.id.spn)
    Spinner spn;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;


    ArrayList<Course> courses = new ArrayList<Course>();
    ArrayAdapter<Course> dataAdapter;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        ButterKnife.bind(this);

        loadingPanel.setVisibility(View.GONE);

        courses = (ArrayList<Course>) CourseUtil.getInstance().getCoursesTeacher();

        dataAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_item, courses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadingPanel.setVisibility(View.VISIBLE);
                Utilities.hideSoftKeyboard(MarkActivity.this);
                Course course = (Course) spn.getSelectedItem();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }
}
