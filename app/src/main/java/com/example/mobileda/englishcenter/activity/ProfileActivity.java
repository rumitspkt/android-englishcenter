package com.example.mobileda.englishcenter.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.dbutility.TeacherUtil;
import com.example.mobileda.englishcenter.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @BindView(R.id.tv_name)
    TextView edtName;
    @BindView(R.id.tv_address) TextView edtAddress;
    @BindView(R.id.tv_birthday) TextView edtBirthday;
    @BindView(R.id.tv_literacy) TextView edtLiteracy ;

    EditText edtAddress1;
    EditText edtBirthday1;
    EditText edtLiteracy1;
    EditText edtName1;

    AlertDialog dialog;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        Log.e(TAG, "onCreate: " + auth.getUid() );

        db.collection("teachers")
                .document(auth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot doc = task.getResult();
                teacher = doc.toObject(Teacher.class);
                Log.e(TAG, "onComplete: " + doc.getId() );
                //Log.e(TAG, "onComplete: " + doc.getData().toString() );
                if(teacher != null){
                    //Log.e(TAG, "onComplete: " + teacher.getBirthday().toString() );
                    if(teacher.getName() != null)
                        edtName.setText(teacher.getName());
                    if(teacher.getAddress() != null)
                        edtAddress.setText(teacher.getAddress());
                    if(teacher.getBirthday() != null){
                        String s = new SimpleDateFormat("dd/MM/yyyy").format(teacher.getBirthday());
                        edtBirthday.setText(s);
                    }

                    if(teacher.getLiteracy() != null)
                        edtLiteracy.setText(teacher.getLiteracy());
                }
            }
        });


        //Preparing views
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ResourceType") View layout = inflater.inflate(R.layout.dialog_profile, (ViewGroup) findViewById(R.layout.activity_profile));



        //Building dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date birthday = null;
                try {
                    birthday = df.parse(edtBirthday1.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Teacher currTeacher = TeacherUtil.getInstance().getTeacher();

                final Teacher teacher1 = new Teacher(edtName1.getText().toString(), edtAddress1.getText().toString(), birthday, edtLiteracy1.getText().toString(), currTeacher.getIncome());
                db.collection("teachers")
                        .document(auth.getUid())
                        .set(teacher1.toMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                teacher = teacher1;
                                TeacherUtil.getInstance().update();
                                Toast.makeText(ProfileActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                edtName.setText(teacher1.getName());
                                edtAddress.setText(teacher1.getAddress());
                                String s = new SimpleDateFormat("dd/MM/yyyy").format(teacher1.getBirthday());
                                edtBirthday.setText(s);
                                edtLiteracy.setText(teacher1.getLiteracy());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
                dialog.dismiss();
            }
        });

         dialog = builder.create();

        edtAddress1 = (EditText) layout.findViewById(R.id.edt_address);
        edtBirthday1 = (EditText) layout.findViewById(R.id.edt_birthday);
        edtLiteracy1 = (EditText) layout.findViewById(R.id.edt_literacy);
        edtName1 = (EditText) layout.findViewById(R.id.edt_name);

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#62c3ff"));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#62c3ff"));

            }
        });



    }

    @OnClick(R.id.btn_update)
    void clickUpdate(View view){
        dialog.show();


        if(teacher != null){
            //Log.e(TAG, "onComplete: " + teacher.getBirthday().toString() );
            if(teacher.getName() != null)
                edtName1.setText(teacher.getName());
            if(teacher.getAddress() != null)
                edtAddress1.setText(teacher.getAddress());
            if(teacher.getBirthday() != null){
                String s = new SimpleDateFormat("dd/MM/yyyy").format(teacher.getBirthday());
                edtBirthday1.setText(s);
            }
            if(teacher.getLiteracy() != null)
                edtLiteracy1.setText(teacher.getLiteracy());
        }
    }


}
