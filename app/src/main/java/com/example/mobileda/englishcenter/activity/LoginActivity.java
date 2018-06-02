//package com.example.mobileda.englishcenter.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//
//import com.example.mobileda.englishcenter.R;
//import com.example.mobileda.englishcenter.dbutility.LoginUtil;
//import com.example.mobileda.englishcenter.model.TeacherLogin;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//
//import javax.annotation.Nullable;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class LoginActivity extends AppCompatActivity {
//
//    public static final String ARG_PARAM_1 = "TEACHER_ID";
//    private static final String TAG = "debug";
//
//    @BindView(R.id.edt_uname) EditText edtUname;
//
//    @BindView(R.id.edt_pwd) EditText edtPwd;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);
//        FirebaseApp.initializeApp(this);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//    }
//
//    @OnClick(R.id.btn_login)
//    public void btnLogin(View view) {
//        FirebaseFirestore mFirebase = FirebaseFirestore.getInstance();
//        TeacherLogin user = LoginUtil.getInstance().getTeacherLogin(edtUname.getText().toString(), edtPwd.getText().toString());
//        if(user == null){
//            //error
//            return;
//        }else{
//            final Intent intent = new Intent(this,HomeActivity.class);
//            user.getTeacher().addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                    Log.e(TAG, "onEvent: " + documentSnapshot.getData().toString() );
//                    intent.putExtra(ARG_PARAM_1, documentSnapshot.getId());
//                    LoginActivity.this.startActivity(intent);
//                }
//            });
//        }
//    }
//}


package com.example.mobileda.englishcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileda.englishcenter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @BindView(R.id.edt_uname) EditText edtUname;

    @BindView(R.id.edt_pwd) EditText edtPwd;

    @BindView(R.id.btn_login) Button btnLogin;

    @BindView(R.id.btn_signup) Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btn_login)
    void onClickBtnLogin(View view)
    {
        try {
            String email = edtUname.getText().toString();
            String password = edtPwd.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(LoginActivity.this, "Sai mật khẩu/tài khoản!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this,"Nhập thông tin cần thiết vào!",Toast.LENGTH_SHORT).show();
        }

    }
    @OnClick(R.id.btn_signup)
    void onClickBtnSignup(View view)
    {
        try {
            String email = edtUname.getText().toString();
            String password = edtPwd.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Map<String, Object> teacher = new HashMap<>();

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("teachers")
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(teacher)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });

                        Toast.makeText(LoginActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(LoginActivity.this, "Có gì đó sai sai! Không thành công!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this,"Nhập thông tin cần thiết vào!",Toast.LENGTH_SHORT).show();
        }
    }
}

