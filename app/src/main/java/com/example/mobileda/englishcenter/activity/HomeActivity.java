package com.example.mobileda.englishcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.dbutility.CourseUtil;
import com.example.mobileda.englishcenter.dbutility.StudentUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "debug";

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle toggle;

    @BindView(R.id.navigation)
    NavigationView navigation;

    @BindView(R.id.nav_action)
    Toolbar toolbar;

//    @BindView(R.id.tv_name)
//    TextView edtName;
//    @BindView(R.id.tv_address) TextView edtAddress;
//    @BindView(R.id.tv_birthday) TextView edtBirthday;
//    @BindView(R.id.tv_income) TextView edtIncome ;
//    @BindView(R.id.tv_literacy) TextView edtLiteracy ;

    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        CourseUtil.getInstance().updateCoursesByTeacher();
        StudentUtil.getInstance().update();

        android.view.View header = navigation.getHeaderView(0);
        LinearLayout linear = header.findViewById(R.id.layout_profile);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_message:
                        Intent intent = new Intent(HomeActivity.this, MessageActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_income:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_home:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.nav_mark:
                        intent = new Intent(HomeActivity.this, MarkActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_quizs:

                        break;
                    case R.id.nav_schedule:

                        break;
                    case R.id.nav_subjects:
                        intent = new Intent(HomeActivity.this, CourseActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
