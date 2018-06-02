package com.example.mobileda.englishcenter.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @BindView(R.id.tv_name)
    TextView edtName;
    @BindView(R.id.tv_address) TextView edtAddress;
    @BindView(R.id.tv_birthday) TextView edtBirthday;
    @BindView(R.id.tv_income) TextView edtIncome ;
    @BindView(R.id.tv_literacy) TextView edtLiteracy ;

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
                Teacher teacher = doc.toObject(Teacher.class);
                Log.e(TAG, "onComplete: " + doc.getId() );
                //Log.e(TAG, "onComplete: " + doc.getData().toString() );
                if(teacher != null){
                    Log.e(TAG, "onComplete: " + teacher.getBirthday().toString() );
                    if(teacher.getName() != null)
                        edtName.setText(teacher.getName());
                    if(teacher.getAddress() != null)
                        edtAddress.setText(teacher.getAddress());
                    if(teacher.getBirthday() != null)
                        edtBirthday.setText(teacher.getBirthday().toString());
                    if(teacher.getIncome() != 0)
                        edtIncome.setText(String.valueOf(teacher.getIncome()));
                    if(teacher.getLiteracy() != null)
                        edtLiteracy.setText(teacher.getLiteracy());
                }
            }
        });
    }


}
