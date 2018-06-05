package com.example.mobileda.englishcenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.adapter.CourseAdapter;
import com.example.mobileda.englishcenter.dbutility.CourseUtil;
import com.example.mobileda.englishcenter.model.Course;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleActivity extends AppCompatActivity {

    String times[] = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"};
    ArrayAdapter<String> timeAdapter;

    CourseAdapter adapter;
    List<Course>  courses, coursesByDay;

    @BindView(R.id.spn)
    Spinner spnTime;

    @BindView(R.id.rv_courses)
    RecyclerView rvItems;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);

        loadingPanel.setVisibility(View.GONE);

        timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTime.setAdapter(timeAdapter);

        courses = CourseUtil.getInstance().getCoursesTeacher();
        coursesByDay = new ArrayList<>();

        adapter = new CourseAdapter(this, coursesByDay);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setAdapter(adapter);
        rvItems.addItemDecoration(new DividerItemDecoration(rvItems.getContext(), DividerItemDecoration.VERTICAL));

        spnTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadingPanel.setVisibility(View.VISIBLE);
                coursesByDay.clear();
                for (Course course: courses
                     ) {
                    if(course.getTime().equals(spnTime.getSelectedItem().toString())){
                        coursesByDay.add(course);
                    }
                }
                adapter.notifyDataSetChanged();
                loadingPanel.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
