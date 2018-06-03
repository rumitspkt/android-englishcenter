package com.example.mobileda.englishcenter.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.example.mobileda.englishcenter.model.Message;
import com.example.mobileda.englishcenter.utility.Utilities;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @BindView(R.id.edt_title)
    EditText edtTitle;

    @BindView(R.id.edt_content)
    EditText edtContent;

    @BindView(R.id.spn)
    Spinner spn;

    @BindView(R.id.btn_send)
    Button btnSend;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingPanel;

    ArrayList<Course> courses = new ArrayList<Course>();
    ArrayAdapter<Course> dataAdapter;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        loadingPanel.setVisibility(View.INVISIBLE);

        courses = (ArrayList<Course>) CourseUtil.getInstance().getCoursesTeacher();


        dataAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_item, courses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Utilities.hideSoftKeyboard(MessageActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    @OnClick(R.id.layout_root)
    void clickBackground(View view){
        Utilities.hideSoftKeyboard(this);
        View v = getCurrentFocus();
        if(v != null)
            v.clearFocus();
    }

    @OnClick(R.id.btn_send)
    void onClickBtnSend(View view){

        if(Utilities.haveBlank(this, new EditText[] {edtContent, edtTitle})) {
            return;
        }

        db = FirebaseFirestore.getInstance();

        btnSend.setEnabled(false);
        loadingPanel.setVisibility(View.VISIBLE);

        Course course = (Course) spn.getSelectedItem();
        DocumentReference coursRef = db.collection("courses").document(course.getId());
        DocumentReference teacherRef = db.collection("teachers").document(FirebaseAuth.getInstance().getUid());

        Message message = new Message(edtTitle.getText().toString(), edtContent.getText().toString(), teacherRef, coursRef);

        db.collection("messages")
                .add(message.toMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        loadingPanel.setVisibility(View.INVISIBLE);
                        btnSend.setEnabled(true);

                        Toast.makeText(MessageActivity.this, "Đã gửi", Toast.LENGTH_SHORT).show();
                        edtContent.setText("");;
                        edtTitle.setText("");
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        loadingPanel.setVisibility(View.INVISIBLE);
                        btnSend.setEnabled(true);

                        Toast.makeText(MessageActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}
