package com.example.mobileda.englishcenter.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mobileda.englishcenter.fragment.CoursesFragment;
import com.example.mobileda.englishcenter.fragment.NewcourseFragment;

public class PagerCourseAdapter  extends FragmentStatePagerAdapter {

    public PagerCourseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new CoursesFragment();
                break;
            case 1:
                frag=new NewcourseFragment();
                break;
        }
        return frag;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="Danh sách";
                break;
            case 1:
                title="Tạo mới";
                break;
        }

        return title;
    }

}
