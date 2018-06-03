package com.example.mobileda.englishcenter.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.adapter.CourseAdapter;
import com.example.mobileda.englishcenter.dbutility.CourseUtil;
import com.example.mobileda.englishcenter.model.Course;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoursesFragment extends android.support.v4.app.Fragment {


    private static final String TAG = "debug";

    public CoursesFragment() {

    }



    CourseAdapter adapter;

    List<Course> courses;

    @BindView(R.id.rv_courses)
    RecyclerView rvItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);
        ButterKnife.bind(this, rootView);

        courses = CourseUtil.getInstance().getCoursesTeacher();
        Log.e(TAG, "onCreateView: " + courses.size() );

        adapter = new CourseAdapter(getActivity(), courses);

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
            courses = CourseUtil.getInstance().getCoursesTeacher();
            if(adapter != null)
                adapter.notifyDataSetChanged();
        }

        else {
            Log.d("MyFragment", "Fragment is not visible.");
        }
    }


}