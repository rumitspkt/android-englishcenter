package com.example.mobileda.englishcenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.DataViewHolder> {

    private List<Course> courses;
    private Context context;

    public CourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public int getItemCount() {
        return courses == null ? 0 : courses.size();
    }

    @Override
    public CourseAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);

        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.DataViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.tvCourse.setText(course.getCourse());
        String description = course.getTime() + " - " + course.getCost() + " - " + "Phòng " + course.getRoom() + " - " + course.getState();
        holder.tvDesciption.setText(description);
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCourse;
        private TextView tvDesciption;

        public DataViewHolder(View itemView) {
            super(itemView);

            tvCourse =  itemView.findViewById(R.id.tv_course);
            tvDesciption =  itemView.findViewById(R.id.tv_desciption);

        }
    }
}
