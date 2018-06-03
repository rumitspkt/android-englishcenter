package com.example.mobileda.englishcenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.model.StudentResult;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.DataViewHolder> {

    private List<StudentResult> students;
    private Context context;

    public StudentAdapter(Context context, List<StudentResult> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public int getItemCount() {
        return students == null ? 0 : students.size();
    }

    @Override
    public StudentAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);

        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        StudentResult student = students.get(position);
        holder.tvStudent.setText(student.getStudent());
        holder.tvDesciption.setText(student.getDescription());
        holder.tvMidtermMark.setText(String.valueOf(student.getMidtermMark()));
        holder.tvFinaltermMark.setText(String.valueOf(student.getFinaltermMark()));
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStudent;
        private TextView tvDesciption;
        private TextView tvMidtermMark;
        private TextView tvFinaltermMark;

        public DataViewHolder(View itemView) {
            super(itemView);

            tvStudent =  itemView.findViewById(R.id.tv_student);
            tvDesciption =  itemView.findViewById(R.id.tv_desciption);
            tvMidtermMark = itemView.findViewById(R.id.tv_midterm_mark2);
            tvFinaltermMark = itemView.findViewById(R.id.tv_finalterm_mark2);

        }
    }
}
